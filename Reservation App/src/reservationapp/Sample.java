package reservationapp;	
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;


public class Sample {
	public static void main(String[] args)
    {
     
//		try
//		{
//			Connection baglanti = DriverManager.getConnection("jdbc:sqlite:test.db");
//			Statement st = baglanti.createStatement();
//			st.execute("CREATE TABLE reservations(date TEXT)");
//			st.close();
//			baglanti.close();
//		}
//		catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
//		
		try(Connection baglanti = DriverManager.getConnection("jdbc:sqlite:test.db");
				Statement st = baglanti.createStatement();)
		{
			st.execute("CREATE TABLE IF NOT EXISTS reservations(date TEXT)");
			st.execute("INSERT INTO reservations (date) VALUES ('02.05.2020')");
			
			ResultSet results = st.executeQuery("SELECT * FROM reservations");
			
			while(results.next())
			{
				System.out.println("Rezervasyon tarihi: " + results.getString(1));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
    }
}
