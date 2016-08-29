<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript"
			src="<%=systemPath %>/js/globalweb/comp/xtwh/wdyy.js"></script>
	<script type="text/javascript">

	function bindWdyyLink(){
		$("#wdyyCd").find("a[class = 'open_03']").click(function(){
            $("a.open_03").css("background","");
            $(this).css("background","#DCE8F8");
            var menuName_1 = $("#li_${gnmkdm}",self.parent.document).html();
            var id = $(this).closest("li").find(".cygn_delete").attr("id").replace('sc_','');
            preloadMenu(id);
            return false;
        });
   }

	
	$(document).ready(function() {
		var array = $('ul.hierarchy_03');
		$.each(array,function(i,n) {
			if (i != 0 && array[i] != "") {
				$(array[i]).css('display','none');
			}
		});
		$("a.open_03").click(function(){
			$("a.open_03").css("background","");
			$(this).css("background","#DCE8F8");
			var menuName_1 = $("#li_${gnmkdm}",self.parent.document).html();
			var menuName_2 = $(this).closest("div").find("h3").find("span").html();
			var menuName_3 = $(this).find("span").html();
			$("#menuLink",self.parent.document).html(menuName_1+" > "+menuName_2+" > "+menuName_3);
		});
		preloadMenu("${quickId}");
	});
	
			/*****************************
			showhideDiv
			Version: Ver 1.02
			Passed : XHtml 1.0, CSS 2.0, IE5.0+, FF1.0+, Opera8.5+
			Update : 2010-07-28
			*****************************/
			function getNext(o)
			{
				while(o)
				{
					if(o.nextSibling.nodeType==1)
					{return o.nextSibling;}
					o=o.nextSibling;
				}
				return o;
			}
	
			/**
			 * 隐藏左边菜单条中每个二级菜单的JS
			 * @param o
			 * @param id
			 * @return
			 */
			 function showhidediv(o){
					var target = $(o);
					var siblings = $(o).parent().siblings().find("h3.open");
					siblings.next().slideUp();
					siblings.removeClass("open").addClass("close");
					
					var style = target.attr("class");
					if(style == 'open'){
						target.removeClass().addClass("close");
						target.next().slideUp();
					}else{
						target.removeClass().addClass("open");
						target.next().slideDown();
					}
				}

			//异步加载--我的应用
			//2012-05-07 yjd
			$(document).ready(function() {
				var fjgndm=$("#fjgndm").val();
				var cddm={"fjgndm":fjgndm};
				var urlRoot="<%=jsPath %>";
				$.ajax({
					url:urlRoot+"/xtgl/wdyy_cxWdyy.html",
					type: "post",
					dataType:"json",
					data:cddm,
					success:function(wdyyList){	
						var htmls="";
						var cdBh="";
						if(wdyyList.length>0){
							for(var i=0;i<wdyyList.length;i++){
								htmls=htmls+"<li>"+
										"<a href='"+urlRoot+wdyyList[i]["dyym"]+"' target='xg_rightFrame' class='open_03'><span>"+wdyyList[i]["gnmkmc"]+"</span></a>"+
		 				 				"<a href='#' class='cygn_delete' id='sc_"+wdyyList[i]["gnmkdm"]+"' class='cygn_add' onclick='scAn(this);return false;'></a>"+
					 				 "</li>";
								cdBh=wdyyList[i]["gnmkdm"];
								cshAn(cdBh);
							}
						}else{
							htmls=htmls+"<li id='zwszyy'>"+
					 					"<a target='framecon'><span>暂未设置应用</span></a>"+
		 				 		"</li>";
						}
						var wdyyObj=$("#wdyyCd");
						wdyyObj.children().remove();
						wdyyObj.append(htmls);

						bindWdyyLink();
					}
				
				});

			});

	function qxyy(obj){
		var curA = jQuery(obj);
		var zjID = curA.attr('id').replace('sc_','zj_');
		jQuery('#'+zjID).attr('class','cygn_add');
		curA.parent('li:first').remove();
		scWdyy(curA.attr('id').replace('zj_',''));
	}

	
	function szyy(obj){
		
		var curA = jQuery(obj);
		var curLi = curA.parent('li:first');

		if (curA.attr('class') != 'cygn_disabled'){
			curA.attr('class','cygn_disabled');
			var newLi = jQuery('<li>'+curLi.html()+'</li>');
			var tempA = newLi.find('a').eq(1);
			tempA.attr('id',curA.attr('id').replace('zj_','sc_'));
			tempA.attr('class','cygn_delete');
			tempA.attr('name','wdyy_tag');
			jQuery('#wdyyCd').append(newLi);
			jQuery('#'+tempA.attr('id')).attr('onclick',"");
			jQuery('#'+tempA.attr('id')).bind('click',function(){qxyy(this)});
			zjWdyy(tempA.attr('id').replace('sc_',''));
		} else {
		}
	}

	function preloadMenu(menuId){
		if(menuId.length==0){//默认加载第一菜单内容
			var tag = $("a[name='tag']:first");
			$(tag).closest("div").find("h3").click();
			var url = $(tag).prev().attr("href");
			//$(tag).prev().css("background","#DCE8F8");
			self.parent.frames['xg_rightFrame'].location.href=url;
			$(tag).prev().click();
		}else{
			var tag = $("a[name='tag'][id$='"+menuId+"']");
			$(tag).closest("div").find("h3").click();
			var url = $(tag).prev().attr("href");
			//$(tag).prev().css("background","#DCE8F8");
			self.parent.frames['xg_rightFrame'].location.href=url;
			$(tag).prev().click();
			$("#quickId",self.parent.document).val("");//调用过后，清除快速按钮的参数
		}
	}

