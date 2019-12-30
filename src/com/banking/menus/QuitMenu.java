package com.banking.menus;

import org.apache.log4j.Logger;

public class QuitMenu implements Menu {

	private static final Menu quitMenu = new QuitMenu();
	private static final Logger log = Logger.getLogger(QuitMenu.class);
	private QuitMenu() {
	}
	public static Menu getMenu() {
		return quitMenu;
	}
	@Override
	public void presentMenu() {
		log.info("Shutting System Down Have A Good Day!!! :) ");
		System.exit(0);
	}

}
