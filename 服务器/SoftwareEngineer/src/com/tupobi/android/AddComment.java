package com.tupobi.android;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tupobi.bean.Comment;
import com.tupobi.bean.StringResponse;
import com.tupobi.biz.comment_biz.CommentBizImpl;
import com.tupobi.biz.comment_biz.ICommentBiz;
import com.tupobi.utils.JsonUtil;

public class AddComment extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		ICommentBiz iCommentBiz = new CommentBizImpl();
		Comment comment = new Comment();
		comment.setUserName(request.getParameter("userName"));
		comment.setHouseName(request.getParameter("houseName"));
		comment.setContent(request.getParameter("content"));
		comment.setAvatarUrl(request.getParameter("avatarUrl"));
		String strDate = request.getParameter("date");
		
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date = null;
//		try {
//			date = simpleDateFormat.parse(strDate);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("日期格式不正确！");
//		}
		comment.setDate(strDate);
		boolean res = iCommentBiz.addComment(comment);
		StringResponse stringResponse = new StringResponse();
		if(res){
			stringResponse.setResponse("true");
		}else{
			stringResponse.setResponse("false");
		}
		out.write(JsonUtil.bean2Json(stringResponse));
		out.flush();
		out.close();
	}

}