</script>
</head>
	<body >
		<div class="textlink" >
			<h2><span onclick="xsWdyy()" style="cursor:pointer;">我的应用</span></h2> 
				 		<ul style="display:block;" class="hierarchy_03" id="wdyyCd">
				 				 <li>
				 				 		<a href="demo/demo_01.html" target="framecon"><span>应用加载中...</span></a>
				 				 		<!-- <a href="#" class="cygn_delete"></a> -->
				 				 </li>
						</ul>
						<div class="bot_cygn"></div>
		</div>
		<s:if test="menuList != null && menuList.size() > 0">
			<s:iterator var="menu" value="menuList" status="sta">
						<s:if test="#menu.sjMenu==null || #menu.sjMenu.isEmpty()">
							<div class="textlink" >
				 				<h3 onclick="showhidediv(this);">
				 				<a href="<%=jsPath %>${menu.DYYM}" 
				 				 	   target="xg_rightFrame" >
				 				 	   <span>${menu.GNMKMC}</span>
				 				 	</a>
				 				</h3>
				 				<ul style="display:none;" class="hierarchy_03"></ul>
				 			</div>
				 		</s:if>
				 		<s:if test="#menu.sjMenu!=null && !#menu.sjMenu.isEmpty()">
							 <div class="textlink" >
							 		
							 		<h3 onclick="showhidediv(this);" class="close"><span>${menu.GNMKMC}</span></h3>
							 		<ul style="display:block;" class="hierarchy_03">
							 			<s:iterator var="zmenu" value="#menu.sjMenu" >
							 				 <li>
							 				 	<a href="<%=systemPath %>${zmenu.DYYM}" 
							 				 	   target="xg_rightFrame" class="open_03">
							 				 	   <span>${zmenu.GNMKMC}</span>
							 				 	</a>
								 				 <a href="#" id="zj_${zmenu.GNMKDM}" class="cygn_add" name="tag" 
								 				 onclick="zjAn(this);bindWdyyLink();return false;"></a>
								 			</li>
							 			</s:iterator>
									</ul>
						      </div>
						</s:if>
			</s:iterator>
		</s:if>
		<s:else>
			<div class="textlink" id="">
				<br/>
				<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;暂无任何功能模块信息！</font>
			</div>
		</s:else>
       
       <script type="text/javascript" defer="defer">
       		function ForceWindow(sTarget) {//
	    		if (sTarget == null) {
	    			sTarget = "mainFrame";
	    		}
	    		this.r = document.documentElement;
	    		this.f = document.createElement("FORM");
	    		this.f.target = sTarget;
	    		this.f.method = "post";
	    		this.r.insertBefore(this.f, this.r.childNodes[0]);
    		}
       		ForceWindow.prototype.open = function (sUrl) {//
       			this.f.action = sUrl;
       			this.f.submit();
       		};
       		    	       
			//var myWindow = new ForceWindow("framecon");
			//myWindow.open("cdgl_rightContent.do");
	</script>
	<input type="hidden" id="fjgndm" name="fjgndm" value="${gnmkdm}" />
	</body>
    </html>