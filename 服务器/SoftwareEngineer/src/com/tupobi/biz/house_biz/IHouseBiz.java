package com.tupobi.biz.house_biz;

import java.util.List;

import com.tupobi.bean.House;

public interface IHouseBiz {
	boolean addHouse(House house);
	
	List<House> getAllHouseInfo();
	
	List<House> getHouseInfoByUserName(String userName);
}
