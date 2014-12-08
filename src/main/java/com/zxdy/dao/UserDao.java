package com.zxdy.dao;

import org.springframework.stereotype.Repository;

import com.zxdy.domain.User;


@Repository
public class UserDao extends BaseDao{
	public int getMatchCount(String userName,String password){
		return 0;
	}
	public User findUserByUserNmae(final String userName){
		return null;
		
	}
	public void updateLoginInfo(User user){
		
	}
}
