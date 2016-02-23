package com.ssm.model;

/**
 * 用户 pojo
 * @author Administrator
 *
 */
public class User {
	
	private int id;				//id
	
	private String userName;	//用户名
	
	private String password;	//用户密码
	
	private int age;	//用户年龄

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}