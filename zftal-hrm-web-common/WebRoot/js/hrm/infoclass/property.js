function initSaveEvent(){
	//保存
	$("#save").click(function(){
		if( $("#name").val() == "" ) {
			alert("属性名称不得为空，请重新输入！");
			return false;
		}
		
		if( $("#fieldName").val() == "" ) {
			alert("字段名不得为空，请重新输入！");
			return false;
		}
		if( $("select[name='fieldType']").val() == "" ) {
			alert("字段类型不得为空，请重新输入！");
			return false;
		}
		$("#windown-content").unbind("ajaxStart");
		var dis = $("form[id='form2']").find(":disabled");
		$(dis).removeAttr("disabled");
		$.post(_path+'/infoclass/infoproperty_save.html', $("form[id='form2']").serialize(), function(data){
			$(dis).attr("disabled","disabled");
			var callback = function(){
				$("form:first").submit();
				//window.location.reload();
			};
			
			processDataCall(data, callback);
		}, "json");
		
		return false;
	});
	
	$("#cancel").click(function(){
		divClose();
	});
}

function initVirtualColumnEvent(){
	$("input[name='virtual']").click(function(){
		if(!$(this).is(":checked")){
			return false;
		}
		loadVirtualColumnInfo();
	});
}
function loadVirtualColumnInfo(){
	$("#windown-content").unbind("ajaxStart");
	$.post(_path+'/infoclass/infoproperty_virtualStep.html', $("form[id='form2']").serialize(), function(data){
		if(data.success){
			$("form[id='form2'] tbody").find("tr[name!='step1']").remove();
			$("form[id='form2'] tbody").find("tr:last").after(data.result);
			initColumnTypeEvent();
			initDefInputStyleEvent();
			loadColumnTypeInfo();
			if($("form[id='form2']").find("input[name='guid']").val()!=''){
				$("input[name='virtual']").attr("disabled","disabled");
			}
			changeReferInput();
		}else{
			alert(data.text);
		}
	}, "json");
}
function initColumnTypeEvent(){
	$("select[name='fieldType']").change(function(){
		loadColumnTypeInfo();
	});
}

function loadColumnTypeInfo(){
	if($("select[name='fieldType']").val()==''){
		return false;
	}
	$("#windown-content").unbind("ajaxStart");
	$.post(_path+'/infoclass/infoproperty_columnStep.html', $("form[id='form2']").serialize(), function(data){
		if(data.success){
			$("form[id='form2'] tbody").find("tr[name='step3']").remove();
			$("form[id='form2'] tbody").find("tr:last").after(data.result);
		}else{
			alert(data.text);
		}
	}, "json");
}
function initDefInputStyleEvent(){
	$("#getDefInputStyle").click(function(){
		getDefInputStyle();
	});
}
function getDefInputStyle(){
	$.post(_path+'/infoclass/infoproperty_getDefInputStyle.html', $("form[id='form2']").serialize(), function(data){
		if(data.success){
			$("#defValIpt").html(data.result);
		}else{
			alert(data.text);
		}
	}, "json");
	
}
function changeReferInput(){
	 if($("select[name='referFunc']").val()=="to_number"){
		 $("#refer0").hide();
		 $("#refer0").removeAttr("name");
		 $("#refer1").show();
		 $("#refer1").attr("name","refer");
	 }else{
		 $("#refer1").hide();
		 $("#refer1").removeAttr("name");
		 $("#refer0").show();
		 $("#refer0").attr("name","refer");
	 }
}