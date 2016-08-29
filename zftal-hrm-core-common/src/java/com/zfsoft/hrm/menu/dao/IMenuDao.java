package com.zfsoft.hrm.menu.dao;

import java.util.List;

import com.zfsoft.dao.entities.IndexModel;

public interface IMenuDao {
	/**
	 * 插入菜单
	 * @param model
	 */
	public void insert(IndexModel model);
	/**
	 * 为admin用户添加操作权限
	 * @param id
	 */
	public void insertCzForAdmin(String id);
	/**
	 * 为admin用户删除操作权限
	 * @param id
	 */
	public void deleteCzForAdmin(String id);
	/**
	 * 删除菜单
	 * @param model
	 */
	public void delete(IndexModel model);
	/**
	 * 修改菜单
	 * @param model
	 */
	public void update(IndexModel model);
	/**
	 * 通过name获取菜单
	 * @param id
	 * @return
	 */
	public List<IndexModel> findByName(IndexModel indexModel);
	
	/**
	 * 通过id获取菜单
	 * @param id
	 * @return
	 */
	public IndexModel findById(String id);

	/**
	 * 通过父id获取菜单
	 * @param fatherId
	 * @return
	 */
	public List<IndexModel> findByFatherId(String fatherId);
	
	/**
	 * 通过父id获取自动添加的菜单
	 * @param fatherId
	 * @return
	 */
	public List<IndexModel> findByFatherIdForAuto(IndexModel model);
	/**
	 * 通过名称查找
	 * @param name
	 * @return
	 */
	public IndexModel findByName(String name,String likeId);
	/**
	 * 通过路径查找
	 * @param url
	 * @return
	 */
	public IndexModel findByUrl(String url);
	/**
	 * 通过层级获取
	 * @param i
	 * @return
	 */
	public List<IndexModel> findByLevel(String likeLevel);
}
