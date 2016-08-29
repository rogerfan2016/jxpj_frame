package com.zfsoft.globalweb.action;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.log.Role;
import com.zfsoft.common.log.User;
import com.zfsoft.common.service.BaseLog;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.service.impl.LogEngineImpl;
import com.zfsoft.service.svcinterface.IIndexService;
import com.zfsoft.service.svcinterface.IJsglService;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.service.svcinterface.IYhglService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.code.CommonCode;
import com.zfsoft.util.code.GenerateCode;

/**
 * 类名称：LoginAction
 * 类描述： 登录相关处理action
 * 创建人：hhy
 * 创建时间：2011-12-20 上午10:49:06
 * 修改人：hhy
 * 修改时间：2011-12-20 上午10:49:06
 * 修改备注：
 */

@Controller
public class LoginAction extends BaseAction implements ModelDriven<LoginModel> {

    private static final long serialVersionUID = 1L;
    private LoginModel model = new LoginModel();
    private ILoginService loginService;
    private IIndexService indexService;
    private IYhglService yhglService;
    private IJsglService jsglService;

    private BaseLog baseLog = LogEngineImpl.getInstance();



    public void initialize(){
        //TODO 做一些初始化操作（比如从缓存里读取一些信息），每个方法都调用
    }

    /**
     * 方法名: login
     * 方法描述: 登录
     * 修改时间：2011-12-20 上午10:49:38
     * 参数 @return
     * 返回类型 String
     *
     * @throws
     */
    public String login() {
    	 ValueStack vs = getValueStack();
         HttpSession session = getSession();
         Integer logincount = setLogincountSession(session);
         String yzm = (String) session.getAttribute("yzm");
//         yzm="1234";
         if (yzm.equalsIgnoreCase(model.getYzm())||logincount<=3) {
             try {
                 User user = loginService.cxYhxx(model);
                 if (StringUtil.isEmpty(user.getYhm())) {
                     vs.set("message", getText("login.info.failed"));
                     if ("0".equals(user.getSfqy())) {
                    	 HashMap map=new HashMap();
                    	 map.put("success", false);
                      	 map.put("message", "用户被锁定！请联系管理员"); 
                      	 this.getValueStack().set(DATA, map);
                         return DATA;
 					 }
                 } else {
                     //登录成功
                     session.setAttribute(USER_INFO_KEY, user);
                     //session.setAttribute(user.getYhm(), indexService.cxJsxxLb(user));
                     //初始化角色信息
                     this.initRoles(user);
                     
                     baseLog.login(user, getText("log.message.ywmc",
                             new String[]{"登陆系统", "xg_xtgl_yhb"}),
                             "系统管理", getText("log.message.czms",
                             new String[]{"登陆系统", "用户名", user.getYhm()}));
                    HashMap map=new HashMap();
                 	map.put("success", true);
                 	this.getValueStack().set(DATA, map);
                     return DATA;
                 }
             } catch (Exception e) {
                //logException(e);
             	HashMap map=new HashMap();
             	map.put("success", false);
             	map.put("logincount", logincount);
             	map.put("message", "登陆用户名或密码错误！");
             	this.getValueStack().set(DATA, map);
                 return DATA;
             }
         } else {
             //验证码不正确
             vs.set("message", getText("login.info.error"));
         }
         //来过后让session的验证码失效！防止Ajax重复提交
         HashMap map=new HashMap();
     	map.put("success", false);
     	map.put("message", "验证码不正确！"); 
     	this.getValueStack().set(DATA, map);
         return DATA;
    }

    /**
     * 登录不成功设置登录失败次数
     * @param session
     * @return
     */
	private Integer setLogincountSession(HttpSession session) {
		Integer logincount = (Integer)session.getAttribute("logincount");
         if(logincount==null){
        	 logincount = new Integer(0);
         }
         logincount++;
         session.setAttribute("logincount", logincount);
		return logincount;
	}


    /**
     * 方法名: logout
     * 方法描述: 注销
     * 修改时间：2011-12-20 上午10:49:50
     * 参数 @return
     * 返回类型 String
     *
     * @throws
     */
    public String logout() {
        HttpSession session = getSession();
        User user = getUser();
        if(null == user)
        {
        	return LOGIN;
        }
        baseLog.logout(user, getText("log.message.ywmc",
                new String[]{"登出系统", "xg_xtgl_yhb"}),
                "系统管理", getText("log.message.czms",
                new String[]{"登出系统", "用户名", user.getYhm()}));

        session.invalidate();
        return LOGIN;
    }

