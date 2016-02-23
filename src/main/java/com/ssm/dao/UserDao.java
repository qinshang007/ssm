package com.ssm.dao;

import com.ssm.model.User;

/**
 * 用户dao类
 * @author Administrator
 *
 */
public interface UserDao {
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public boolean createUser(User user);
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user);
	
	/**
	 * 根据id返回用户
	 * @param id
	 * @return
	 */
	public User getUserById(int id);
	
	/**
	 * 根据id删除用户
	 * @param id
	 * @return
	 */
	public boolean deleteUser(int id);
	
}
