package com.tupobi.biz.history_biz;

import java.sql.SQLException;
import java.util.List;

import com.tupobi.bean.House;
import com.tupobi.dao.history_dao.HistoriyDaoImpl;
import com.tupobi.dao.history_dao.IHistoryDao;

public class HistoryBizImpl implements IHistoryBiz{
	private IHistoryDao iHistoryDao;
	public HistoryBizImpl() {
		iHistoryDao = new HistoriyDaoImpl();
	}

	@Override
	public boolean addHistory(String userName, String houseName) {
		int res = 0;
		try {
			res = iHistoryDao.insertHistory(userName, houseName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(res == 1){
			return true;
		}
		return false;
	}

	@Override
	public boolean addCollect(String userName, String houseName) {
		int res = 0;
		try {
			res = iHistoryDao.insertCollect(userName, houseName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(res == 1){
			return true;
		}
		return false;
	}

	@Override
	public List<House> getHousesByHistory(String userName) {
		List<House> houses = null;
		try {
			houses = iHistoryDao.selectHousesByHistoryAndUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return houses;
	}

	@Override
	public List<House> getHousesByCollect(String userName) {
		List<House> houses = null;
		try {
			houses = iHistoryDao.selectHousesByCollectAndUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return houses;
	}

}
