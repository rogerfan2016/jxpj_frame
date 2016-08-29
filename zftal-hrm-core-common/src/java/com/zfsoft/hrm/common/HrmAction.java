package com.zfsoft.hrm.common;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.opensymphony.xwork2.ActionContext;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.hrm.mail.entities.MailConfig;
import com.zfsoft.hrm.mail.service.svcinterface.IMailConfigService;
import com.zfsoft.hrm.report.entity.ReportView;
import com.zfsoft.hrm.util.mail.MailEngine;
import com.zfsoft.util.encrypt.DBEncrypt;

/**
 * 人事是系统基础Action
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-28
 * @version V1.0.0
 */
public abstract class HrmAction extends BaseAction {

	private static final long serialVersionUID = 1577874760931510447L;

	protected static final String SESSION_FORM_INFO = "_s_from_info";
	// 从取默认学年 
	protected String DEFULT_XN = SubSystemHolder.getPropertiesValue("defult_xn");
	// 从取默认学期 
	protected String DEFULT_XQ = SubSystemHolder.getPropertiesValue("defult_xq");

	private MailEngine mailEngine;
	
	private IMailConfigService mailConfigService;
	
	/**
	 * 列表页面
	 */
	protected static final String LIST_PAGE = "list";

	/**
	 * 编辑页面
	 */
	protected static final String EDIT_PAGE = "edit";

	private Message message = new Message();

	/**
	 * 设置错误信息
	 * 
	 * @param text信息内容
	 */
	public void setErrorMessage(String text) {
		setMessage(false, text);
	}

	/**
	 * 设置成成功信息
	 * 
	 * @param text
	 *            信息内容
	 */
	public void setSuccessMessage(String text) {
		setMessage(true, text);
	}

	/**
	 * 返回操作结果信息
	 */
	public Message getMessage() {
		return message;
	}

	protected void setMessage(boolean success, String text) {
		message.setSuccess(success);
		message.setText(text);
	}

	/**
	 * 从页面中获取int类型的值
	 * 
	 * @param num
	 * @return
	 */
	public int getInt(String num) {
		String number = getRequest().getParameter(num);
		if (number == null || "".equals(number)) {
			return -1;
		}

		return Integer.parseInt(number.trim());
	}

	/**
	 * 从页面http请求中获到String类型的参数值
	 * 
	 * @param str
	 *            http参数名称
	 * @return 如果http请求中含有该参数则返加该参数对应的值,如果没有则返回null
	 */
	public String getString(String str) {
		String st = getRequest().getParameter(str);
		if (st == null) {
			return null;
		}
		return st.trim();
	}

	/**
	 * 将一个对象存放到我们的ActionContext的上下文环境context中。
	 * 
	 * @param key
	 * @param value
	 */
	public void setInActionContext(String key, Object value) {

		ActionContext.getContext().put(key, value);
	}
	
	/**
	 * 
	 * 方法描述：获取登录用户所具有的角色数组
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-9 上午09:21:04
	 */
	public String[] getRole(){
		return getUser().getJsdms().toArray(new String[getUser().getJsdms().size()]);
	}

	public void sendEmailMessage(Map<String, Object> messageMap) throws MessagingException {
        // TODO: figure out how to get bundle specified
        // model.put("bundle", getTexts());
        String emailTo = (String) messageMap.get("mailTo");
//        emailTo="slw@10.71.32.115";
        if (StringUtils.isNotEmpty(emailTo)) {
            messageMap.put("sendDate", new Date(System.currentTimeMillis()));
//            if (mailEngine.getMailSender()==null) {
				setMailConfig(mailConfigService.findByType("EMAIL"));
//			}
            mailEngine.sendMessage(emailTo,
                    messageMap.get("subject")+"", (String)messageMap.get("taskId"),
                    messageMap);
        } else {
            throw new MessagingException("email key is null!");
        }
    
    }

