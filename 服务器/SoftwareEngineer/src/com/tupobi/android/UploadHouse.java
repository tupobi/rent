package com.tupobi.android;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.tupobi.bean.House;
import com.tupobi.bean.StringResponse;
import com.tupobi.biz.house_biz.HouseBizImpl;
import com.tupobi.biz.house_biz.IHouseBiz;
import com.tupobi.utils.JsonUtil;

public class UploadHouse extends HttpServlet {

	private House house;
	private Map<String, String> fileMap;
	private Map<String ,String> fieldMap;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		house = new House();
		IHouseBiz iHouseBiz = new HouseBizImpl();
		fileMap = new HashMap<String, String>();
		fieldMap = new HashMap<String, String>();

		// 要执行文件上传的操作
		// 判断表单是否支持文件上传。即：enctype="multipart/form-data"
		boolean isMultipartContent = ServletFileUpload
				.isMultipartContent(request);
		if (!isMultipartContent) {
			System.out.println("your form is not multipart/form-data");
		}

		// 创建一个DiskFileItemfactory工厂类
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File("c:\\"));// 指定临时文件的存储目录
		// 创建一个ServletFileUpload核心对象
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// 解决上传表单项乱码问题
		sfu.setHeaderEncoding("UTF-8");

		// 解析request对象，并得到一个表单项的集合
		try {
			// 限制上传文件的大小
			sfu.setFileSizeMax(1024 * 1024 * 10);// 表示10M大小
			sfu.setSizeMax(1024 * 1024 * 10);
			List<FileItem> fileItems = sfu.parseRequest(request);

			// 遍历表单项数据
			for (FileItem fileitem : fileItems) {
				if (fileitem.isFormField()) {
					// 普通表单项
					processFormField(fileitem);
				} else {
					// 上传表单项
					processUploadField(fileitem);
				}
			}

		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			// throw new RuntimeException("文件过大，不能超过3M");
			System.out.println("文件过大，不能超过3M");
		} catch (FileUploadBase.SizeLimitExceededException e) {
			System.out.println("总文件大小不能超过6M");
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
//		boolean isOk = iUserBiz.updateAvatar(userName, avatarUrl);
//		StringResponse stringResponse = new StringResponse();
//		if (isOk) {
//			stringResponse.setResponse("true");
//		} else {
//			stringResponse.setResponse("false");
//		}
//		out.write(JsonUtil.bean2Json(stringResponse));
		house.setUserName(fieldMap.get("userName"));
		house.setHouseName(fieldMap.get("houseName"));
		house.setHouseCity(fieldMap.get("houseCity"));
		house.setHouseArea(fieldMap.get("houseArea"));
		house.setHouseTyle(fieldMap.get("houseTyle"));
		house.setHouseTitle(fieldMap.get("houseTitle"));
		house.setMonthPrice(Integer.valueOf(fieldMap.get("monthPrice")));
		house.setHouseDescription(fieldMap.get("houseDescription"));
		house.setHouseLocation(fieldMap.get("houseLocation"));
		house.setOwnerPhone(fieldMap.get("ownerPhone"));
		
		house.setPic1Url(fileMap.get("pic1Url"));
		house.setPic2Url(fileMap.get("pic2Url"));
		house.setPic3Url(fileMap.get("pic3Url"));
		house.setPic4Url(fileMap.get("pic4Url"));
		house.setPic5Url(fileMap.get("pic5Url"));
		house.setPic6Url(fileMap.get("pic6Url"));
		
		boolean result = iHouseBiz.addHouse(house);
		StringResponse stringResponse = new StringResponse();
		if(result){
			stringResponse.setResponse("true");
			out.write(JsonUtil.bean2Json(stringResponse));
		}else{
			stringResponse.setResponse("false");
			out.write(JsonUtil.bean2Json(stringResponse));
		}
		out.flush();
		out.close();
	}
	
	private void processUploadField(FileItem fileitem) {
		try {
			// 得到文件输入流
			InputStream is = fileitem.getInputStream();
			System.out.println("文件字段内容:" + fileitem.getFieldName());
			// 创建一个文件存盘的目录
			String directoryRealPath = this.getServletContext().getRealPath(
					"/upload");
			File storeDirectory = new File(directoryRealPath);// 既代表文件又代表目录
			if (!storeDirectory.exists()) {
				storeDirectory.mkdirs();// 创建一个指定的目录
			}
			// 得到上传的名子
			String filename = fileitem.getName();// 文件项中的值 F:\图片素材\小清新\43.jpg 或者
													// 43.jpg
			if (filename != null) {
				// filename =
				// filename.substring(filename.lastIndexOf(File.separator)+1);
				filename = FilenameUtils.getName(filename);// 效果同上
			}

			// 解决文件同名的问题
			filename = UUID.randomUUID() + "_" + filename;
			// 按日期打散目录
			// String childDirectory = makeChildDirectory(storeDirectory); //
			// 2015-10-19
			String childDirectory = makeChildDirectory(storeDirectory, filename); // a/b
			System.out.println("childDirectory == " + childDirectory);
			// 上传文件
			File uploadFile = new File(storeDirectory, childDirectory + "/"
					+ filename);
			fileitem.write(uploadFile);
			System.out.println("文件位置：" + uploadFile.getAbsolutePath());
			String picUrl = "SoftwareEngineer/upload/" + childDirectory
					+ "/" + filename;
			System.out.println("picUrl == " + picUrl);
			fileMap.put(fileitem.getFieldName(), picUrl);
			fileitem.delete();
			// 删除临时文件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 处理普通表单项
	private void processFormField(FileItem fileitem) {
		try {
			String fieldname = fileitem.getFieldName();// 字段名
			String fieldvalue = fileitem.getString("UTF-8");// 字段值
			fieldMap.put(fieldname, fieldvalue);
			// fieldvalue = new
			// String(fieldvalue.getBytes("iso-8859-1"),"utf-8");
			System.out.println(fieldname + "=" + fieldvalue);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	// 按目录打散
	private String makeChildDirectory(File storeDirectory, String filename) {
		int hashcode = filename.hashCode();// 返回字符转换的32位hashcode码
		// System.out.println(hashcode);
		String code = Integer.toHexString(hashcode); // 把hashcode转换为16进制的字符
														// abdsaf2131safsd
														// System.out.println(code);
		String childDirectory = code.charAt(0) + "/" + code.charAt(1); // a/b
		// 创建指定目录
		File file = new File(storeDirectory, childDirectory);
		if (!file.exists()) {
			file.mkdirs();
		}
		return childDirectory;
	}
}
