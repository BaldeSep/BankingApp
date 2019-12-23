package core.entry;

import org.apache.log4j.Logger;

public class RegisterMenu implements Menu {
	private static final Logger log = Logger.getLogger(RegisterMenu.class);
	private static final Menu registerMenu = new RegisterMenu();
	public RegisterMenu() {
		
	}
	public static Menu getInstance() {
		return registerMenu;
	}
	@Override
	public void presentMenu() {
		// TODO Auto-generated method stub
		System.out.println("Register Here");
	}

	public void switchMenu(int option) {
		// TODO Auto-generated method stub
		System.out.println("Register Switch Menus Here");
	}

}
