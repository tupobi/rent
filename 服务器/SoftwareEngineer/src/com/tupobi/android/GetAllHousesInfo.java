package com.tupobi.android;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tupobi.bean.House;
import com.tupobi.biz.house_biz.HouseBizImpl;
import com.tupobi.biz.house_biz.IHouseBiz;
import com.tupobi.utils.JsonUtil;

public class GetAllHousesInfo extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		IHouseBiz iHouseBiz = new HouseBizImpl();
		List<House> houses = iHouseBiz.getAllHouseInfo();
		if(houses == null){
			return;
		}
		out.print(JsonUtil.beanList2JsonList(houses));
		out.flush();
		out.close();
	}
}
