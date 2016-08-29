<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	</head>
	<body></body>
</html>
<script type="text/javascript">
	var aTag=$("<form method=\"post\"></form>");
	aTag.attr("action","<%=request.getContextPath() %>/xtgl/login_loginpage.html");
	aTag.appendTo("body");
	aTag.submit();
</script>
