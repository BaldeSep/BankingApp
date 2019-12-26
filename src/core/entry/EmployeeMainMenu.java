package core.entry;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class EmployeeMainMenu implements Menu {
	private static final Logger log = Logger.getLogger(EmployeeMainMenu.class);
	private static final Menu employeeMainMenu = new EmployeeMainMenu();
	private static EmployeeMainMenuOptions[] employeeMainMenuOptions;
	private static Map<Integer, Menu> nextMenus = null;
	private EmployeeMainMenu() {
		employeeMainMenuOptions = EmployeeMainMenuOptions.values();
	}
	
	public static Menu getInstance() {
		return employeeMainMenu;
	}
	
	@Override
	public void presentMenu() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public Map<Integer, Menu> getNextMenus(){
		if(EmployeeMainMenu.nextMenus != null) {
			return EmployeeMainMenu.nextMenus;
		}
		EmployeeMainMenu.nextMenus = new HashMap<>();
		for( EmployeeMainMenuOptions option : employeeMainMenuOptions ) {
			switch(option) {
			case LOGOUT:
				nextMenus.put(option.ordinal(), WelcomeMenu.getInstance());
				break;
			case QUIT:
				log.info("Program Shutting Down Goodbye");
				System.exit(0);
				break;
			case VIEW_PENDING_ACCOUNTS:
				nextMenus.put(option.ordinal(), null);
				break;
			case VIEW_CUSTOMER_ACCOUNT:
				nextMenus.put(option.ordinal(), null);
				break;
			case VIEW_ALL_TRANSACTIONS:
				nextMenus.put(option.ordinal(), null);
				break;
			default:
				break;			
			}
		}
		
		return nextMenus;
		
	}
	
	
	 

}
