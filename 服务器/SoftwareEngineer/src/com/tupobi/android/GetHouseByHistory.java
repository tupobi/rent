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

public class GetHouseByHistory extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");//ÉèÖÃ×Ö·û±àÂë¸ñÊ½
		PrintWriter out = response.getWriter();
		
		String userName = request.getParameter("userName");
		IHistoryBiz iHistoryBiz = new HistoryBizImpl();
		List<House> houses = new  ArrayList<House>();
		houses = iHistoryBiz.getHousesByHistory(userName);
		out.write(JsonUtil.beanList2JsonList(houses));
		out.flush();
		out.close();
	}

}
