package com.zfsoft.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.UserSubsystemLastRole;
import com.zfsoft.dao.entities.YhglModel;

/**
 * 
* 
* 类名称：YhglDao 
* 类描述： 用户管理DAO
* 创建人：Administrator 
* 创建时间：2012-4-10 下午06:45:13 
* 修改人：Administrator 
* 修改时间：2012-4-10 下午06:45:13 
* 修改备注： 
* @version 
*
 */
public interface IYhglDao extends BaseDao<YhglModel>{
	
	/**
	 * 
	* 方法描述: 查询启用的用户信息列表
	* 参数 @return
	* 参数 @ 参数说明
	* 返回类型 List<YhglModel> 返回类型
	* @throws
	 */
	public List<YhglModel> cxQyyhxxList(YhglModel model);
	

	/**
	 * 
	* 方法描述: 查询用户角色列表
	* 参数 @param model
	* 参数 @return
	* 参数 @ 参数说明
	* 返回类型 List<YhglModel> 返回类型
	* @throws
	 */
	public List<JsglModel> cxJsdmList(JsglModel jsglModel);

	
	/**
	 * 
	* 方法描述: 删除用户角色信息
	* 参数 @param array 参数说明
	* 返回类型 void 返回类型
	* @throws
	 */
	public int scYhjsxx(Map<String, Object> param);
	
	/**
	 * 
	* 方法描述: 保存用户角色表
	* 参数 @param model 参数说明
	* 返回类型 void 返回类型
	* @throws
	 */
	public int zjYhjsxx(YhglModel model);
	
	/**
	 * 
	* 方法描述: 密码初始化
	* 参数 @param array 参数说明
	* 返回类型 void 返回类型
	* @throws
	 */
	public int mmCsh(YhglModel model);
	
	/**
	 * 
	* 方法描述: 修改密码
	* 参数 @param array 参数说明
	* 返回类型 void 返回类型
	* @throws
	 */
	public int xgMm(YhglModel model);
	
	/**
	 * 
	* 方法描述: 根据角色代码查询用户列表
	* 参数 @param model
	* 参数 @return 参数说明
	* 返回类型 JsglModel 返回类型
	* @throws
	 */
	public List<YhglModel> cxYhByJsdm(YhglModel model);
	
	/**
	 * 
	* 方法描述: 根据角色代码查询角色名称
	* 参数 @param model
	* 参数 @return 参数说明
	* 返回类型 JsglModel 返回类型
	* @throws
	 */
	public  JsglModel cxJsmcByJsdm(JsglModel model);
	
	/**
	 * 方法描述: 根据用户代码查询角色信息
	 * @param model
	 * @return
	 * @
	 */
	public List<JsglModel> cxJsdm(JsglModel model);
	
	/**
	 * 方法描述：根据职工号获取其证件号码
	 * @param: 
	 * @return: 
	 */
	public String zjhmByZgh(String zgh);
	
	/**
	 * 方法描述：查找所有职工号，用于批量初始化密码
	 * @param: 
	 * @return: 
	 */
	public List<String> zghList();
	/**
	 * 保存用户_子系统_上次登录角色 
	 * @param model
	 * @return
	 */
	public int insertUserSubsystemRole(UserSubsystemLastRole model);
	/**
	 * 更新用户上次访问子系统的角色
	 * @param model
	 * @return
	 */
	public int updateRoleByUserAndSubsystem(UserSubsystemLastRole model);
	/**
	 * 查询用户_子系统_上次登录角色表
	 * @param model
	 * @return
	 */
	public List<UserSubsystemLastRole> findUserSubsystemRoles(UserSubsystemLastRole model);
}
