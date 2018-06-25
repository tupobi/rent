package com.tupobi.android;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tupobi.bean.StringResponse;
import com.tupobi.biz.IUserBiz;
import com.tupobi.biz.UserBizImpl;
import com.tupobi.utils.JsonUtil;

public class Regist extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		String userType = request.getParameter("userType");
		IUserBiz iUserBiz = new UserBizImpl();
		int res = iUserBiz.regist(userName, userPassword,
				Integer.valueOf(userType));
		StringResponse stringResponse = new StringResponse();
		
		if (res == 1) {
			stringResponse.setResponse("true");
			out.write(JsonUtil.bean2Json(stringResponse));
			System.out.println("注册成功！");
		} else {
			stringResponse.setResponse("false");
			out.write(JsonUtil.bean2Json(stringResponse));
			System.out.println("注册失败，用户名已存在！");
		}

		out.flush();
		out.close();
	}

}
