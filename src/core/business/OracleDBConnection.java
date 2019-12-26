package core.business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDBConnection {
	private static Connection connection = null;
	
	
	
	public static Connection getInstance() throws ClassNotFoundException, SQLException {
		if(connection != null) {
			return connection;
		}
		Class.forName("oracle.jdbc.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userName = "java";
		String password = "sepu";
		connection = DriverManager.getConnection(url, userName, password);
		return connection;
	}
}
