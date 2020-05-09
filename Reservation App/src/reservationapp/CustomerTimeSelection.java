package reservationapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class CustomerTimeSelection extends JPanel {

	DBConnection con;
	Statement st;
	ResultSet rs;
	JButton b;
	
	public CustomerTimeSelection(String tableNo, String buttonText, String restaurantName) // button text is date
	{
		setLayout(new GridLayout(7,7));
		this.setPreferredSize(new Dimension(1600,900));
		con = new DBConnection();
		st = con.connect();
		
		LocalDate selectedDate = LocalDate.parse(buttonText);

		try {
			
			rs = st.executeQuery("SELECT * FROM '@SA" + restaurantName + "' WHERE Dates = '" + buttonText + "'");
			while(rs.next())
			{
				String time = rs.getString("TimeSlots");
				String[] times = time.split("-");
				String[] times2 = times[0].split(":");
						
	
				LocalTime minutes = LocalTime.of(Integer.parseInt(times2[0]), Integer.parseInt(times2[1]));
				
				if( selectedDate.isEqual(LocalDate.now()))
				{
				if (minutes.isAfter(LocalTime.now()))
				{
				b = new JButton("" + rs.getString("TimeSlots"));
				if(rs.getInt(tableNo) != 1)
					b.setBackground(Color.GREEN);
				else
					b.setBackground(Color.RED);
				add(b);
				b.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						((JButton)e.getSource()).setBackground(Color.MAGENTA);
						String timeSlot = ((JButton)e.getSource()).getText();
						try {
							rs = st.executeQuery("SELECT * FROM 'All Restaurants' WHERE Name = '" + restaurantName + "'");
							int durationOfMeal = rs.getInt("DurationOfMeal");
							String m = rs.getString("OpeningHour");
							String[] d = m.split(":"); 
							LocalTime daysFirstSlot = LocalTime.of(Integer.parseInt(d[0]), Integer.parseInt(d[1]));
							LocalTime firstSlot = minutes.minusMinutes(durationOfMeal - 10);
							if(firstSlot.isBefore(daysFirstSlot))
							{
								firstSlot = daysFirstSlot;
							}
							LocalTime lastSlot = minutes.plusMinutes(durationOfMeal); 
							String a = rs.getString("ClosingHour");
							String[] b = a.split(":"); 
							LocalTime daysLastSlot = LocalTime.of(Integer.parseInt(b[0]), Integer.parseInt(b[1])).minusMinutes(durationOfMeal); // availableHourEnd - duration of meal
							// 10 is the interval between timeslots havent decided yet whether restaurant can change it
							for( LocalTime i = firstSlot; i.isBefore(daysLastSlot.plusMinutes(10)) && i.isBefore(lastSlot); i = i.plusMinutes(10) )
							{
								String s1 = i.getHour() + ":" + i.getMinute() + "-" + i.plusMinutes(durationOfMeal).getHour() + ":" + i.plusMinutes(durationOfMeal).getMinute(); // s1 is timeslot
								System.out.println(s1);
								st.execute("UPDATE '@SA" + restaurantName + "' SET '" + tableNo + "' = 1 WHERE TimeSlots = '" + s1 + "' AND Dates = '" + buttonText + "'");
								// painting all buttons to red
								Component[] components = CustomerTimeSelection.this.getComponents();
								for(Component c : components)
								{
									if(((JButton)c).getText().equals(s1) && !((JButton)e.getSource()).getText().equals(s1))
										((JButton)c).setBackground(Color.RED);
								}
							}
							
							JOptionPane.showOptionDialog(null, "RESERVED!", "Success!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
							con.getConnection().close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				});
				}
				else
				{
					
				}
			}
				else
				{
					b = new JButton("" + rs.getString("TimeSlots"));
					if(rs.getInt(tableNo) != 1)
						b.setBackground(Color.GREEN);
					else
						b.setBackground(Color.RED);
					add(b);
					b.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							((JButton)e.getSource()).setBackground(Color.MAGENTA);
							String timeSlot = ((JButton)e.getSource()).getText();
							try {
								rs = st.executeQuery("SELECT * FROM 'All Restaurants' WHERE Name = '" + restaurantName + "'");
								int durationOfMeal = rs.getInt("DurationOfMeal");
								String m = rs.getString("OpeningHour");
								String[] d = m.split(":"); 
								LocalTime daysFirstSlot = LocalTime.of(Integer.parseInt(d[0]), Integer.parseInt(d[1]));
								LocalTime firstSlot = minutes.minusMinutes(durationOfMeal - 10);
								if(firstSlot.isBefore(daysFirstSlot))
								{
									firstSlot = daysFirstSlot;
								}
								LocalTime lastSlot = minutes.plusMinutes(durationOfMeal); 
								String a = rs.getString("ClosingHour");
								String[] b = a.split(":"); 
								LocalTime daysLastSlot = LocalTime.of(Integer.parseInt(b[0]), Integer.parseInt(b[1])).minusMinutes(durationOfMeal); // availableHourEnd - duration of meal
								// 10 is the interval between timeslots havent decided yet whether restaurant can change it
								for( LocalTime i = firstSlot; i.isBefore(daysLastSlot.plusMinutes(10)) && i.isBefore(lastSlot); i = i.plusMinutes(10) )
								{
									String s1 = i.getHour() + ":" + i.getMinute() + "-" + i.plusMinutes(durationOfMeal).getHour() + ":" + i.plusMinutes(durationOfMeal).getMinute(); // s1 is timeslot
									System.out.println(s1);
									st.execute("UPDATE '@SA" + restaurantName + "' SET '" + tableNo + "' = 1 WHERE TimeSlots = '" + s1 + "' AND Dates = '" + buttonText + "'");
									// painting all buttons to red
									Component[] components = CustomerTimeSelection.this.getComponents();
									for(Component c : components)
									{
										if(((JButton)c).getText().equals(s1) && !((JButton)e.getSource()).getText().equals(s1))
											((JButton)c).setBackground(Color.RED);
									}
								}
								
								JOptionPane.showOptionDialog(null, "RESERVED!", "Success!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
								con.getConnection().close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
					});
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
