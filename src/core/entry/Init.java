package core.entry;


import java.io.IOException;

public class Init {
	public static void main(String[] args) throws IOException {
		Menu initialMenu = WelcomeMenu.getInstance();
		initialMenu.presentMenu();
	}
}
