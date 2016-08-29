package com.zfsoft.service.svcinterface;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.zfsoft.common.log.User;
import com.zfsoft.common.service.BaseService;
import com.zfsoft.dao.entities.LoginModel;

/**
 * 该类覆盖了zftal-service中的com.zfsoft.service.svcinterface.ILoginService类增加了单点登录的方法
 * 
 * 类名称：LoginService 
 * 类描述：登录业务层接口 
 * 创建人：hhy 
 * 创建时间：2011-12-20 上午10:52:26 
 * 修改人：hhy 
 * 修改时间：2011-12-20 上午10:52:26 
 * 修改备注： 
 * @version 
 *
 */
public interface ILoginService extends BaseService<LoginModel>{

	/**
	 *  
	* @Title: cxYhxx 
	* @Description: TODO(验证用户登录返回提示信息)
	* @param @param model
	* @param @return    设定文件 
	* @return User    返回类型 
	* @throws
	 */
	public User cxYhxx(LoginModel model) ;
	
	/**
	 * 
	* @Title: cxYhxxSso 
	* @Description: TODO(验证用户单点登录，该方法不做密码验证) 
	* @param @param model
	* @param @param session
	* @param @return    设定文件 
	* @return User    返回类型 
	* @throws
	 */
	public User cxYhxxSso( LoginModel model,HttpSession session );
	
	
	/**
	 * 
	* @Title: queryYjGnmkdm 
	* @Description: TODO(查询用户功能模块代码) 
	* @param @param model
	* @param @return    设定文件 
	* @return List<HashMap<String,String>>    返回类型 
	* @throws
	 */
	public List<HashMap<String, String>> queryYjGnmkdm(LoginModel model);
	
	
	/**
	 * 
	* @Title: queryLeftMenu 
	* @Description: TODO(查询用户二级，三级功能菜单列表) 
	* @param @param model
	* @param @return    设定文件 
	* @return List<HashMap<String,Object>>    返回类型 
	* @throws
	 */
	List<HashMap<String, Object>> queryLeftMenu(LoginModel model);
	
	
	/**
	 * 
	* @Title: setYhqxToSession 
	* @Description: TODO(将用户权限经过处理再塞入session) 
	* @param @param model
	* @param @param session    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setYhqxToSession(LoginModel model,HttpSession session) ;
	
	
}
