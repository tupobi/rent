package com.tupobi.dao.history_dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.tupobi.bean.House;
import com.tupobi.utils.C3P0Util;

public class HistoriyDaoImpl implements IHistoryDao {

	@Override
	public int insertHistory(String userName, String houseName)
			throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr
				.update("insert into history (userName, houseName, history) values(?, ?, ?)",
						userName, houseName, 1);
	}

	@Override
	public int insertCollect(String userName, String houseName)
			throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr
				.update("update history set history=?, collect=? where userName=? and houseName=?",
						1, 1, userName, houseName);
	}

	@Override
	public List<House> selectHousesByHistoryAndUserName(String userName)
			throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query(
				"select * from history where userName = ? and history = ?",
				new BeanListHandler<House>(House.class), userName, 1);
	}

	@Override
	public List<House> selectHousesByCollectAndUserName(String userName)
			throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query(
				"select * from history where userName = ? and collect = ?",
				new BeanListHandler<House>(House.class), userName, 1);
	}

}
