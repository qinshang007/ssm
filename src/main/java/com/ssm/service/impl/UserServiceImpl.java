package com.ssm.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ssm.dao.UserDao;
import com.ssm.model.User;
import com.ssm.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Resource
	private UserDao userDao;
	
	/**
	 * 添加用户
	 */
	public boolean createUser(User user) {
		// TODO Auto-generated method stub
		try{
			userDao.createUser(user);
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 修改用户
	 */
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		try{
			userDao.updateUser(user);
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 根据id返回用户
	 */
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return userDao.getUserById(id);
	}

	/**
	 * 删除用户
	 */
	public boolean deleteUser(int id) {
		// TODO Auto-generated method stub
		try{
			userDao.deleteUser(id);
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

}
