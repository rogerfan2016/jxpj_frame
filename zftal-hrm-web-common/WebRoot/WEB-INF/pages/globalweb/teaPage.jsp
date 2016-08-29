<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	<script type="text/javascript">
	$(function(){
		//loadMessage();
		loadNotice();
		loadPendingAffair();
		loadFileDown();
		loadStaffInfo();
		//setInterval("loadMessage()",60*1000);
		
		$("a[id^='usual_']").click(function(){
			quickMenu(this);
		});
		
		$("#hideoff").click(function(){
		   	$("#mainframe2").attr('class','type_mainframe'); 
		    $("#right").attr('class','typeright floatright'); 
		    $("#left").attr('class','typeleft floatleft'); 
		    $("#left").css('display','');   
		    $("#hideon").css('display','');
		    $("#hideoff").css('display','none');
		})
		
		$("#hideon").click(function(){
		    $("#mainframe2").attr('class','type_mainframe_hidden');
		    $("#left").css('display','none');   
		    $("#right").attr('class','typeright_hidden');
		    $("#hideon").css('display','none');
		    $("#hideoff").css('display','');
		})
	});

	function loadPendingAffair(){
		var divContent = $("div#todo");
		$("#windown-content").unbind("ajaxStart");
		$.post(_path+'/pendingAffair/pendingAffair_indexList.html','',function(data){
			if(data.success){
				createPendingAffairList(data.result,divContent);
			}
		},"json");
	};
	
/*	function loadMessage(){
		var divContent = $("div#tips");
		$("#windown-content").unbind("ajaxStart");
		$.post(_path+'/message/message_listData.html','',function(data){
			if(data.success){
				createMessageList(data.result,divContent);
			}
		},"json");
	};*/
	
