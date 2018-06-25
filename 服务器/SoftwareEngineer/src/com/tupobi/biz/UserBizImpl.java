package com.tupobi.biz;

import java.sql.SQLException;

import com.tupobi.bean.User;
import com.tupobi.dao.IUserDao;
import com.tupobi.dao.UserDaoImpl;

public class UserBizImpl implements IUserBiz {

	IUserDao iUserDao;

	public UserBizImpl() {
		iUserDao = new UserDaoImpl();
	}

	@Override
	public int regist(String userName, String userPassword, int userType) {
		boolean isExist = false;
		isExist = isUserNameExist(userName);
		if (isExist) {
			return 0;// 0表注册失败
		} else {
			try {
				return iUserDao.regist(userName, userPassword, userType);// 1表成功
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public boolean isUserNameExist(String userName) {
		boolean res = false;
		try {
			res = iUserDao.selectUserByUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean login(String userName, String userPassword, int userType) {
		boolean loginSuccess = false;
		try {
			loginSuccess = iUserDao.selectUserByUserNameAndPasswrodAndUserType(
					userName, userPassword, userType);
			if (loginSuccess) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginSuccess;
	}

	@Override
	public boolean updateAvatar(String userName, String avatarUrl) {
		boolean isUpdateAvatarSuccess = false;
		try {
			int res = iUserDao.updateAvatarUrlByUserName(userName, avatarUrl);
			if(res == 1){
				isUpdateAvatarSuccess = true;
			}else{
				isUpdateAvatarSuccess = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isUpdateAvatarSuccess;
	}

	@Override
	public User getUserInfo(String userName) {
		User user = null;
		try {
			user = iUserDao.getUserByUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

}
