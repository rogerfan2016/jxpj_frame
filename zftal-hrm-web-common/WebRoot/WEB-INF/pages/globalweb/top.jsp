<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.zfsoft.zfca.tp.cas.client.ZfssoConfig,com.zfsoft.common.system.SubSystemHolder" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/v4_url.ini"%>
	<script type="text/javascript">
	$(function() {
		viewMenu();
		var liList=$(".ul_find").children();
		var width=0;
		for(var i=0;i<liList.length;i++){
			var name=$(liList[i]).find("a").html();
			width=width+name.length*12+6;
			if(width>600){
				$(liList[i]).appendTo("#moreUl");
			}
		}
		
		if($("#moreUl li").length<=0){
			$(".morediv").remove();
		}

		$.post(_path+"/message/message_listData.html","",function(data){
            if(data.success){
            	$("#messageNum").html("[新消息(<font color='red'>"+data.sumcount+"</font>)]");
                 if(data.sumcount > 0){
                	 $("#messageNum").click(function(){
                		 var targetCode = 'N701020';
                         var topCode = targetCode.substring(0,3);
                         $("[name='quickId']:hidden").val(targetCode);
                         $("a[id='li_"+topCode+"']").click();
                    });
                 }
            }else{
                alert(data.text);
            }
        },"json");
		
	});
	
	function logout(){

		showConfirmDivLayer('确定注销？',{'okFun':function(){
			//document.forms[0].action="login_logout.html";
			//document.forms[0].submit();
			var tag=$("<form method=\"post\"></form>");
			tag.attr("action","login_logout.html");
			tag.appendTo("body");
			tag.submit();
		 }
		})
	}
	//将当前选中的菜单样式突出
	function viewMenu() {
		$('ul.ul_find > li').each(function (i,n) {
			$(n).removeClass();
		});
		var classbz = $('#classbz').val();
		if (classbz != null && classbz != "") {
			classbz = "li_"+classbz;
			$('#'+classbz).parent().addClass('current');
		} else {
			$('#li_page').parent().addClass('current');
		}
	}
	
	//修改密码
	function xgMm(){
		showWindowV2('修改密码','<%=jsPath %>/xtgl/yhgl_xgMm.html',400,240);
	}
	function showPassword(oString){
		var obj = document.getElementById('downmenu');
		if(obj){
			obj.style.display=oString;
		}
	}

	function switchRole(sel){
		location.href="<%=request.getContextPath()%>/xtgl/index_switchRole.html?newRole="+$(sel).val();
	}
	function switchRole1(v){
        location.href="<%=request.getContextPath()%>/xtgl/index_switchRole.html?newRole="+v;
    }
	function subFormJs(sUrl){
		var tag=$("<form method=\"post\"></form>");
		tag.attr("action",sUrl);
		tag.appendTo("body");
		tag.append($("#classbz"));
		tag.append($("#quickId"));
		tag.submit();
	}
</script>
</head>
	<body >
		<!-- 一级菜单选中标志 -->
    <input type="hidden" name="classbz" id="classbz" value='<c:out value="${gnmkdm }"/>'/>
    <input type="hidden" name="quickId" id="quickId" value='<c:out value="${quickId }"/>'/>
      <div class="head">
        <div class="logo">
          <h2 class="floatleft"><img src="<%=stylePath%>/logo/logo_school.png"/></h2>
          <c:if test="${empty sessionScope.subsystem}">
			<h3 class="floatleft"><img src="<%=stylePath%>/logo/logo_hrm.png" /></h3>
          </c:if>
          <c:if test="${!empty sessionScope.subsystem }">
			<h3 class="floatleft"><img src="<%=request.getContextPath()%>/${ sessionScope.subsystem.index_icon}" /></h3>
          </c:if>
        </div>
        <%if("sole_role".equals(SubSystemHolder.getPropertiesValue("login_type"))){ %>
        <c:if test="${!empty sessionScope.user.allRoles }">
        <div class="versions" onmouseover="javascript:document.getElementById('versions').style.display='block'" onmouseout="javascript:document.getElementById('versions').style.display='none'">
            <A class=versions_btn onclick="showhid('versions');" href="#">${sessionScope.user.scdljsmc }</A> 
            <div style="display: none;width:${sessionScope.user.maxJsmcLength*15+10}px" id=versions class=versionslist >
                <c:forEach items="${sessionScope.user.allRoles }" var="item">
                    <A class=passw href="#" onclick="switchRole1('${item.jsdm}')">${item.jsmc }</A>
                </c:forEach>
            </div>
        </div>
        </c:if>
        <%} %>
        <div class="info">
        <div class="welcome">
        <div class="tool"  
        	onmouseover="javascript:showPassword('block')" 
        	onmouseout="javascript:showPassword('none')">
        	
       	 	<a class="tool_btn" href="#" onclick="${(empty sessionScope.subsystem || sessionScope.subsystem.sysCode =='hrm_system') ? "showhid('downmenu');" : ""}" >${user.xm }</a>
       	 	
       	 	<%
        	if( ZfssoConfig.usezfca == null || "0".equals( ZfssoConfig.usezfca ) ) {
        	%>
        		<c:if test="${empty sessionScope.subsystem || sessionScope.subsystem.sysCode =='hrm_system'}">
		        	<div class="downmenu" id="downmenu" style="display:none;">
		        		<a href="#" class="passw" onclick="xgMm()">修改密码</a>
		        	</div>
	        	</c:if>
        	<%
        	}
        	%>
         </div>
		<span>您好！</span>
		<c:if test="${empty sessionScope.subsystem}">
			<span> <a class="news" id="messageNum" href="#">[新消息(0)]</a></span>
		</c:if>
		<%
		if( ZfssoConfig.usezfca == null || "0".equals( ZfssoConfig.usezfca ) ) {
		%>
		<c:if test="${empty sessionScope.subsystem}">
			<a href="#" onclick="logout();" id="tologin" title="注销" class="logout"></a></div> 
		</c:if>
		<c:if test="${!empty sessionScope.subsystem}">
			<a href="#" onclick="javascript:window.close();" id="tologin" title="退出" class="sign_out"></a></div> 
		</c:if>
		<%
		} else {
		%>
		<a href="#" onclick="javascript:window.close();" id="tologin" title="退出" class="sign_out"></a></div>
		<%	
		}
		%>
        
      </div>
    </div>
      <div class="menu">
    	<div class="nav">
 			<ul class="ul_find">
						<li>
								<a href="#" onclick="viewMenu();subFormJs('index_initMenu.html')" id="li_page">首页</a>
						</li>
						<s:if test="topMenuList != null && topMenuList.size() > 0">
							<s:iterator id="cdlist" value="topMenuList">
								<li>
									<a style="cursor: pointer;" onclick="viewMenu();subFormJs('index_initMenu.html?gnmkdm=${GNMKDM }')" id="li_${GNMKDM }">${GNMKMC}</a>
								</li>
							</s:iterator>
						</s:if>
						<s:else>
							<div>
								<b><font color="red" size="2">该用户尚未开放任何功能模块权限，请联系管理员！</font>
								</b>
							</div>
						</s:else>
						
			</ul>
      </div>
      <div onmouseout="javascript:document.getElementById('morelist').style.display='none'" onmouseover="javascript:document.getElementById('morelist').style.display='block'" class="morediv">
     	 <p onclick="showhid('morelist');" class="more"></p>
	      <div style="display: none;" id="morelist" class="morelist">
	      	<ul id="moreUl">
	        </ul>
	      </div>
      </div>
    </div>
    </body>
    </html>