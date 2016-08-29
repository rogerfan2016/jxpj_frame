package com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoClassQuery;

/**
 * 信息类数据操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-21
 * @version V1.0.0
 */
public interface IInfoClassDao {

	/**
	 * 查询信息类信息
	 * @param query 信息类查询条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public List<InfoClass> findList(InfoClassQuery query) throws DataAccessException;
	
	/**
	 * 统计记录数
	 * @param query 统计条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public int findCount(InfoClassQuery query) throws DataAccessException;
	
	/**
	 * 根据主键ID查询信息类信息
	 * @param guid 全局ID
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public InfoClass findById(String guid) throws DataAccessException;
	
	/**
	 * 增加信息类属性
	 * @param entity 增加的信息类
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void insert(InfoClass entity) throws DataAccessException;
	
	/**
	 * 修改信息类属性
	 * @param entity 修改的信息类
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void update(InfoClass entity) throws DataAccessException;
	
	/**
	 * 删除信息类属性
	 * @param guid 信息类ID
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void delete(String guid) throws DataAccessException;
	
	/**
	 * 更新信息类属性索引
	 * <p>
	 * 将指定信息类之后的索引信息类索引向上移一位，在信息类删除时使用
	 * </p>
	 * @param entity 信息类属性
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void updateAllIndex( InfoClass entity ) throws DataAccessException; 
}
