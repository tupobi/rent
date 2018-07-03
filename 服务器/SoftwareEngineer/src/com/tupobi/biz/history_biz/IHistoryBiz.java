package com.tupobi.biz.history_biz;

import java.util.List;

import com.tupobi.bean.House;

public interface IHistoryBiz {
	boolean addHistory(String userName, String houseName);
	
	boolean addCollect(String userName, String houseName);
	
	List<House> getHousesByHistory(String userName);
	
	List<House> getHousesByCollect(String userName);
}
