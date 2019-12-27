package core.business;

import java.time.LocalDateTime;

public class RequestTicket {
	private String userName;
	private double initialBalance;
	
	public RequestTicket() {
		// TODO Auto-generated constructor stub
	}

	public RequestTicket(String userName, double initialBalance) {
		super();
		this.userName = userName;
		this.initialBalance = initialBalance;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getInitialBalance() {
		return initialBalance;
	}

	public void setInitialBalance(double initialBalance) {
		this.initialBalance = initialBalance;
	}
	
	
	
}
