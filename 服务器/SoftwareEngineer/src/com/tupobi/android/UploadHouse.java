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

		// Ҫִ���ļ��ϴ��Ĳ���
		// �жϱ��Ƿ�֧���ļ��ϴ�������enctype="multipart/form-data"
		boolean isMultipartContent = ServletFileUpload
				.isMultipartContent(request);
		if (!isMultipartContent) {
			System.out.println("your form is not multipart/form-data");
		}

		// ����һ��DiskFileItemfactory������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File("c:\\"));// ָ����ʱ�ļ��Ĵ洢Ŀ¼
		// ����һ��ServletFileUpload���Ķ���
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// ����ϴ�������������
		sfu.setHeaderEncoding("UTF-8");

		// ����request���󣬲��õ�һ������ļ���
		try {
			// �����ϴ��ļ��Ĵ�С
			sfu.setFileSizeMax(1024 * 1024 * 10);// ��ʾ10M��С
			sfu.setSizeMax(1024 * 1024 * 10);
			List<FileItem> fileItems = sfu.parseRequest(request);

			// ������������
			for (FileItem fileitem : fileItems) {
				if (fileitem.isFormField()) {
					// ��ͨ����
					processFormField(fileitem);
				} else {
					// �ϴ�����
					processUploadField(fileitem);
				}
			}

		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			// throw new RuntimeException("�ļ����󣬲��ܳ���3M");
			System.out.println("�ļ����󣬲��ܳ���3M");
		} catch (FileUploadBase.SizeLimitExceededException e) {
			System.out.println("���ļ���С���ܳ���6M");
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
			// �õ��ļ�������
			InputStream is = fileitem.getInputStream();
			System.out.println("�ļ��ֶ�����:" + fileitem.getFieldName());
			// ����һ���ļ����̵�Ŀ¼
			String directoryRealPath = this.getServletContext().getRealPath(
					"/upload");
			File storeDirectory = new File(directoryRealPath);// �ȴ����ļ��ִ���Ŀ¼
			if (!storeDirectory.exists()) {
				storeDirectory.mkdirs();// ����һ��ָ����Ŀ¼
			}
			// �õ��ϴ�������
			String filename = fileitem.getName();// �ļ����е�ֵ F:\ͼƬ�ز�\С����\43.jpg ����
													// 43.jpg
			if (filename != null) {
				// filename =
				// filename.substring(filename.lastIndexOf(File.separator)+1);
				filename = FilenameUtils.getName(filename);// Ч��ͬ��
			}

			// ����ļ�ͬ��������
			filename = UUID.randomUUID() + "_" + filename;
			// �����ڴ�ɢĿ¼
			// String childDirectory = makeChildDirectory(storeDirectory); //
			// 2015-10-19
			String childDirectory = makeChildDirectory(storeDirectory, filename); // a/b
			System.out.println("childDirectory == " + childDirectory);
			// �ϴ��ļ�
			File uploadFile = new File(storeDirectory, childDirectory + "/"
					+ filename);
			fileitem.write(uploadFile);
			System.out.println("�ļ�λ�ã�" + uploadFile.getAbsolutePath());
			String picUrl = "SoftwareEngineer/upload/" + childDirectory
					+ "/" + filename;
			System.out.println("picUrl == " + picUrl);
			fileMap.put(fileitem.getFieldName(), picUrl);
			fileitem.delete();
			// ɾ����ʱ�ļ�
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ������ͨ����
	private void processFormField(FileItem fileitem) {
		try {
			String fieldname = fileitem.getFieldName();// �ֶ���
			String fieldvalue = fileitem.getString("UTF-8");// �ֶ�ֵ
			fieldMap.put(fieldname, fieldvalue);
			// fieldvalue = new
			// String(fieldvalue.getBytes("iso-8859-1"),"utf-8");
			System.out.println(fieldname + "=" + fieldvalue);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	// ��Ŀ¼��ɢ
	private String makeChildDirectory(File storeDirectory, String filename) {
		int hashcode = filename.hashCode();// �����ַ�ת����32λhashcode��
		// System.out.println(hashcode);
		String code = Integer.toHexString(hashcode); // ��hashcodeת��Ϊ16���Ƶ��ַ�
														// abdsaf2131safsd
														// System.out.println(code);
		String childDirectory = code.charAt(0) + "/" + code.charAt(1); // a/b
		// ����ָ��Ŀ¼
		File file = new File(storeDirectory, childDirectory);
		if (!file.exists()) {
			file.mkdirs();
		}
		return childDirectory;
	}
}
