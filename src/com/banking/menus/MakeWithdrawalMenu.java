package com.banking.menus;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.bo.BankAccount;
import com.banking.bo.BankingSystem;
import com.banking.bo.User;
import com.banking.exception.DatabaseException;
import com.banking.exception.LibraryException;

public class MakeWithdrawalMenu implements Menu {
	private static final MakeWithdrawalMenu makeWithdrawalMenu = new MakeWithdrawalMenu();
	private static final Logger log = Logger.getLogger(MakeWithdrawalMenu.class);	
	private Menu prevMenu;
	private MakeWithdrawalMenu() {
		prevMenu = null;
	}
	public static MakeWithdrawalMenu getMenu() {
		return makeWithdrawalMenu;
	}
	
	@Override
	public void presentMenu() {
		// TODO Auto-generated method stub
		
	}
	
	private List<BankAccount> getAllAccounts(User user){
		BankingSystem system = BankingSystem.getInstance();
		List<BankAccount> accounts = new ArrayList<>();
		try {
			accounts = system.getAccounts(user.getUserName());
		} catch (LibraryException | DatabaseException e) {
			log.error(e);
			log.info(e.getMessage());
		}
		return accounts;
	}
	
	public void presentMenu(Menu prevMenu) {
		setPreviousMenu(prevMenu);
		presentMenu();
	}
	
	public void setPreviousMenu(Menu prevMenu) {
		this.prevMenu = prevMenu;
	}

}
