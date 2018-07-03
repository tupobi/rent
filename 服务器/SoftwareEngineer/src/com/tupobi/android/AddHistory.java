package com.tupobi.android;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tupobi.bean.StringResponse;
import com.tupobi.biz.history_biz.HistoryBizImpl;
import com.tupobi.biz.history_biz.IHistoryBiz;
import com.tupobi.dao.history_dao.HistoriyDaoImpl;
import com.tupobi.utils.JsonUtil;

public class AddHistory extends HttpServlet {

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
        response.setCharacterEncoding("utf-8");//ÉèÖÃ×Ö·û±àÂë¸ñÊ½
		PrintWriter out = response.getWriter();
		
		IHistoryBiz iHistoryBiz = new HistoryBizImpl();
		String userName = request.getParameter("userName");
		String houseName = request.getParameter("houseName");
		System.out.println("userName == " + userName);
		System.out.println("houseName == " + houseName);
		boolean res = iHistoryBiz.addHistory(userName, houseName);
		StringResponse stringResponse = new StringResponse();
		if(res == true){
			stringResponse.setResponse("true");
		}else{
			stringResponse.setResponse("false");
		}
		out.write(JsonUtil.bean2Json(stringResponse));
		out.flush();
		out.close();
	}

}
