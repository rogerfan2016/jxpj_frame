package com.zfsoft.hrm.file.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.dto.FileProp;
import com.zfsoft.hrm.file.dto.Progress;
import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.util.base.StringUtil;

/**
 * upload file
 * 
 * @author scott.Cgi
 */
public class UploadFileUtil {
	
	private static final Logger LOG = Logger.getLogger(UploadFileUtil.class);
	
	public static Map<String,Progress> keyMap=new HashMap<String,Progress>();
	private static String[] excludeEndChars=new String[]{":","?"};
	
	public static String checkedFileName(String fileName,String... limitSuffixs){
		if(fileName.lastIndexOf(".")<0)
			throw new RuntimeException("文件没有后缀名");
		else{
			String endName=fileName.substring(fileName.lastIndexOf(".")+1);
			endName=endName.toLowerCase();
			for(String endChar:excludeEndChars){
				if(endName.toLowerCase().indexOf(endChar)>0){
					throw new RuntimeException("后缀名不能含有非法字符\""+endChar+"\"");
				}
			}
			if(limitSuffixs.length<=0){
				return endName;
			}
			for(String suffix:limitSuffixs){
				if(suffix.toLowerCase().equals(endName)){
					return suffix;
				}
			}
			throw new RuntimeException("非法的后缀");
		}
	}
	/**
	 * 上传文件
	 * 
	 * @param request
	 *            http request
	 * @param response
	 *            htp response
	 * @throws IOException
	 *             IOException
	 */
	@SuppressWarnings("unchecked")
	public static void upload(HttpServletRequest request,
			HttpServletResponse response,FileProp fileProp) throws IOException {
		LOG.info("客户端提交类型: " + request.getContentType());
		if (request.getContentType() == null) {
			throw new IOException(
					"the request doesn't contain a multipart/form-data stream");
		}

		Progress p = UploadFileUtil.keyMap.get(fileProp.getKey());
		
		if(request.getContentLength() > fileProp.getMaxSize()){
			p.setErrorMessage("文件大小过大，限制"+fileProp.getLimitDes());
			p.setComplete(true);
			p.setExceptionMaxSize(true);
			return;
		}
		// 设置上传文件总大小
		p.setLength(request.getContentLength());
		
		LOG.info("上传文件大小为 : " + p.getLength());
		// 上传临时路径
		String path = request.getSession().getServletContext().getRealPath("/");
		path=FilePathUtil.resetPath(path);
		path += "upload/";
		
		LOG.info("上传临时路径 : " + path);
		// 设置上传工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		factory.setRepository(new File(path));
		// 阀值,超过这个值才会写到临时目录
		factory.setSizeThreshold(1024 * 1024 * 10);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// 最大上传限制
		upload.setSizeMax(fileProp.getMaxSize());
		
		// 设置监听器监听上传进度
		upload.setProgressListener(p);

		try {
			LOG.info("解析上传文件....");
			List<FileItem> items = upload.parseRequest(request);
			// 20150707 add start
			boolean isCreateImage = false;
			String uploadPath = SubSystemHolder.getPropertiesValue("uploadPath");
			String uploadSwitch = SubSystemHolder.getPropertiesValue("uploadSwitch");
			File newFile;
			File outFile;
			String tempPath = path.replace("\\", "/");
			
			if (StringUtils.isEmpty(uploadSwitch) || StringUtils.isEmpty(uploadPath)) {
				isCreateImage = false;
			} else {
				if ("on".equals(uploadSwitch)) {
					isCreateImage = true;
					uploadPath = uploadPath.replace("\\", "/");
					if (!uploadPath.endsWith("/")) {
						uploadPath += "/";
					}
					uploadPath = uploadPath.replace("[projectName]", request.getContextPath());
					tempPath = tempPath.substring(0, tempPath.indexOf(request.getContextPath())) + uploadPath.substring(uploadPath.indexOf(request.getContextPath()));
				} else {
					isCreateImage = false;
				}
			}
			// 20150707 add end

			LOG.info("上传数据...");
			for (FileItem item : items) {
				String fileName = item.getName();
				if(StringUtil.isEmpty(fileName)||item.getInputStream()==null||StringUtil.isEmpty(item.getFieldName())){
					continue;
				}
				if(!item.getFieldName().equals(fileProp.getCurrentName())){
					continue;
				}
				fileName=fileName.replace("\\", "/");
				if (fileName.lastIndexOf("/") >= 0) {
					fileName = fileName.substring(fileName.lastIndexOf("/"));
				}
				if(request.getAttribute("fileGuid")!=null){
					fileProp.setFileId(request.getAttribute("fileGuid").toString());
				}
				String guid = saveToDB(fileProp, item, fileName);
				
				p.setFileName(fileName);
				
				p.setFileGuid(guid);
				
				// 20150707 add start
				if ("image".equalsIgnoreCase(fileProp.getFileType()) && isCreateImage) {
					newFile = new File(tempPath);
					if (!newFile.exists()) {
						newFile.mkdir();
					}
					
					outFile = new File(tempPath, fileName);
					if (!outFile.exists()) {
						outFile.createNewFile();
					} else {
						outFile.delete();
						outFile.createNewFile();
					}
					
					ImageIO.setUseCache(false);
					byte[] content = Byte_File_Object.getBytesFromFile(item.getInputStream());
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
					BufferedImage newImage = ImageIO.read(byteArrayInputStream);
					ImageIO.write(newImage, UploadFileUtil.checkedFileName(fileName), outFile);
				}
				// 20150707 add end
				
				LOG.info("完成上传文件!");

				item.delete();
				LOG.info("删除临时文件!");

				p.setComplete(true);
				LOG.info("更新progress对象状态为完成状态!");
				return;
			}
			p.setComplete(true);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("上传文件出现异常, 错误原因 : " + e.getMessage());
			// 发生错误,进度信息对象设置为完成状态
			p.setComplete(true);
			
			UploadFileUtil.keyMap.remove(fileProp.getKey());
		}
	}

