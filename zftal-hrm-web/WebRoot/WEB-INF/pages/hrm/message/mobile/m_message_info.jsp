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
          <a href="javascript:history.go(-1);" class="">
              <img class="am-header-icon-custom" src="data:image/svg+xml;charset=utf-8,&lt;svg xmlns=&quot;http://www.w3.org/2000/svg&quot; viewBox=&quot;0 0 12 20&quot;&gt;&lt;path d=&quot;M10,0l2,2l-8,8l8,8l-2,2L0,10L10,0z&quot; fill=&quot;%23fff&quot;/&gt;&lt;/svg&gt;" alt=""/>
          </a>
      </div>

      <h1 class="am-header-title">
         消息详情
      </h1>
  </header>

	<article data-am-widget="paragraph" class="am-paragraph am-paragraph-default" data-am-paragraph="{ tableScrollable: true, pureview: true }">
		<p>
			发送人：<ct:PersonParse code="${msg.sender }"/>
		</p>
		<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
      	<p>
      		<ct:ContentExplain content="${msg.content }" />
      	</p>
  	</article>

  <%@ include file="/WEB-INF/pages/mobile/navbar.jsp" %>
</body>
</html>