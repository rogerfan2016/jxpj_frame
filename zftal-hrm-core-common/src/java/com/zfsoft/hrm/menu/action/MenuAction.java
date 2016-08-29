package com.zfsoft.hrm.menu.action;

import java.util.List;

import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.menu.business.IMenuBusiness;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2013-11-15
 * @version V1.0.0
 */
public class MenuAction extends HrmAction{
	private static final long serialVersionUID = 15656565326L;
	/**
	 * 顶级菜单（查询条件）
	 */
	private IMenuBusiness menuBusiness;
	/**
	 * 菜单列表（展现对象）
	 */
	private List<IndexModel> menus;
	private IndexModel model = new IndexModel();
	private String op;
	
	public String page(){
		if(	getUser()==null
				||getUser().getJsdms()==null
				||getUser().getJsdms().isEmpty()
				||!getUser().getJsdms().get(0).equals("admin")){
			return "404";
		}
		String menuId = "N";
		if (model!=null&&!StringUtil.isEmpty(model.getGnmkdm())) {
			menuId = model.getGnmkdm();
		}
		menus = menuBusiness.getMenuByFartherId(menuId);
		model.setGnmkdm(menuId);
		return "page";
	}
	
	public String toEdit(){
		if(	getUser()==null
				||getUser().getJsdms()==null
				||getUser().getJsdms().isEmpty()
				||!getUser().getJsdms().get(0).equals("admin")){
			return "404";
		}
		if (model!=null&&!StringUtil.isEmpty(model.getGnmkdm())) {
			model=menuBusiness.getById(model.getGnmkdm());
			op = "modify";
		}else{
			String fjgndm="";
			if (model!=null) {
				fjgndm = model.getFjgndm();
			}
			model = new IndexModel();
			model.setFjgndm(fjgndm);
			op = "add";
		}
		getValueStack().set("businessInfoClasses",InfoClassCache.getInfoClasses("business"));
		return "edit";
	}
	
	public String remove(){
		if(	getUser()==null
				||getUser().getJsdms()==null
				||getUser().getJsdms().isEmpty()
				||!getUser().getJsdms().get(0).equals("admin")){
			return "404";
		}
		menus = menuBusiness.getMenuByFartherId(model.getGnmkdm());
		if (menus!=null&&menus.size()>0) {
			setErrorMessage("不能删除含子菜单的菜单，请先删除所属子菜单");
		}else {
			menuBusiness.remove(model.getGnmkdm());
			setSuccessMessage("删除成功");
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String save(){
		if(	getUser()==null
				||getUser().getJsdms()==null
				||getUser().getJsdms().isEmpty()
				||!getUser().getJsdms().get(0).equals("admin")){
			return "404";
		}
		if ("add".equals(op)) {
			menuBusiness.insertMenu(model);
		}else{
			menuBusiness.modify(model);
		}
		setSuccessMessage("保存成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 返回
	 */
	public List<IndexModel> getMenus() {
		return menus;
	}
	/**
	 * 设置
	 * @param menus 
	 */
	public void setMenus(List<IndexModel> menus) {
		this.menus = menus;
	}
	/**
	 * 设置
	 * @param menuBusiness 
	 */
	public void setMenuBusiness(IMenuBusiness menuBusiness) {
		this.menuBusiness = menuBusiness;
	}

	/**
	 * 返回
	 */
	public IndexModel getModel() {
		return model;
	}

	/**
	 * 设置
	 * @param model 
	 */
	public void setModel(IndexModel model) {
		this.model = model;
	}

	/**
	 * 返回
	 */
	public String getOp() {
		return op;
	}

	/**
	 * 设置
	 * @param op 
	 */
	public void setOp(String op) {
		this.op = op;
	}
	
}