	public String previewEmailMessage(Map<String, Object> messageMap) throws MessagingException {
        String emailTo = (String) messageMap.get("mailTo");
//        emailTo="slw@10.71.32.115";
        if (StringUtils.isNotEmpty(emailTo)) {
            messageMap.put("sendDate", new Date(System.currentTimeMillis()));
            return mailEngine.previewMessage((String)messageMap.get("taskId"), messageMap);
        } else {
            throw new MessagingException("邮箱没有设置！");
        }
    
    }
	
	/**
	 * @return the mailEngine
	 */
	public MailEngine getMailEngine() {
		return mailEngine;
	}

	/**
	 * @param mailEngine the mailEngine to set
	 */
	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	/**
	 * 设置邮件服务器
	 * 发送邮件之前需要配置
	 * @param config 
	 */
	public void setMailConfig(MailConfig config) {
        JavaMailSenderImpl send = new JavaMailSenderImpl();
        Properties p = new Properties();
        p.put("mail.smtp.host", config.getHost());
        p.put("mail.smtp.auth", "true");
        send.setJavaMailProperties(p);
        send.setUsername(config.getUser());
        send.setPort(config.getPort());
        try{
        	send.setPassword(new DBEncrypt().dCode( config.getPwd().getBytes()));
        }catch (Exception e) {
        	send.setPassword(config.getPwd());
		}
        mailEngine.setFrom(config.getSend());
        mailEngine.setMailSender(send);
	}
	
	//==================================导出EXCEL公用方法===================================================
	
    /**
     * 组装表头
     * @param column
     * @param row
     * @param cellContent
     * @return
     */
	protected Label generateTheadLabel(int column, int row, Object cellContent) throws WriteException{
        WritableCellFeatures f = new WritableCellFeatures();
        String content;
        if (cellContent == null) {
            content = "";
        } else {
            content = cellContent.toString();
        }
        Label label = new Label(column, row, content);
        label.setCellFeatures(f);
        label.setCellFormat(getTitleCellFormat());
        return label;
    }
    
    /**
     * 表头样式
     * @return
     * @throws WriteException
     */
    private CellFormat getTitleCellFormat() throws WriteException {
        WritableFont wf = new WritableFont(WritableFont.TAHOMA, 11, WritableFont.BOLD, false);
        WritableCellFormat wcf = new WritableCellFormat(wf);
        wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
        wcf.setBackground(Colour.GRAY_25);
        return wcf;
    }
    
    /**
     * 组装文本内容
     * @param column
     * @param row
     * @param cellContent
     * @return
     */
	protected Label generateValueLabel(int column,int row, Object cellContent) throws WriteException{
        WritableCellFeatures f = new WritableCellFeatures();
        String content;
        if (cellContent == null) {
            content = "";
        } else {
            content = cellContent.toString();
        }
        Label label = new Label(column,row,content);
        label.setCellFeatures(f);
        label.setCellFormat(getValueCellFormat());
        return label;
    }
	
	/**
     * 组装数值内容
     * @param column
     * @param row
     * @param cellContent
     * @return
     */
	protected jxl.write.Number generateValueNumber(int column,int row, Double cellContent) throws WriteException{
        WritableCellFeatures f = new WritableCellFeatures();
        if (cellContent == null) {
        	cellContent = 0.00;
        }
        jxl.write.Number number = new jxl.write.Number(column, row, cellContent);
        number.setCellFeatures(f);
        number.setCellFormat(getValueCellFormat());
        return number;
    }
    
    /**
     * 内容样式
     * @return
     * @throws WriteException
     */
    private CellFormat getValueCellFormat() throws WriteException {
        WritableFont wf = new WritableFont(WritableFont.TAHOMA, 11, WritableFont.NO_BOLD, false);
        WritableCellFormat wcf = new WritableCellFormat(wf);
        wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
        return wcf;
    }
    
