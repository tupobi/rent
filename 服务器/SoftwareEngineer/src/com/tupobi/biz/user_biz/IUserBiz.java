package com.tupobi.biz.user_biz;

import com.tupobi.bean.User;

public interface IUserBiz {
	int regist(String userName, String userPassword, int userType);
	
	boolean isUserNameExist(String userName);
	
	boolean login(String userName, String userPassword, int userType);
	
	boolean updateAvatar(String userName, String avatarUrl);
	
	User getUserInfo(String userName);
}
