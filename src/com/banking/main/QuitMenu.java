package com.banking.main;

import org.apache.log4j.Logger;

public class QuitMenu implements Menu {
	private static final Menu quitMenu = new QuitMenu();
	private static Logger log = Logger.getLogger(QuitMenu.class);
	private QuitMenu() {
	}
	
	public static Menu getInstance() {
		return quitMenu;
	}
	@Override
	public void presentMenu() {
		log.info("Program Shutting Down Goodbye :)");
		System.exit(0);
	}

}