    /**
     * 统计报表图标-柱状图（标准条形）
     * @param view
     * @param legend 标题
     * @param Sign  Y轴数值显示百分号
     */
    protected void fullColumnDataXML(ReportView view, String legend, String Sign) {  
		StringBuilder echarts_obj = new StringBuilder();
		String keySet = "";
		String keyValue = "";  
		String avgLine = "";
        for(String[] array : view.getValueList()) {  
        	keySet += "'" + array[1] + "',";
        	keyValue += array[0] + ",";;
        }
        echarts_obj.append(" {title : {text: '"+view.getReportTitle()+"',subtext: '"+view.getSubTitle()+"'},");
        echarts_obj.append(" tooltip : { trigger: 'axis' }, legend: { data:['"+ legend +"'] },");
        echarts_obj.append(" toolbox: { show : true, feature : {  mark : {show: true}, dataView : {show: true, readOnly: false},");
        echarts_obj.append(" magicType : {show: true, type: ['line',  'bar']}, restore : {show: true}, saveAsImage : {show: true} }},");
        echarts_obj.append(" calculable : true, yAxis : [ { type : 'category', data : [");
        echarts_obj.append(keySet);
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("]}],");
		echarts_obj.append(" xAxis : [ { type : 'value', axisLabel: { show: true, interval: 'auto', formatter: '{value}"+Sign+"' } } ],series : [ { name:'"+ legend +"', type:'bar', data:[");
		echarts_obj.append(keyValue);
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("], itemStyle:{ normal:{ label:{ show: true },  labelLine :{show:true} }  } "+ avgLine +" } ] }");
		this.getValueStack().set("echarts_obj", echarts_obj);
	}
  
    /**
     * 统计报表图标-柱状图（标准）
     * @param view
     * @param legend 标题
     * @param Sign  Y轴数值显示百分号
     * @param hasAvgLine  是否有平均值线
     */
    protected void fullBarDataXML(ReportView view, String legend, String Sign, boolean hasAvgLine) {  
		StringBuilder echarts_obj = new StringBuilder();
		String keySet = "";
		String keyValue = "";  
		String avgLine = "";
		if(hasAvgLine){
			avgLine = ",  markLine : { data : [ {type : 'average', name : '平均值'} ] }";
		}
        for(String[] array : view.getValueList()) {  
        	keySet += "'" + array[1] + "',";
        	keyValue += array[0] + ",";;
        }
        echarts_obj.append(" {title : {text: '"+view.getReportTitle()+"',subtext: '"+view.getSubTitle()+"'},");
        echarts_obj.append(" tooltip : { trigger: 'axis' }, legend: { data:['"+ legend +"'] },");
        echarts_obj.append(" toolbox: { show : true, feature : {  mark : {show: true}, dataView : {show: true, readOnly: false},");
        echarts_obj.append(" magicType : {show: true, type: ['line',  'bar']}, restore : {show: true}, saveAsImage : {show: true} }},");
        echarts_obj.append(" calculable : true, xAxis : [ { type : 'category', data : [");
        echarts_obj.append(keySet);
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("]}],");
		echarts_obj.append(" yAxis : [ { type : 'value', axisLabel: { show: true, interval: 'auto', formatter: '{value}"+Sign+"' } } ],series : [ { name:'"+ legend +"', type:'bar', barWidth : 30, data:[");
		echarts_obj.append(keyValue);
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("], itemStyle:{ normal:{ label:{ show: true },  labelLine :{show:true} }  } "+ avgLine +" } ] }");
		this.getValueStack().set("echarts_obj", echarts_obj);
	}
    
