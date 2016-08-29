<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/commons/hrm/head.ini" %>
<%@ include file="/WEB-INF/pages/mobile/meta.jsp" %>
</head>
<body>
 <header data-am-widget="header" class="am-header am-header-default">
      <div class="am-header-left am-header-nav">
          <a href="../wjdc_mobile/index_initMenu.html" class="">
              <img class="am-header-icon-custom" src="data:image/svg+xml;charset=utf-8,&lt;svg xmlns=&quot;http://www.w3.org/2000/svg&quot; viewBox=&quot;0 0 12 20&quot;&gt;&lt;path d=&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill=&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
          </a>
      </div>

      <h1 class="am-header-title">
         我的消息
      </h1>
  </header>
<div data-am-widget="tabs" class="am-tabs am-tabs-default">
	<ul class="am-tabs-nav am-cf">
	    <li class="<c:if test='${query.status eq "0"}'>am-active</c:if>"><a onclick="changeTabs('0')">未读</a></li>
	    <li class="<c:if test='${query.status eq "1"}'>am-active</c:if>"><a onclick="changeTabs('1')">已读</a></li>
	</ul>
	<div data-am-widget="list_news" class="am-list-news am-list-news-default" >
		<ul class="am-list">
			<s:iterator value="pageList" var="msg">
		      <li class="am-g am-list-item-dated">
		          <a href="../message/message_m_info.html?msg.guid=${msg.guid }" class="am-list-item-hd ">${msg.title }</a>
		          <span class="am-list-date"><s:date name="sendTime" format="yyyy-MM-dd"></s:date></span>
		      </li>
			</s:iterator>
		</ul>
	</div>
</div>
<script type="text/javascript">
    function changeTabs(val) {
        location.href = "<%=request.getContextPath()%>/message/message_m_page.html?query.status=" + val;
    }
</script>
<%@ include file="/WEB-INF/pages/mobile/navbar.jsp" %>
</body>
</html>