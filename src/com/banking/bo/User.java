package com.banking.bo;

import com.banking.bo.types.UserType;

public class User {
	private String userName;
	private String password;
	private UserType type;
	
	public User() {}

	public User(String userName, String password, UserType type) {
		super();
		this.userName = userName;
		this.password = password;
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User Name: [" + userName + "] Type Of User: [" + type + "]";
	}
	
	
	
	
	
}