    /**
     * @throws IOException 
     * 方法名: login
     * 方法描述: 注销
     * 修改时间：2011-12-20 上午10:49:50
     * 参数 @return
     * 返回类型 String
     *
     * @throws
     */
    public String loginpage() throws IOException {
//        HttpSession session = getSession();
        User user = getUser();
        if(null == user)
        {
        	return LOGIN;
        }
//        baseLog.logout(user, getText("log.message.ywmc",
//                new String[]{"登出系统", "xg_xtgl_yhb"}),
//                "系统管理", getText("log.message.czms",
//                new String[]{"登出系统", "用户名", user.getYhm()}));
//
//        session.invalidate();
//        return LOGIN;
        getResponse().sendRedirect(getRequest().getContextPath()+"/xtgl/index_initMenu.html");
		return null;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public String yzm() {
        return "yzm";
    }

    public String code() {
        GenerateCode.getInstance(new CommonCode()).generate(getRequest(),getResponse());
        /*try {
            HttpServletResponse response = getResponse();
            HttpServletRequest request = getRequest();
            HttpSession session = request.getSession();

            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            int width = 65, height = 19;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            Random random = new Random();
            g.setColor(new Color(255, 255, 255));
            g.fillRect(0, 0, width, height);
            g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            g.setColor(new Color(255, 255, 255));//--del
            for (int i = 0; i < 40; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(3);
                g.setColor(new Color(255, 255, 255));//--del
                g.drawLine(x, y, x + xl, y + yl);
            }
            String sRand = "";
            for (int i = 0; i < 4; i++) {
                int j = (random.nextInt(35) + 48);
                if (j > 57) {
                    j += 8;
                }
                if (j == 48) {
                    j += random.nextInt(7) + 1;
                }
                if (j == 49) {
                    j += random.nextInt(7) + 1;
                }
                if (j == 73) {
                    j += random.nextInt(7) + 1;
                }
                if (j == 79) {
                    j += random.nextInt(7) + 1;
                }
                if (j == 111) {
                    j += random.nextInt(7) + 1;
                }

                String rand = String.valueOf((char) j);
                sRand += rand;
                g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
                g.drawString(rand, 13 * i + 6, 16);
            }
            g.dispose();
            session.setAttribute("yzm", sRand);
//            setAttibute("yzm",sRand);

            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        return null;
    }

	/**
	 * 初始化角色信息
	 * @param user
	 */
    private void initRoles(User user){
    	//如果系统部署方式为平台部署,session中标识为平台登陆
    	if("platform".equals(SubSystemHolder.getPropertiesValue("system_deploy"))){
    		getSession().setAttribute("platform_deploy", "1");
    		getSession().setAttribute("platform_login", "1");
    		return;
    	}
    	String login_type=SubSystemHolder.getPropertiesValue("login_type");
    	//用户拥有的角色
    	List<Role> allRoles=user.getAllRoles();
    	//1、单角色登陆
    	if("sole_role".equals(login_type)){
    		List<String> jsdms=new ArrayList<String>(1);
    		if(allRoles==null||allRoles.size()==0){
    			user.setJsdms(jsdms);
    			getRequest().getSession().setAttribute(user.getYhm(), null);
    			return;
    		}else if(StringUtils.isEmpty(user.getScdljsdm())){
    			String currentJsdm=allRoles.get(0).getJsdm();
    			user.setScdljsdm(currentJsdm);
    			jsdms.add(currentJsdm);
    			//保存本次登陆的角色
    			YhglModel yhglModel=new YhglModel();
    			yhglModel.setZgh(user.getYhm());
    			yhglModel.setScdljsdm(currentJsdm);
    			yhglService.update(yhglModel);
    		}else{
    			//检测上次登陆的角色，该用户是否还拥有，如果不再拥有，则取第一个拥有的角色，并写入db
    			for (Role role : allRoles) {
					if(user.getScdljsdm().equals(role.getJsdm())){
						jsdms.add(user.getScdljsdm());
						break;
					}
				}
    			if(jsdms.size()==0){
    				String currentJsdm=allRoles.get(0).getJsdm();
    				user.setScdljsdm(currentJsdm);
    				jsdms.add(currentJsdm);
    				//保存本次登陆的角色
        			YhglModel yhglModel=new YhglModel();
        			yhglModel.setZgh(user.getYhm());
        			yhglModel.setScdljsdm(currentJsdm);
        			yhglService.update(yhglModel);
    			}
    		}
    		user.setJsdms(jsdms);
    	}else{
    		//2、普通登陆
    		if(allRoles==null||allRoles.size()==0){
    			List<String> emptyList=Collections.emptyList();
    			user.setJsdms(emptyList);
    			getRequest().getSession().setAttribute(user.getYhm(), null);
    			return;
    		}else{
    			List<String> jsdms=new ArrayList<String>();
    			for(Role role:user.getAllRoles()){
    				jsdms.add(role.getJsdm());
    			}
    			user.setJsdms(jsdms);
    		}
    	}
    	getRequest().getSession().setAttribute(user.getYhm(), user.getJsdms());
    }
    
    public LoginModel getModel() {
        return model;
    }


    public ILoginService getLoginService() {
        return loginService;
    }


    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }


    public IIndexService getIndexService() {
        return indexService;
    }


    public void setIndexService(IIndexService indexService) {
        this.indexService = indexService;
    }

    
    public IYhglService getYhglService() {
        return yhglService;
    }

    
    public void setYhglService(IYhglService yhglService) {
        this.yhglService = yhglService;
    }

    
    public IJsglService getJsglService() {
        return jsglService;
    }

    
    public void setJsglService(IJsglService jsglService) {
        this.jsglService = jsglService;
    }
    
    
}
