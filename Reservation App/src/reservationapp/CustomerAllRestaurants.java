package reservationapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CustomerAllRestaurants extends JPanel {

	DBConnection con;
	Statement st;
	ResultSet rs;
	JButton b;
	
	public CustomerAllRestaurants()
	{
		con = new DBConnection();
		st = con.connect();
		
		try {
			rs = st.executeQuery("SELECT * FROM 'All Restaurants'");

			while(rs.next())
			{
				b = new JButton(rs.getString(1));
				add(b);
				b.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						JPanel restaurant = new CustomerSeatSelection(((JButton)e.getSource()).getText());
						GUIMain.mainPanel.add(restaurant);
						GUIMain.customerAllRestaurants.setVisible(false);
						restaurant.setVisible(true);
						
						/*
						if(((JButton)e.getSource()).getText().equals("kebapci"))
						{
							GUIMain.customerAllRestaurants.setVisible(false);
							GUIMain.kebapci.setVisible(true);
						}
						else if(((JButton)e.getSource()).getText().equals("tatlici"))
						{
							GUIMain.customerAllRestaurants.setVisible(false);
							GUIMain.tatlici.setVisible(true);
						}
						*/

					}
					
					
				});
			
			}
			con.getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
