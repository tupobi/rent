package com.tupobi.dao.history_dao;

import java.sql.SQLException;
import java.util.List;

import com.tupobi.bean.House;

public interface IHistoryDao {
	int insertHistory(String userName, String houseName) throws SQLException;
	
	int insertCollect(String userName, String houseName) throws SQLException;
	
	List<House> selectHousesByHistoryAndUserName(String userName) throws SQLException;
	
	List<House> selectHousesByCollectAndUserName(String userName) throws SQLException;
}
