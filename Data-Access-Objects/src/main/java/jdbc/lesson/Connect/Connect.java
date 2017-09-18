package jdbc.lesson.Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	
	 static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
     static final String USER = "postgres";
     static final String PASS = "bondstone";
	
	
	public Connection getConnection(){
		
		try {
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Statement getStatment(){
		
		try {
			return getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
