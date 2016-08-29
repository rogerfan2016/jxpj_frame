//function imageMouseover(content,name) {
//	var fileDiv = $(content).closest(".img_"+name);
//	var form=$(fileDiv).find('#imageCommitBtn_'+name).closest("form");
//	form.attr("target","uploadimage_iframe_"+name);
//}

function getImage(content,name) {
	return getImage(content,name,100,200) ;
}

function getImage(content,name, size,width) {
	
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	var fileSize = 0;
	var fileInput=$(content)[0];
	var imageDiv = $(content).closest(".img_"+name);
	var type=imageDiv.attr("name");
	var input = $("#"+name);
	var form=$(imageDiv).find('#imageCommitBtn_'+name).closest("form");
//	imageMouseover(content,name);
	if (isIE && !fileInput.files) {
		//var filePath = $(fileInput).val();
		//var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
		//var file = fileSystem.GetFile(filePath);
		//fileSize = file.Size;
	} else {
		fileSize =fileInput.files[0].size;
		console.log(fileSize);
	}
	
	fileSize = fileSize / 1024;
	
	if (fileSize > size) {
		showWarning("附件不能大于"+size+"k");
		$('#window-sure').click(function() {
			divClose();
		});
		return;
	}
	
	startImageUp();

	// 获取文件上传key
	function startImageUp() {
		if ($(imageDiv).find('#imageCommitBtn_'+name).val() == ''
				|| $(imageDiv).find('#imageCommitBtn_'+name).val() == null) {
			showWarning('请选择上传的图片');
			$('#window-sure').click(function() {
				divClose();
			});
			return false;
		}
		$.post(_path + '/file/file_startImageUp.html', "fileName="+$(imageDiv).find('#imageCommitBtn_'+name).val(), function(data) {
			if (data.success) {
				$(data).find('#p_in_'+name).css('width', '0%');
				uploadImage(data.key);
				readImagePrograss(data.key);
			} else {
				showWarning(data.text);
				$('#window-sure').click(function() {
					divClose();
				});
			}
		});
	}
	// 上传文件
	function uploadImage(key) {
		var fileName=$(imageDiv).find('#imageCommitBtn_'+name).val();
		var id=$(imageDiv).find('#imageCommitBtn_'+name).attr("id");
		var url=_path +'/file/file_uploadimage.html?fileProp.width='+width+'&fileProp.currentName=imageCommit_'+name+'&fileProp.maxSize='+size+'&key=' + key + '&fileName='
			+fileName+'&fileGuid=' + input.val()+'&newSession=false';
		jQuery.ajaxFileUpload({
			  url:url,//服务器端程序
			  secureuri:false,
			  fileElementId:id,
			  success:function(data,type){
				//if (type=='success'){
				//	alert("上传成功");
				//}
			  }
		});
//		var fileName=$(imageDiv).find('#imageCommitBtn_'+name).val();
//		form.attr( "action",
//				_path +'/file/file_uploadimage.html?fileProp.currentName=imageCommit_'+name+'&fileProp.maxSize='+size+'&key=' + key + '&fileName='
//				+fileName+'&fileGuid=' + input.val()+'&newSession=false');
//		form.submit();
//
//		form.removeAttr("target");
	}
	// 读取文件进度
	function readImagePrograss(key) {
		$.post(_path + '/file/file_readProgress.html?key=' + key, null,
				function(data) {
					if (data.success) {
						if(data.exceptionMaxSize){
							showWarning(data.errorMessage);
							
							$('#window-sure').click(function() {
								divClose();
							});
							return false;
						}
						updateImageProcess(data.key, data.len, data.total, data.guid,
								data.isComplete);
					}
				});
	}
	
	// 更新进度
	function updateImageProcess(key, len, total, guid, isComplete) {
		if (total == 0) {
			readImagePrograss(key);
			return;
		}
		$(imageDiv).find('#p_in_'+name)
				.css('width', (Math.round(len / total * 100)) + '%');
		if (isComplete) {
			if (len >= total) {
				$(imageDiv).find('#image_'+name).attr("src",_path
						+ '/file/file_'+type+'.html?fileGuid=' + guid+"&date="+new Date().getTime());;
				input.val(guid);
				alert("上传成功");
			}
		} else {
			readImagePrograss(key);
		}
	}
}