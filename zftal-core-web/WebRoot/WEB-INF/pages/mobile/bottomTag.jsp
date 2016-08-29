<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/mobile/pageTag.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<input type="hidden" id="totalSize" name="totalSize" value="${totalSize }"/>
<input type="hidden" id="nowPage" name="nowPage" value="${nowPage }"/>
<input type="hidden" id="perSize" name="perSize" value="${perSize }"/>
<input type="hidden" id="handle" name="handle" value="${handle }"/>

<table cellSpacing="0" cellPadding="0" width="100%" class="pageTag">
  <tr>
    <td align="right"><div id="previous"><a onclick="toPrevious();" href="#">上一页</a></div></td>
    <td width="30%" align="center">第<span id="nowPage" style="color:red;">${nowPage }</span>页/共<span id="totalPage" style="color:red;">${totalSize }</span>页</td>
    <td align="left"><div id="next"><a onclick="toNext();" href="#">下一页</a></div></td>
  </tr>
</table>