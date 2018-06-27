package com.tupobi.biz.house_biz;

import java.sql.SQLException;

import com.tupobi.bean.House;
import com.tupobi.dao.house_dao.HouseDaoImpl;
import com.tupobi.dao.house_dao.IHouseDao;

public class HouseBizImpl implements IHouseBiz {
	private IHouseDao iHouseDao;

	public HouseBizImpl() {
		iHouseDao = new HouseDaoImpl();
	}

	@Override
	public boolean addHouse(House house) {
		int res = 0;
		try {
			res = iHouseDao.insertHouseInfo(house);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res == 1;
	}

}
