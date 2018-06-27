package com.tupobi.android;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tupobi.bean.User;
import com.tupobi.biz.user_biz.IUserBiz;
import com.tupobi.biz.user_biz.UserBizImpl;
import com.tupobi.dao.user_dao.IUserDao;
import com.tupobi.dao.user_dao.UserDaoImpl;
import com.tupobi.utils.JsonUtil;

public class GetUserInfo extends HttpServlet {

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
		
		String userName = request.getParameter("userName");
		IUserBiz iUserBiz = new UserBizImpl();
		User user = iUserBiz.getUserInfo(userName);
		if(user != null){
			out.write(JsonUtil.bean2Json(user));
		}else{
			user = new User();
			out.write(JsonUtil.bean2Json(user));
		}
		
		out.flush();
		out.close();
	}

}
