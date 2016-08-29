<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.zfsoft.util.date.TimeUtil"%>
<%@ page import="com.zfsoft.zfca.tp.cas.client.ZfssoConfig" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript"
			src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
		<script type="text/javascript"
            src="<%=systemPath %>/js/globalweb/password.js"></script>
			
		<% String usezfca = ZfssoConfig.usezfca;
			String goUrl = request.getContextPath() + "/xtgl/index_initMenu.html";
		%>	
		<% if(null !=usezfca && "1".equals(usezfca)){ %>
		<script language="javascript">
			window.location.href = "<%=ZfssoConfig.casurl%>/login?service=<%=java.net.URLEncoder.encode("http://"+ZfssoConfig.ywxtservername+goUrl, "utf-8")%>";
		</script>
		<%}%>
			
			
		<script type="text/javascript">
				
			 $(document).ready(function(){
                 refreshCode();
                 $("#mm").keyup(function(e){
                	 pwStrength(this.value);
                	 if(e.keyCode==13){
                		dl();
                	 }
                 });
                 $("#yzm").keyup(function(e){
                	 if(e.keyCode==13){
                		dl();
                	 }
                 });
                 $("#btn_dl").click(function(e){
                	 dl();
                 });
                 if("${logincount>=3}"=="true"){
                	 $("#yzmdiv").css("display","block");
                     $("#mm").val("");
                     }
                 
			 });
            function refreshCode(){
			    document.getElementById("yzmPic").src = _path + '/xtgl/login_code.html?time=' + new Date().getTime();
            }
			function isEmpty(dataid){
				if($('#' + dataid).val() != null && $('#' + dataid).val() != ''){
					return false;
				}else{
					return true;
				}
			}
			function showErrMsg(message){
				$(".errors").html("<span class='red'>"+message+"</span>");
			} 
			function hideMMshow(){
                $(".errors").html("");
            } 
			//登录
			function dl() {
				if(isEmpty('yhm')){
					alert('用户名不能为空！');
					return false;
				}
				if(isEmpty('mm')){
					alert('密码不能为空！');
					return false;
				}
				
				if(isEmpty('yzm') && $("#count").val()>=3){
					alert('验证码不能为空！');
					return false;
				}
							
				$("#btn_dl").attr("disabled","disabled");
				$(".errors").html("<span style='color:blue'>正在登录中，请稍候......</span>");
				$.post('<%=request.getContextPath() %>/xtgl/login_login.html', $('#form :input').serialize(), function(data){
					$("#btn_dl").removeAttr("disabled");
					if(data.success==false){
						showErrMsg(data.message);
						$("#count").val(data.logincount);
						 if(data.logincount>=3){
							 $("#yzmdiv").css("display","block");
							 $("#mm").val("");
						 }else{
							 $("#yzmdiv").css("display","none");
							 $("#mm").val("");
						 }
					}else{
						$(".errors").html("");
						location.href="<%=request.getContextPath() %>/xtgl/index_initMenu.html";
					}
				});
				//document.forms[0].submit();
			}

			function toRecruit(){
				var tag=$("<form method=\"post\"></form>");
				tag.attr("action","<%=request.getContextPath() %>/iframeweb/login_loginPage.html");
				tag.appendTo("body");
				tag.submit();
			}			
		</script>
		
	</head>
	<body class="login_bg">
		<div id="form">
			<input type="hidden" name="count" id="count" />
			<div class="login_main">
				<div class="login_logo">
					<h2>
						<img src="<%=stylePath%>logo/logo_school.png" />
					</h2>
					<h3>
						<img src="<%=stylePath%>logo/logo_hrm.png" />
					</h3>
				</div>
				<div class="login_left">
					<img class="login_pic" src="<%=stylePath%>logo/login_pic.png" />
				</div>
				<div class="login_right">
					<div class="login">
						<div class="user">
							<label for="">
								用户名：
							</label>
							<input name="yhm" id="yhm" type="text" class="text_nor"
								maxlength="20" style="width: 120px" value=""
								onchange="isNotChar(this);" />
						</div>
						<div class="passw">
							<label for="">
								密&nbsp;&nbsp;&nbsp;码：
							</label>
							<input name="mm" id="mm" type="password" value=""
								class="text_nor" maxlength="20" style="width: 120px" />
						</div>
						<div class="yzm" id="yzmdiv" style="display:none;">
							<label>
								验证码：
							</label>
							<input name="yzm" type="text" id="yzm" class="text_nor"
								style="width: 45px;" maxlength="4" value=""
								/>
							<img border="0" align="absmiddle" id="yzmPic" style="cursor:hand;" onclick="javascript:refreshCode();" name="yzmPic"
								width="70px" height="19px" />
						</div>

						<div class="btn">
							<button class="btn_dl" id="btn_dl" type="button"></button>
							<button class="btn_cz" type="reset"></button>
							<!-- 
							<a style="color: blue;vertical-align:bottom;text-decoration: underline;" 
								href="#" onclick="javascript:toRecruit();">招聘入口</a>
							 -->
						</div>
						<div class="errors" >
						</div>
						<div class="login_notice">
							<p>
								温馨提示：为了您的帐号安全，请及时修改您的初始密码。
							</p>
						</div>
					</div>
				</div>
				<!-- 版权信息 -->
				<%@include file="/WEB-INF/pages/globalweb/bottom.jsp" %>
			</div>
		</div>

		<script type="text/javascript">
			if('${message}' != null && '${message}' != ''){
				alert('${message}');
			}
		</script>
	</body>
</html>

