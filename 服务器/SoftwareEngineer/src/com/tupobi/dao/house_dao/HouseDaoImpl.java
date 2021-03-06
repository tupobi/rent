package com.tupobi.dao.house_dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.tupobi.bean.House;
import com.tupobi.utils.C3P0Util;

public class HouseDaoImpl implements IHouseDao {

	@Override
	public int insertHouseInfo(House house) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr
				.update("insert into house (userName, houseName, houseCity, houseArea, houseTyle, "
						+ "houseTitle, monthPrice, houseDescription, houseLocation, ownerPhone, "
						+ "pic1Url, pic2Url, pic3Url, pic4Url, pic5Url, pic6Url) values (?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)",
						house.getUserName(), house.getHouseName(),
						house.getHouseCity(), house.getHouseArea(),
						house.getHouseTyle(), house.getHouseTitle(),
						house.getMonthPrice(), house.getHouseDescription(),
						house.getHouseLocation(), house.getOwnerPhone(),
						house.getPic1Url(), house.getPic2Url(),
						house.getPic3Url(), house.getPic4Url(),
						house.getPic5Url(), house.getPic6Url());
	}

	@Override
	public List<House> selectAllHouseInfo() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from house order by monthPrice ASC", new BeanListHandler<House>(House.class));
	}

	@Override
	public List<House> selectHouseByUserName(String userName)
			throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from house where userName = ?", new BeanListHandler<House>(House.class), userName);
	}
}