	private static String saveToDB(FileProp fileProp,FileItem item,String fileName) throws IOException {
		if(fileProp.getFileType().equalsIgnoreCase("image")){
			byte[] content=Byte_File_Object.getBytesFromFile(item.getInputStream());
			ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(content);
			try {
				ImageIO.setUseCache(false);
				BufferedImage newImage = ImageIO.read(byteArrayInputStream);
				Image croppedImage = null;
				if(croppedImage==null){
					croppedImage = Toolkit.getDefaultToolkit().createImage(newImage.getSource());
				}
				if(newImage.getWidth()>fileProp.getWidth()*2){
					
					Double nHeight=new Double(fileProp.getWidth()*2)/new Double(newImage.getWidth())*newImage.getHeight();
					// 压缩
					croppedImage = croppedImage.getScaledInstance(fileProp.getWidth()*2, nHeight.intValue(),Image.SCALE_DEFAULT);
					// 绘制缩小后的图
					BufferedImage tag = new BufferedImage(fileProp.getWidth()*2, nHeight.intValue(),BufferedImage.TYPE_INT_RGB);
					Graphics g = tag.getGraphics();
					g.drawImage(croppedImage, 0, 0, null); 
					g.dispose();
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					// 输出到字节流
					ImageIO.write(tag, UploadFileUtil.checkedFileName(fileName), outputStream);
					content=outputStream.toByteArray();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			ImageDB imageDB;
			if(!StringUtil.isEmpty(fileProp.getFileId())){
				imageDB=ImageDBUtil.getImageDBByGuid(fileProp.getFileId());
				if(imageDB!=null){
					imageDB=ImageDBUtil.updateFileToDB(item.getName(),content, imageDB);
				}else{
					imageDB=ImageDBUtil.saveFileToDB(item.getName(),content);
				}
			}else{
				imageDB = ImageDBUtil.saveFileToDB(fileName,content);
			}
			return imageDB.getGuid();
		}else{
			Attachement attachement;
			if(!StringUtil.isEmpty(fileProp.getFileId())){
				attachement=AttachementUtil.getAttachementByGuid(fileProp.getFileId());
				if(attachement!=null){
					attachement=AttachementUtil.updateAttachement(item.getName(),item.getInputStream(), attachement);
				}else{
					attachement=AttachementUtil.saveAttachement(item.getName(),item.getInputStream());
				}
			}else{
				attachement = AttachementUtil.saveAttachement(fileName,item.getInputStream());
			}
			return attachement.getGuId();
		}
	}
}