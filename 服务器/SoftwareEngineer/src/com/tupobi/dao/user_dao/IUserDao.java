package com.tupobi.dao.user_dao;

import java.sql.SQLException;

import com.tupobi.bean.User;

public interface IUserDao {
	int regist(String userName, String userPassword, int userType)
			throws SQLException;

	boolean selectUserByUserName(String userName) throws SQLException;

	boolean selectUserByUserNameAndPasswrodAndUserType(String userName, String userPassword,
			int userType) throws SQLException;
	
	int updateAvatarUrlByUserName(String userName, String avatarUrl) throws SQLException;
	
	User getUserByUserName(String userName) throws SQLException;
}