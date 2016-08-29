<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/globalweb/head/v4_url.ini"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        	<div class="piclink_01">
            <h3 style="z-index:0"><span class="title">常用入口</span><img src="<%=stylePath %>/images/blue/ico_help.gif" width="14" height="14" class="help" 
            			onmouseover="helpcon.style.display='block'"
						onmouseout="helpcon.style.display='none'" />
            	<p class="titlecon" id="helpcon" style="display: none;background: none repeat scroll 0 0 #F8FBFE;border: 1px solid #57ABF0;">
					提供快速进入页面的快捷方式，点"设置"可自定义常用功能，&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					点"<img src="/zfstyle_v4/images/blue/ico_linkup.gif" />
					<img src="/zfstyle_v4/images/blue/ico_linkdown.gif" />"可进行上移、下移
				</p>
            </h3>
            <script type="text/javascript">
				$(function(){
				    var movePx=0;
				    var index=0;
				    $(".down").live('click',function(){
				    	var len=$("#region").find("li").length;
				    	var liObj = $("#region").find("li");
					    if(index >= len -4) return;
				         if(!$("#region").is(":animated")){
					         if(index==0) movePx -= 12;
				        	 movePx=movePx-$(liObj[index]).height()-2;
				             $("#region").animate({marginTop:movePx+"px"},200);    
				             index++;
				          }   
				     });   
				     $(".up").live('click',function(){  
				    	 var len=$("#region").find("li").length;
				    	 var liObj = $("#region").find("li");  
				    	 if(index <= 0) return;
				         if(!$("#region").is(":animated")){
				        	 index--;
				        	 if(index==0) movePx += 12;
				        	 movePx=movePx+$(liObj[index]).height()+2;
                             $("#region").animate({marginTop:movePx+"px"},200);  
				         }   
				      });
				})
				</script>
            <div style="height: 430px;overflow: hidden;">
					<ul id="region">
					
					</ul>
			</div>
							<script type="text/javascript">
							requestData('${userId}','region');	
							</script>
						<div class="functionbut">
							<a id="teaLeftIndex" class="up"></a>
							<a class="down"></a>
							<a class="shez" title="添加常用功能"
								onclick="webLoad();"
								href="#">设置</a>
						</div>
  			</div>
