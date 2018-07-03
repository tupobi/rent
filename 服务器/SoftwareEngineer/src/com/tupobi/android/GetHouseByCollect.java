package com.tupobi.android;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tupobi.bean.House;
import com.tupobi.biz.history_biz.HistoryBizImpl;
import com.tupobi.biz.history_biz.IHistoryBiz;
import com.tupobi.utils.JsonUtil;

public class GetHouseByCollect extends HttpServlet {

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
		
		String userName = request.getParameter("userName");
		IHistoryBiz iHistoryBiz = new HistoryBizImpl();
		List<House> houses = new  ArrayList<House>();
		houses = iHistoryBiz.getHousesByCollect(userName);
		out.write(JsonUtil.beanList2JsonList(houses));
		out.flush();
		out.close();
	}

}
