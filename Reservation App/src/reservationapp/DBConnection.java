package reservationapp;

import java.sql.*;

public class DBConnection {

	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;

	public DBConnection() {}

	public Statement connect()
	{
		try
		{
			con = DriverManager.getConnection("jdbc:sqlite:resapp.db");
			stmt = con.createStatement();
			return stmt;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Connection getConnection()
	{
		return con;
	}
	
}