/*	function loadMessage(){
		var divContent = $("div#grrcul");
		$("#windown-content").unbind("ajaxStart");
		$.post(_path+'/grgk/overAll_view.html','',function(data){
			if(data.success){
				createMessageList(data.result,divContent);
			}
		},"json");
	};*/
	

	function createPendingAffairList(data,div){
		var html = "<ul></ul>";
		var content = $(html);
		$.each(data,function(){
			var d = this;
			$(content).append("<li><a href='#' id='usual_"+d.menu+"'>"+d.affairName+"</a></li>");
			$(content).find("a:last").data("data",d);
		});
		if($(content).find("li").length==0){
			$(content).append("<li><span>暂无信息</span></li>");
		}

		$(content).find("a").click(function(){
			var data0 = $(this).data("data");
			var menu0 = data0.menu;
			if(data0.menu==null){
				alert("ID缺失");
				return false;
			}
			var topCode = menu0.substring(0,3);
			//$("[name='quickId']:hidden").val(menu0);
			//$("a[id='li_"+topCode+"']").click();
			quickMenu(this);
		});
		$(div).find("ul").remove();
		$(div).find("div").append($(content));
	}
	
	function createMessageList(data,div){
		var html = "<ul></ul>";
		var content = $(html);
		$.each(data,function(){
			var d = this;
			$(content).append("<li><a href='#'>"+d.title+"</a></li>");
			$(content).find("a:last").data("data",d);
		});
		if($(content).find("li").length==0){
			$(content).append("<li><span>暂无信息</span></li>");
		}
		$(content).find("a").click(function(){
			var data = $(this).data("data");
			if(data.guid==null){
				alert("ID缺失");
				return false;
			}
			var callback = function(){
				//loadMessage();
			};
			showWindow111("查看",720,300,"<%=request.getContextPath()%>/message/message_info.html?msg.guid="+data.guid,callback);
		});
		$(div).find("ul").remove();
		$(div).append($(content));
	}
	function showWindow111(title,width,height,url,handler){
	    
	    ymPrompt.win({message:url,
	                  width:width,
	                  height:height,
	                  title:title,
	                  maxBtn:true,
	                  minBtn:true,
	                  iframe:true,
	                  showShadow:false,
	                  useSlide:true,
	                  maskAlphaColor:"#FFFFFF",
	                  maskAlpha:0.3,
	                  handler:handler
	            }
	    );
	}
	function loadNotice(){
		var divContent = $("div#notice");
		$("#windown-content").unbind("ajaxStart");
		$.post(_path+'/message/noticeView_listData.html','',function(data){
			if(data.success){
				createNoticeList(data.result,divContent);
			}
		},"json");
	};
	
	function createNoticeList(data,div){
		var html = "<ul></ul>";
		var content = $(html);
		$.each(data,function(){
			var d = this;
			if(d.top==1){
				$(content).append("<li><a guid='"+d.guid+"' href='#'><font color='red'>【置顶】</font>"+d.title+"</a><span class='time'>"+d.createTime.substr(0,10)+"</span></li>");
			}else{
				$(content).append("<li><a guid='"+d.guid+"' href='#'>"+d.title+"</a><span class='time'>"+d.createTime.substr(0,10)+"</span></li>");
			}
			$(content).find("a:last").data("data",d);
		});
		if($(content).find("li").length==0){
			$(content).append("<li><span>暂无信息</span></li>");
		}
		$(content).find("a").click(function(){
			var guid = $(this).attr("guid");
			if(guid==null){
				alert("ID缺失");
				return false;
			}
			var callback = function(){
				//loadMessage();
			};
			showWindow111("查看",720,400,"<%=request.getContextPath()%>/message/noticeView_info.html?notice.guid="+guid,callback);
		});
		$(div).find("ul").remove();
		$(div).find("div").append($(content));
	}
	
	function loadStaffInfo(){
		var divContent = $("#staffinfo");
		$("#windown-content").unbind("ajaxStart");
		$.post(_path+'/message/noticeView_StaffInfolist.html','',function(data){
			if(data.success){
				createStaffInfoList(data,divContent);
			}
		},"json");
	};
	
	function createStaffInfoList(data,div){
		var html = "<ul></ul>";
		var content = $(html);
		
		$(content).append("<li class='liheight' title='"+ data.gh +"' style='width:180px;margin-top:2px;'>职工号：<span style='margin-left:8px'></span>"+ data.gh +"</li>");
		$(content).append("<li class='liheight' title='"+ data.xm +"' style='width:180px;'>姓     &nbsp&nbsp名：<span style='margin-left:8px' class='marginlength'></span>"+ data.xm +"</li>");
		$(content).append("<li class='liheight' title='"+ data.xb +"' style='width:180px;'>性      &nbsp&nbsp别：<span style='margin-left:8px' class='marginlength'></span>"+ data.xb +"</li>");
		$(content).append("<li class='liheight' title='"+ data.bm +"' style='width:180px;'>部      &nbsp&nbsp门：<span style='margin-left:8px' class='marginlength'></span>"+ data.bm +"</li>");
		$(content).append("<li class='liheight' title='"+ data.zc +"' style='width:180px;'>专业技术职务：<span style='margin-left:8px' class='marginlength'></span>"+ data.zc +"</li>");
		$(content).append("<li class='liheight' title='"+ data.zzmm +"' style='width:180px;'>政治面貌：<span style='margin-left:8px' class='marginlength'></span>"+ data.zzmm +"</li>");
	
		$(div).find("ul").remove();
		$(div).append($(content));
		
		$("#zptd").html("<a onclick='staffInfoShow()'>" + data.zp + "</a>");
	}
	
	function loadFileDown(){
		var divContent = $("div#download");
		$("#windown-content").unbind("ajaxStart");
		$.post(_path+'/message/file_viewList.html','',function(data){
			if(data.success){
				createFileList(data.result,divContent);
			}
		},"json");
	};
	
	function createFileList(data,div){
		var html = "<ul></ul>";
		var content = $(html);
		$.each(data,function(){
			var d = this;
			if(d.top==1){
				$(content).append("<li><a guid='"+d.fileId+"' href='#'><font color='red'>【置顶】</font>"+ "(" + d.fileType + ")+" + d.name+"</a></li>");
			}else{
				$(content).append("<li><a guid='"+d.fileId+"' href='#'>"+  "(" + d.fileType + ")+" + d.name+"</a></li>");
			}
			$(content).find("a:last").data("data",d);
		});
		if($(content).find("li").length==0){
			$(content).append("<li><span>暂无信息</span></li>");
		}
		$(content).find("a").click(function(){
			var guid = $(this).attr("guid");
			if(guid==null){
				alert("ID缺失");
				return false;
			}
			var url = "<%=request.getContextPath() %>/file/attachement_download.html?guId=" + guid;
			window.open( url, "400", "300", true);
		});
		$(div).find("ul").remove();
		$(div).append($(content));
	}
	
	function quickMenu(obj){
		var ids = $(obj).attr("id").split("_");
		var targetCode = ids[1];
		var topCode = targetCode.substring(0,3);
		$("[name='quickId']:hidden").val(targetCode);
		$("a[id='li_"+topCode+"']").click();
	}
	function webLoad(){
		 $(".mainframe").css('display','none');
		  $("#nyxs").css('display','block');
		var content = '<form id="form" method="post" target="framecon" action="<%=request.getContextPath()%>/common/common_toListAll.html">';
		content +='    </form>';
		$('body').append(content);
		$('#form').submit();
		$('#form').remove();
	}

	function listAll(){
		var content = '<form id="form" method="post" target="framecon" action="<%=request.getContextPath()%>/common/common_listAll.html">';
		content +=' 	<input type="hidden" name="indexModel.gnmkdm" value="" />';
		content +=' 	<input type="hidden" name="indexModel.gnmkmc" value="" />';
		content +='    </form>';
		$('body').append(content);
		$('#form').submit();
		$('#form').remove();
	}
	
	function tipsRefresh(msg,suc){
		if(suc == 'false'){
			tipsWindown("提示信息","text:"+msg,"340","120","true","","true","id");
		}else{
			listAll();
		}
		
		$("#window-sure").click(function() {
			divClose();
		});
	}
	
	function requestData(params,divId){
		var successCall = function(d){
			try{
	    		var data = $.parseJSON(d);
	    		if(data.success==false){
	    			tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
	    			$("#window-sure").click(function() {
	    				alertDivClose();
					});
	    		}
			}catch(e){
				$("#"+divId).empty();
				$("#"+divId).append(d);
			}
		};
		$("#windown-content").unbind("ajaxStart");
		$.ajax({
			url:_path + "/common/common_list.html",
			type:"post",
			data:params,
			cache:false,
			dataType:"html",
			success:successCall
		});
	}
	</script>
	<style type="text/css">
	.liheight{
	   text-overflow:ellipsis;
	   white-space:nowrap;
	   overflow: hidden;
	   }
	</style>
