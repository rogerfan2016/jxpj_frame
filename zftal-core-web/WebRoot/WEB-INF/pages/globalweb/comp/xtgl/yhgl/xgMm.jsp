<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>密码修改</title>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/inputPrompt.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
	<script type="text/javascript">		   
		function formSubmit(){
			var mm = $('#mm').val();
			var nmm = $('#nmm').val();
			if (inputResult() && checkInputNotNull('ymm!!mm!!nmm')){ 
				if(mm!=nmm){
					alert("两次密码输入不一致！");
					return false;
				}
			/**	if(mm==yhm){
				   alert("用户名密码不能相同！");
					return false;
				} 
				document.forms[0].submit(); **/
				var param = $("#form1").serialize();
				
				$.post(_path+'/xtgl/yhgl_xgMm.html',param,function(data){
					if(data.success){
						var text ="<span style='color:red;font-size:18px'>"
						+data.html
						+"</span>"
		    			$("#remind").html(text);
		    			setTimeout("iFClose()",1000);
					}else{
						showDownError($("#ymm")[0],data.html);
					}
				},"json");
			}
		}

		function checkMm(obj){
			var mm = $('#mm').val();
			 var yhm_ = $('#myName').val();
			  var reg = /([0-9].*[a-zA-Z])|([a-zA-Z].*[0-9])/; //正则表达式必须包含数字和字母
			if(mm.length <6){
				showDownError(obj,'密码长度不能为空并且范围6-20位！');
			}
			//添加用户名密码不能相同校验
			if(mm==yhm_){
			   showDownError(obj,'用户名密码不能相同！');
			}
			if(!reg.test(mm)){
			    showDownError(obj,'密码必须包含字母和数字！');
			}
		}


		function checkNmm(obj){
			var mm = $('#mm').val();
			var nmm = $('#nmm').val();
			 if(mm!=nmm){
				showDownError(obj,'两次密码输入不一致！');
			}
		}
		

		
		
	</script>
  </head>
  
  <body>
	<s:form method="post" action="yhgl_xgMm.html" theme="simple" id="form1">
	 <input type="hidden" name="doType" value="save"/>
	  <input type="hidden" id="myName" name="myName" value="${user.yhm}" />
	
  	<div class="tab">
  	<table width="100%" border="0" class="formlist">	
		<thead>
			<tr>
				<th colspan="2"><span>密码修改</span><div id="remind"></div></th>			
			</tr>
		</thead>
		<tbody>
			<tr>
				<th align="right">
					<span class="red">*</span>原密码
					
				</th>
				<td>
				 <s:password maxlength="20" name="ymm" id="ymm" cssStyle="width:154px" ></s:password> 
				</td>
			</tr>
			<tr>
				<th align="right">
					<span class="red">*</span>新密码
				</th>
				<td>
					 <s:password maxlength="20" name="mm" id="mm" cssStyle="width:154px" 
	         			onblur="checkMm(this);"  onfocus="showDownPrompt(this,'密码必须包含字母和数字,并且长度不能小于6位');"></s:password> 
				</td>
			</tr>
			<tr>
				<th align="right">
		    		<span class="red">*</span>重复新密码
				</th>
				<td>
		    		 <s:password maxlength="20" name="nmm" id="nmm" cssStyle="width:154px"
	        	onblur="checkNmm(this);"  onfocus="showDownPrompt(this,'密码必须包含字母和数字,并且长度不能小于6位');"></s:password> 
				</td>
			</tr>
			 <tr>
	      <td colspan="3" >
	      &nbsp;
	      </td>
	      </tr>
	    </tbody>
	    <tfoot>
	    	<tr>
	    		<td colspan="2">
	    		<div class="bz">"<span class="red">*</span>"为必填项</div>
	    			<div class="btn">
					<button type="button" class="" onclick="formSubmit();return false;">
							修改
					</button>
					<button type="button" class="" onclick="iFClose();return false;">
							关闭
					</button>
	    			</div>
	    		</td>
	    		
	    	</tr>
	    </tfoot>
	    </table>
    </div>
   </s:form>
   <s:if test="message!='' && message != null">
		<script type="text/javascript">

		alert('${message}','',{'clkFun':function(){

			//更新成功,跳转到登录页面
			if(${ok} != null && ${ok} != "" && ${ok} != "false"){
				iFClose();
				jQuery(window.parent.document).find('#tologin').click();
			}
			}

		});
			
		</script>
	</s:if>
  </body>
</html>
