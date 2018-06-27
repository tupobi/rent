package com.tupobi.dao.house_dao;

import java.sql.SQLException;

import com.tupobi.bean.House;

public interface IHouseDao {
	int insertHouseInfo(House house) throws SQLException;
}
