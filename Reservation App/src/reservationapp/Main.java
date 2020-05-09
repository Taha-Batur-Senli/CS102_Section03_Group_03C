package reservationapp;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.sql.*;

public class Main {

	public static void main(String[] args) {
		
//		AllCustomers customers = new AllCustomers();
//		
//		Customer a = new Customer("mail","ahmet","123");
//		customers.addCustomer(a);
//		Customer b = new Customer("mail","berkay","123");
//		customers.addCustomer(b);
//		Customer c = new Customer("mail","cem","123");
//		customers.addCustomer(c);
//		Customer d = new Customer("mail","deniz","123");
//		customers.addCustomer(d);
//		Customer e = new Customer("mail","ezel","123");
//		customers.addCustomer(e);
//		Customer f = new Customer("mail","feyza","123");
//		customers.addCustomer(f);
//
//
//		Restaurant kebapci = new Restaurant(null, null, "kebapci", null, LocalTime.of(12, 0), LocalTime.of(18, 30), "phonenum", "adress", "description");
//		System.out.println("");
//		System.out.println("");
//		System.out.println("");
//		System.out.println("");
//		System.out.println("");
//		System.out.println("İlk kullanıcı saat 19.00 - 20.30 arasına rezerve yapmaya çalışıyor");
//		f.makeReservation(kebapci, null, LocalDateTime.of(2020, 5, 2, 19, 0));
//
//		System.out.println("");
//		System.out.println("");
//		System.out.println("Baska kullanıcı saat 19.30 - 21.00 arasına rezerve yapmaya çalışıyor (Zaten rezerveli)");
//		e.makeReservation(kebapci, null, LocalDateTime.of(2020, 5, 2, 19, 30));
//		
//		System.out.println("");
//		System.out.println("");
//		System.out.println("Baska kullanıcı saat 20.30 - 22.00 arasına rezerve yapmaya çalışıyor (Restoran 21.00 da kapanıyor)");
//		d.makeReservation(kebapci, null, LocalDateTime.of(2020, 5, 2, 20, 30));
//		System.out.println("");
//		System.out.println("");
//		
//		System.out.println("Baska kullanıcı saat 15.30 - 17.00 arasına rezerve yapmaya çalışıyor (Boş, bir sıkıntı yok)");
//		d.makeReservation(kebapci, null, LocalDateTime.of(2020, 5, 2, 15, 30));
//		System.out.println("");
//		System.out.println("");
//		System.out.print("Reserved Time Slots  ");
//		System.out.print("Reserved Time Slots  ");
//		System.out.print("Reserved Time Slots  ");
//		System.out.println("Reserved Time Slots");
//		System.out.println(kebapci.getCalendar().printReservedTimeSlots());
		
		
		DBConnection con = new DBConnection();
		Statement st = con.connect();
		ResultSet rs;

		// CREATING A RESTAURANT'S SEATING AVAILABILITY TABLE IN DATABASE
		
		try {
			
			// KEBAPCI
			
			st.execute("CREATE TABLE IF NOT EXISTS '@SAkebapci' ('Dates' text)");
			st.execute("ALTER TABLE '@SAkebapci' ADD 'TimeSlots' text;");
			
			int noOfSeats = 9;
			
			for( int i = 1; i < noOfSeats + 1; i++)
			{
				st.execute("ALTER TABLE '@SAkebapci' ADD 'table" + i + "' int;");
			}
			
			rs = st.executeQuery("SELECT * FROM 'All Restaurants' WHERE Name = 'kebapci'");
			int durationOfMeal = rs.getInt("DurationOfMeal");
			String timeSlot = "";
			int startIndex = LocalTime.of(8, 0).getHour()*60 + LocalTime.of(15, 0).getMinute();
			int endIndex = LocalTime.of(23, 0).getHour()*60 + LocalTime.of(23, 0).getMinute() - durationOfMeal + 1;
			
			LocalDate startDate = LocalDate.now();
			LocalDate endDate = LocalDate.now().plusDays(7);

			for( LocalDate j = startDate; j.isBefore(endDate); j = j.plusDays(1)) 
			{
				for( int i = startIndex; i < endIndex; i = i + 10)
				{
					timeSlot = i/60 + ":" + i%60 + "-" + (i + durationOfMeal)/60 + ":" + (i + durationOfMeal)%60;
					st.execute("INSERT INTO '@SAkebapci' ('Dates','TimeSlots') VALUES ('" + j + "', '" + timeSlot  + "');");
				}
			}
			
			// TATLICI
			
			st.execute("CREATE TABLE IF NOT EXISTS '@SAtatlici' ('Dates' text)");
			st.execute("ALTER TABLE '@SAtatlici' ADD 'TimeSlots' text;");
			
			noOfSeats = 5;
			
			for( int i = 1; i < noOfSeats + 1; i++)
			{
				st.execute("ALTER TABLE '@SAtatlici' ADD 'table" + i + "' int;");
			}
			

			rs = st.executeQuery("SELECT * FROM 'All Restaurants' WHERE Name = 'tatlici'");
			durationOfMeal = rs.getInt("DurationOfMeal");
			timeSlot = "";
			startIndex = LocalTime.of(9, 0).getHour()*60 + LocalTime.of(9, 0).getMinute();
			endIndex = LocalTime.of(18, 0).getHour()*60 + LocalTime.of(18, 0).getMinute() - durationOfMeal + 1;
			
			startDate = LocalDate.now();
			endDate = LocalDate.now().plusDays(7);
			
			for( LocalDate j = startDate; j.isBefore(endDate); j = j.plusDays(1) )
			{
			for( int i = startIndex; i < endIndex; i = i + 10)
				{
					timeSlot = i/60 + ":" + i%60 + "-" + (i + durationOfMeal)/60 + ":" + (i + durationOfMeal)%60;
					st.execute("INSERT INTO '@SAtatlici' ('Dates', 'TimeSlots') VALUES ('" + j + "', '" + timeSlot  + "');");
				}
			}
			
			
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		/*JFrame frame = new JFrame();
		 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);
		JButton button = new JButton("BU MASA SU AN REZERVE");
		button.setBackground(Color.RED);
		frame.getContentPane().add(button);
		frame.setVisible(true);
		
		
		while(true)
		{
			try
			{
				Thread.sleep(1000);
				if( LocalTime.now().isAfter(LocalTime.of(23,15)))
				{
					button.setBackground(Color.GREEN);
					button.setText("MASA BOŞALDI");
				}
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		}*/
		
		
	}
}
