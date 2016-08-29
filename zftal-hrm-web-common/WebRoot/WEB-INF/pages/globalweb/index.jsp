<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript">
		function getElementByClassName(o,tagName,className){
				var elements = o.getElementsByTagName(tagName);
				
				for (var i = 0 ; i < elements.length ; i++){
					if (elements[i].className == className){
						return elements[i];
					}
				}
				return null;
			}
		
		/**
		 * 隐藏左边菜单条的JS
		 * @return
		 */
		 $(function(){
	    		$(".btn_hide_on>button").click(function(){
	    			$(".btn_hide_off").show();
	    			$(".btn_hide_on").hide();
	    			$("#left").hide();
	    			$("#right").removeClass("typeright").addClass("typeright_hidden");
	    		});
	    		$(".btn_hide_off>button").click(function(){
	    			$(".btn_hide_off").hide();
	    			$(".btn_hide_on").show();
	    			$("#left").show();
	    			$("#right").removeClass("typeright_hidden").addClass("typeright");
	    		});
	    		
	    		$("a[id^='help']").click(function(){
					window.open("<%=request.getContextPath()%>/help/${gnmkdm}/index.htm");
				});
	    	});
		</script>
	</head>
	<body>
		<div class="mainbody type_mainbody">
			<!-- TOP菜单的加载 -->
			<div class="topframe" id="topframe">
				<jsp:include page="top.jsp" flush="true"></jsp:include>
			</div>

			<div class="type_mainframe" style="z-index:0">
				<div class="typeleft floatleft" id="left">
					<iframe name="xg_leftFrame" style="width: 100%;" id="xg_leftFrame"
						marginwidth="0" marginheight="0" framespacing="0" frameborder="0"
						scrolling="no" src="index_leftMenu.html?gnmkdm=${gnmkdm }&quickId=${quickId}"
						onload="this.height=0;var fdh=(this.Document?this.Document.body.scrollHeight:this.contentDocument.body.offsetHeight);this.height=(fdh>800?fdh:800)"></iframe>
				</div>

				<div class="btn_hide_on" id="leftBotton">
					<button id="changeid"></button>
				</div>
				<div class="btn_hide_off" style="display: none;" id="rightBotton">
					<button></button>
				</div>

				<div class="typeright floatright" id="right">
					<!--选项卡-->
					<!---->
					<!--内容区主体-->
					<div class="typecon">
					    
            <div class="tab_cur">
                <p class="location">
                    <em>您的当前位置：</em><a id="menuLink"></a>
                </p>
                <!--<p class="help">
                	<a href="#" id="help">帮助手册</a>
                </p>-->
            </div>
						<iframe id="xg_rightFrame" name="xg_rightFrame" class="framecon"
							allowTransparency="true" src="index_center.html" width="100%"
							frameborder="0" marginwidth="0" marginheight="0"
							onload="this.height=300;var fdh=(this.Document?this.Document.body.scrollHeight:this.contentDocument.body.offsetHeight);this.height=(fdh>600?fdh:610)"
							scrolling="no">
						</iframe>
						<script type="text/javascript">
							function reinitIframe(){
								var iframe = document.getElementById("xg_rightFrame");
	
								try{
									var bHeight = iframe.contentWindow.document.body.scrollHeight;
									var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
									var height = Math.max(bHeight, dHeight);
									height = Math.max(height, 600);
									iframe.height =  height;
								}catch (ex){}
	
							}
	
							window.setInterval("reinitIframe()", 1000);
						</script>
					</div>
				</div>
			</div>

			<!-- 底部页面加载 -->
			<div class="botframe" id="botframe">
				<!-- 版权信息 -->
				<%@include file="/WEB-INF/pages/globalweb/bottom.jsp" %>
			</div>
		</div>
	</body>
</html>