</head>
<body>
<div class="mainbody type_mainbody">
	<!-- TOP菜单的加载 -->
	<div class="topframe" id="topframe" style="z-index:1;">
	     <jsp:include page="top.jsp" flush="true"></jsp:include>
	</div>
	
    <div class="mainframe" id="mainframe">
		
		<div class="leftframe" id="leftframe">
        	<!-- LEFT 菜单的加载 -->
			<jsp:include page="teaLeftIndex.jsp" flush="true"></jsp:include>
        </div>
		<!-- RIGHT页面加载 -->
		<div class="rightframe" id="rightframe">
			<jsp:include page="teaRightIndex.jsp" flush="true"></jsp:include>
		</div>
    </div>
    <div id="nyxs" style="display: none;">
				<div class="type_mainframe" id="mainframe2">
					<div class="notice" style="display: none;">
						<h3>
							<span>系统帮助 </span>
						</h3>

					</div>
					<div class="typeleft floatleft" id="left">
						<div class="piclink_01" style="width: auto">
							<h3>
								<span class="title">常用功能</span>
								<img src="<%=stylePath %>/images/blue/ico_help.gif" width="14"
									height="14" class="help"/>
							</h3>
							<ul id="region2"></ul>
							<script type="text/javascript">
							requestData('${userId}','region2');	
							</script>
							<div class="functionbut">
							</div>

						</div>
					</div>
					<div class="btn_hide_on" id="hideon">
						<button></button>
					</div>
					<div class="btn_hide_off" style="display: none;" id="hideoff">
						<button></button>
					</div>

					<div class="typeright floatright" id="right" style="display: block">
						<div class="tab_cur">
							<p class="location" id="clickto">
								<em>您的当前位置：</em>首页 > 常用功能设置
							</p>
						</div>
						<div class="typecon">
							<iframe name="framecon" id="framecon" src="content.jsp"
								width="100%" frameborder="0" marginwidth="0" marginheight="0"
								onload="this.height=1600" scrolling="no">
							</iframe>
						</div>
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
