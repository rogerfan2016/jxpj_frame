package com.zfsoft.hrm.report.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.zfsoft.hrm.report.dto.Col;
import com.zfsoft.hrm.report.xentity.Item;
/**
 * 报表展示对象，供前台展示
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
public class ReportView {
	// 图形标题
	private String reportTitle;
	// 图形二级标题
	private String subTitle;
	// 数据键值对
	private List<String[]> valueList;
	// 数据中的标题
	private List<Item> titles=new ArrayList<Item>();
	private LinkedHashMap<Item,List<Col>> itemValueMaps=new LinkedHashMap<Item,List<Col>>();
	/**
	 * 将表头放入
	 * @param items
	 */
	public void putTitle(List<Item> items){
		if(items==null)return;
		for (Item item : items) {
			titles.add(item);
		}
	}
	public Set<Item> getKeySet(){
		return itemValueMaps.keySet();
	}
	public void putValues(Item key,List<Col> values){
		itemValueMaps.put(key, values);
	}
	public List<Item> getTitles() {
		return titles;
	}
	public void setTitles(List<Item> titles) {
		this.titles = titles;
	}
	public LinkedHashMap<Item, List<Col>> getItemValueMaps() {
		return itemValueMaps;
	}
	public void setItemValueMaps(LinkedHashMap<Item, List<Col>> itemValueMaps) {
		this.itemValueMaps = itemValueMaps;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	/**
	 * @return the subTitle
	 */
	public String getSubTitle() {
		return subTitle;
	}
	/**
	 * @param subTitle the subTitle to set
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	/**
	 * @return the valueList
	 */
	public List<String[]> getValueList() {
		return valueList;
	}
	/**
	 * @param valueList the valueList to set
	 */
	public void setValueList(List<String[]> valueList) {
		this.valueList = valueList;
	}

}
