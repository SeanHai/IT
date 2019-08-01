package datebase;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDatabase {
	private Connection conn;
	
	public Connection connect()
	{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/ITJob";
		String user = "root";
		String password = "54130907";
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		}catch(Exception e){e.printStackTrace();}
		return conn;
	}
}