    /**
     * 统计报表图标-柱状图(双柱图)
     * @param view
     * @param legend 标题
     * @param Sign  Y轴数值显示百分号
     * @param hasAvgLine  是否有平均值线
     */
    protected void fullBar2DataXML(ReportView view, String[] legend, String Sign, boolean hasAvgLine) {  
		StringBuilder echarts_obj = new StringBuilder();
		String keySet = "";
		String keyValue = "",keyValue2 = "";  
		String avgLine = "";
		if(hasAvgLine){
			avgLine = ",  markLine : { data : [ {type : 'average', name : '平均值'} ] }";
		}
        for(String[] array : view.getValueList()) {  
        	keySet += "'" + array[0] + "',";
        	keyValue += array[1] + ",";
        	keyValue2 += array[2] + ",";
        }
        echarts_obj.append(" {title : {text: '"+view.getReportTitle()+"',subtext: '"+view.getSubTitle()+"'},");
        echarts_obj.append(" tooltip : { trigger: 'axis' }, legend: { data:['"+ legend[0] +"','" + legend[1] + "'] },");
        echarts_obj.append(" toolbox: { show : true, feature : {  mark : {show: true}, dataView : {show: true, readOnly: false},");
        echarts_obj.append(" magicType : {show: true, type: ['line',  'bar']}, restore : {show: true}, saveAsImage : {show: true} }},");
        echarts_obj.append(" calculable : true, xAxis : [ { type : 'category', data : [");
        echarts_obj.append(keySet);
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("]}],");
		echarts_obj.append(" yAxis : [ { type : 'value'} ],series : [ ");
		
		echarts_obj.append("{ name:'"+ legend[0] +"', type:'bar', data:[");
		echarts_obj.append(keyValue);
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("]"+ avgLine +" } ");
		
		echarts_obj.append(",{ name:'"+ legend[1] +"', type:'bar', data:[");
		echarts_obj.append(keyValue2);
		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
		echarts_obj.append("]"+ avgLine +" } ");
		
		echarts_obj.append("] }");
		this.getValueStack().set("echarts_obj", echarts_obj);
	}
    
    /**
     * <p>Title: fullPieDataXML</p>
     * <p>Description: 统计报表图标-饼图</p>
     * @param view
      */
    protected void fullPieDataXML(ReportView view) {  
 		StringBuilder echarts_obj = new StringBuilder();
 		String keySet = "";
 		String keyValue = "";  
         for(String[] array : view.getValueList()) {  
         	keySet += "'" + array[1] + "',";
         	keyValue += "{value:" + array[0] + ", name:'" + array[1] + "'},";
         }
 		echarts_obj.append("{title : {text: '"+view.getReportTitle()+"',subtext: '"+view.getSubTitle()+"',x:'center'},");
 		echarts_obj.append("tooltip : { trigger: 'item', formatter: '{a} <br/>{b} : {c} ({d}%)' }," +
 				"legend: { orient : 'vertical', x : 'left', data:[");
 		echarts_obj.append(keySet);
 		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
 		echarts_obj.append("]},");
 		echarts_obj.append("toolbox: { show : true, feature : { mark : {show: true}, dataView : {show: true, readOnly: false}, " +
 				"magicType : { show: true,  type: ['pie', 'funnel'], " +
 				"option: { funnel: { x: '25%', width: '50%', funnelAlign: 'left', max: 1548 } } }, " +
 				"restore : {show: true}, saveAsImage : {show: true} } },");
 		echarts_obj.append("calculable : true,");
 		echarts_obj.append("series : [ { name:'', type:'pie', radius : '55%', center: ['50%', '60%'], data:[");
 		echarts_obj.append(keyValue);
 		echarts_obj=echarts_obj.deleteCharAt(echarts_obj.length()-1);
 		echarts_obj.append("], itemStyle:{  normal:{  label:{  show: true,  formatter: '{b} : {c} ({d}%)'  },  labelLine :{show:true} }  } } ] }");
 		this.getValueStack().set("echarts_obj", echarts_obj);
 	}

	/**
	 * 返回
	 */
	public IMailConfigService getMailConfigService() {
		return mailConfigService;
	}

	/**
	 * 设置
	 * @param mailConfigService 
	 */
	public void setMailConfigService(IMailConfigService mailConfigService) {
		this.mailConfigService = mailConfigService;
	}
	
}
