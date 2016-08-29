<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/globalweb/head/v4_url.ini"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ct" uri="/custom-code"%>

<style>
	.tableheight .liheight{
		height:28px;line-height:28px
	}
.tableheight .marginlength{
	margin-left:18px
}
	
	.tableheight ul li a {
    	color: #ccff00;
}	
</style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	<script type="text/javascript">
	
	function staffInfoShow(){
		$('#usual_N110101').click();
	}

	</script>
		<div class="newsnotice" id="notice">
			<h3><span>最新通知</span><a href="#" id="usual_N702020"></a></h3>
			<div class="newscon01"><!--有图新闻此处class命名改为：newscon-->
				
				<ul>
				</ul>
			</div>
		</div>
		
		<div class="remindtoday" >
      			<h3><span>个人信息</span><a href="#" id="usual_N110101"></a></h3>
	      			<table border="0" width="100%" class="tableheight">
		      			<tr >
		      				<td align="left" id="staffinfo">
			      				<ul>
			      				</ul>
		      				</td> 
	   				    	<td  width="35%" valign="top" id="zptd">  					
	   				        </td>  		
		      			</tr>
	      			</table>
    	</div>
		
		<!--
		<div class="remindtoday" id="tips">
			<h3><span>今日提醒</span><a href="#" id="usual_N701020"></a></h3>
			<ul>
			</ul>
		</div>
		--><div class="newsnotice" style="margin-top:5px;" id="todo">
			<h3 class="head_03"><span>待办事宜</span><a href="#" id="usual_N703010"></a></h3>
			<div class="newscon01"><!--有图新闻此处class命名改为：newscon-->
				<ul>
				</ul>
			</div>
		</div>
		
		<div class="remindtoday" style="margin-top:5px;" id="download">
			<h3><span>文件下载</span><a href="#" id="usual_N704020"></a></h3>
			<ul>
				</ul>
		</div>
		
		<jsp:include page="pendingtask.jsp"></jsp:include>
