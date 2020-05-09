package reservationapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CustomerDateSelection extends JPanel {

	// properties
	JButton b;
	DBConnection con;
	Statement st;
	ResultSet rs;
	private ArrayList<String> dates;
	
	// constructors
	public CustomerDateSelection(String buttonText, String restaurantName) // button text is table name
	{
		dates = new ArrayList<String>();
		setLayout(new GridLayout(0,1));
		this.setPreferredSize(new Dimension(1600,700));
		con = new DBConnection();
		st = con.connect();
		
		try {
			rs = st.executeQuery("SELECT * FROM 'All Restaurants' WHERE Name = '" + restaurantName + "'");
			int durationOfMeal = rs.getInt("DurationOfMeal");
			
			
			rs = st.executeQuery("SELECT * FROM '@SA" + restaurantName + "'");
			LocalDate dateControl = LocalDate.of(2000, 1, 1);
			int datesToBeAdded = 0;
			
			// controlling whether up to date
			while(rs.next())
			{
				String dateS = rs.getString("Dates");
				LocalDate dateLD = LocalDate.parse(dateS);
				
				if (dateLD.isBefore(LocalDate.now()))
				{
					st.execute("DELETE FROM '@SA" + restaurantName + "' WHERE Dates = '" + dateS + "';");
					rs = st.executeQuery("SELECT * FROM '@SA" + restaurantName + "'");
				}
				
				if( !dateLD.isEqual(dateControl)) 
				{
					dateControl = dateLD;
					
					if (dateLD.isBefore(LocalDate.now()))
					{
						datesToBeAdded++;
					}
					else
					{
						dates.add(dateS);// the dates which are shown on the screen -- in order to find last date shown
					}
				}
				
			}
			
			if ( datesToBeAdded > 0) 
			{ 	System.out.println("girdim");
				addDatesToDB( buttonText, restaurantName, datesToBeAdded);
				System.out.println("ciktim");
			}
			
			// adding buttons to the screen
			
			dateControl = LocalDate.of(2000, 1, 1);
			rs = st.executeQuery("SELECT * FROM '@SA" + restaurantName + "'");
			while(rs.next())
			{
				String dateS = rs.getString("Dates");
				LocalDate dateLD = LocalDate.parse(dateS);
				
				if( !dateLD.isEqual(dateControl)) 
				{
					dateControl = dateLD;
						System.out.println(dateS);
						b = new JButton(dateS);
						add(b);
						b.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								//                                       tableNo            source is date
								JPanel temp = new CustomerTimeSelection(buttonText, ((JButton)e.getSource()).getText(), restaurantName);
								GUIMain.mainPanel.add(temp);
								CustomerDateSelection.this.setVisible(false);
								temp.setVisible(true);
							}
							
						});
				}
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// methods
	
	// needed bcs after 8 day login should work
	private void addDatesToDB( String buttonText, String restaurantName, int datesToBeAdded)
	{
		try {
			rs = st.executeQuery("SELECT * FROM 'All Restaurants' WHERE Name = '" + restaurantName + "'");
			int durationOfMeal = rs.getInt("DurationOfMeal");
			String timeSlot = "";
			LocalDate startDate; // which is the start point of creation dates in the database
			LocalDate endDate;
			int startIndex = LocalTime.of(8, 0).getHour()*60 + LocalTime.of(15, 0).getMinute();
			int endIndex = LocalTime.of(23, 0).getHour()*60 + LocalTime.of(23, 0).getMinute() - durationOfMeal + 1;
			
			if( !dates.isEmpty())
			{
				System.out.println("Muhtemelen burdayim");
				String lastDate = dates.get(dates.size() - 1);
				System.out.println("last date 14 olmali : " + lastDate);
				startDate = LocalDate.parse(lastDate).plusDays(1);
				endDate = startDate.plusDays(datesToBeAdded);
			}
			else
			{
				startDate = LocalDate.now();
				endDate = startDate.plusDays(7);
			}
			
				for( LocalDate j = startDate; j.isBefore(endDate); j = j.plusDays(1)) 
				{
					for( int i = startIndex; i < endIndex; i = i + 10)
					{
						timeSlot = i/60 + ":" + i%60 + "-" + (i + durationOfMeal)/60 + ":" + (i + durationOfMeal)%60;
						st.execute("INSERT INTO '@SA" + restaurantName + "' ('Dates','TimeSlots') VALUES ('" + j + "', '" + timeSlot  + "');");
					}
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
