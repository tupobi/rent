package com.tupobi.dao.house_dao;

import java.sql.SQLException;
import java.util.List;

import com.tupobi.bean.House;

public interface IHouseDao {
	int insertHouseInfo(House house) throws SQLException;

	List<House> selectAllHouseInfo() throws SQLException;

	List<House> selectHouseByUserName(String userName) throws SQLException;
}