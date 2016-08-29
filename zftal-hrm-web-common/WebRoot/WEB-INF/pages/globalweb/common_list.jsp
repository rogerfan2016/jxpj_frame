<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/commons/hrm/head.ini"%>
<script type="text/javascript">
	$(function(){
		$("a[id^='usual_']").click(function(){
			quickMenu(this);
		});
	});

</script>
<c:forEach items="${userCommons}" var="userComm"
	varStatus="index">
	<c:if test="${index.index < 9 }">
		<li>
			<a id="usual_${userComm.resourceId }" href="#"><img src="<%=stylePath %>/images/blue/54/Function0${index.index +1 }.png"/><span>${userComm.resourceName }</span></a>
		</li>
	</c:if>
	<c:if test="${index.index == 9 }">
		<li>
			<a id="usual_${userComm.resourceId }" href="#"><img src="<%=stylePath %>/images/blue/54/Function10.png"/><span>${userComm.resourceName }</span></a>
		</li>
	</c:if>
</c:forEach>
	