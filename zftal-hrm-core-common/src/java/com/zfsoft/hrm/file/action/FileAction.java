package com.zfsoft.hrm.file.action;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.dto.FileProp;
import com.zfsoft.hrm.file.dto.Progress;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.FilePathUtil;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.util.base.StringUtil;
/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-8-9
 * @version V1.0.0
 */
public class FileAction extends HrmAction {  

	private static final long serialVersionUID = 6649027352616232244L;  
    
    private String fileName;
    
    private String fileGuid;
    
    private FileProp fileProp;
    
	public FileProp getFileProp() {
		return fileProp;
	}

	public void setFileProp(FileProp fileProp) {
		this.fileProp = fileProp;
	}
    
	public String getFileGuid() {
		return fileGuid;
	}

	public void setFileGuid(String fileGuid) {
		this.fileGuid = fileGuid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 上传图片页面
	 * @return page view
	 */
	public String list() {
		String path = getRequest().getSession().getServletContext().getRealPath("/");
		path=FilePathUtil.resetPath(path);
		if(fileName==null||("").equals(fileName)){
			path+="upload"+File.separator+"default.jpg";
		}else{
			path+="upload"+File.separator+fileName;
		}
		return "list";
	}
	
	/**
	 * 初始化图片上传，生成进度对象
	 * @return page view
	 */
	public String startImageUp() {
		String[] suffixs=new String[]{"jpg","gif","png","bmp"};
		//获取文件后缀名
		try{
			UploadFileUtil.checkedFileName(fileName, suffixs);
		}catch(Exception e){
			this.setErrorMessage("文件类型不支持,仅限于"+Arrays.toString(suffixs));
			this.getValueStack().set("data", getMessage());
			return DATA;
		}
		// 缓存progress对象的key值
		String key = Integer.toString(getRequest().hashCode());
		// 新建当前上传文件的进度信息对象
		Progress p = new Progress();
		// 缓存progress对象
		UploadFileUtil.keyMap.put(key, p);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("key", key);
		this.getValueStack().set("data", map);
		return DATA;
	}
	/**
	 * 初始化文件上传，生成进度对象
	 * @return page view
	 */
	public String startFileUp() {
		String[] suffixs=new String[]{"doc","docx","txt","xls","xlsx","jpg","gif","png","bmp","pdf"};
		//获取文件后缀名
		try{
			UploadFileUtil.checkedFileName(fileName, suffixs);
		}catch(Exception e){
			this.setErrorMessage("文件类型不支持,仅限于"+Arrays.toString(suffixs));
			this.getValueStack().set("data", getMessage());
			return DATA;
		}
		// 缓存progress对象的key值
		String key = Integer.toString(getRequest().hashCode());
		// 新建当前上传文件的进度信息对象
		Progress p = new Progress();
		// 缓存progress对象
		UploadFileUtil.keyMap.put(key, p);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("key", key);
		this.getValueStack().set("data", map);
		return DATA;
	}
	
	/**
	 * 上传图片
	 * @return page view
	 * @throws IOException 
	 */
	public String uploadimage() throws IOException {
		fileProp.setMaxSize(fileProp.getMaxSize()*1024);
		fileProp.setFileType("image");
		fileProp.setKey(getRequest().getParameter("key"));
		try {
			UploadFileUtil.upload(getRequest(), getResponse(),fileProp);
		} catch (IOException e) {
			LOG.error("上传文件发生异常,错误原因 : " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 上传附件
	 * @return page view
	 * @throws IOException 
	 */
	public String uploadattachement() throws IOException {
		fileProp.setMaxSize(fileProp.getMaxSize()*1024*1024);
		fileProp.setKey(getRequest().getParameter("key"));
		try {
			UploadFileUtil.upload(getRequest(), getResponse(),fileProp);
		} catch (IOException e) {
			LOG.error("上传文件发生异常,错误原因 : " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 向客户端返回进度
	 * @return
	 */
	public String readProgress() {
		String key = getRequest().getParameter("key");

		Progress p=UploadFileUtil.keyMap.get(key);
		if(p!=null&&(p.isExceptionMaxSize()|| p.isComplete())){
			UploadFileUtil.keyMap.remove(key);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("key", key);
		map.put("len", p.getCurrentLength());
		map.put("total", p.getLength());
		map.put("fileName", p.getFileName());
		map.put("guid", p.getFileGuid());
		map.put("exceptionMaxSize", p.isExceptionMaxSize());
		map.put("errorMessage", p.getErrorMessage());
		map.put("isComplete", p.isComplete());
		this.getValueStack().set("data", map);
		
		return DATA;
	}
	
	/**
	 * 获取图片
	 * @return
	 * @throws IOException
	 */
	public String image() throws IOException {
		ImageDB imageDB;
		if("undefined".equals(fileGuid)||StringUtil.isEmpty(fileGuid)){
			imageDB=ImageDBUtil.getImageDBByGuid("default_"+Type.IMAGE.toLowerCase());
			showPic(imageDB,Type.IMAGE);
			return null;
		}else{
			imageDB=ImageDBUtil.getImageDBByGuid(fileGuid);
			showPic(imageDB,Type.IMAGE);
		}
		return null;
	}
	/**
	 * 获取照片
	 * @return
	 * @throws IOException
	 */
	public String photo() throws IOException {
		
		ImageDB imageDB;
		if("undefined".equals(fileGuid)||StringUtil.isEmpty(fileGuid)){
			imageDB=ImageDBUtil.getImageDBByGuid("default_"+Type.PHOTO.toLowerCase());
			showPic(imageDB,Type.PHOTO);
			return null;
		}else{
			imageDB=ImageDBUtil.getImageDBByGuid(fileGuid);
			showPic(imageDB,Type.PHOTO);
		}
		
		return null;
	}
	
	public void showPic(ImageDB imageDB,String type) throws IOException{
		String path = getRequest().getSession().getServletContext().getRealPath("/");
		if(imageDB==null){
			path+="upload"+File.separator+"default_"+type.toLowerCase()+".jpg";
			getResponse().getOutputStream().write(Byte_File_Object.getBytesFromFile(new File(path)));
		}else{
			if(StringUtil.isEmpty(imageDB.getPath())){
				getResponse().getOutputStream().write(imageDB.getFileContent());
			}else{
				if(imageDB.getFileContent()==null&&!StringUtil.isEmpty(imageDB.getPath())){
					path+="upload"+File.separator+imageDB.getGuid()+"."+imageDB.getSuffixs();
					getResponse().getOutputStream().write(Byte_File_Object.getBytesFromFile(new File(path)));
				}else{
					getResponse().getOutputStream().write(imageDB.getFileContent());
				}
			}
		}
	}
	
}
