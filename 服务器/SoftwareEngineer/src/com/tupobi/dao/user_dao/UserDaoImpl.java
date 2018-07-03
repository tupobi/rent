package com.tupobi.dao.user_dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.tupobi.bean.User;
import com.tupobi.utils.C3P0Util;

public class UserDaoImpl implements IUserDao {

	@Override
	public int regist(String userName, String userPassword, int userType)
			throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		// return 1表示插入成功，0表示失败。返回影响的行数
		return qr
				.update("insert into user (userName, userPassword, userType) values(?, ?, ?)",
						userName, userPassword, userType);
	}

	@Override
	public boolean selectUserByUserName(String userName) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		User user = qr.query("select * from user where userName = ?",
				new BeanHandler<User>(User.class), userName);
		if (user == null) {
			// 0表示不存在
			return false;
		} else {
			// 1表示存在
			return true;
		}
	}
	
	public int deleteUserByUserName(String userName) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.update("delete from user where userName = ?", userName);
	}

	@Override
	public boolean selectUserByUserNameAndPasswrodAndUserType(String userName,
			String userPassword, int userType) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		User user = qr
				.query("select * from user where userName = ? and userPassword = ? and userType = ?",
						new BeanHandler<User>(User.class), userName,
						userPassword, userType);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int updateAvatarUrlByUserName(String userName, String avatarUrl) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		int res = qr.update("update user set avatarUrl = ? where userName = ?", avatarUrl, userName);
		return res;
	}

	@Override
	public User getUserByUserName(String userName) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from user where userName = ?", new BeanHandler<User>(User.class), userName);
	}
}
