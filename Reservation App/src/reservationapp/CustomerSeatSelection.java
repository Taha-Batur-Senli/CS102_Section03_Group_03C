package reservationapp;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CustomerSeatSelection extends JPanel {

	JButton b;
	DBConnection con;
	Statement st;
	ResultSet rs;
	
	public CustomerSeatSelection(String buttonText) // button text is restaurant name
	{
		this.setLayout(null);
		this.setPreferredSize(new Dimension(1600,900));
		con = new DBConnection();
		st = con.connect();
		try {
			rs = st.executeQuery("SELECT * FROM '@RP" + buttonText + "'");
			while(rs.next())
			{
				int tableNo = rs.getInt(1);
				double x = rs.getDouble(2);
				double y = rs.getDouble(3);
				b = new JButton("table" + tableNo);
				b.setBounds((int)x+ this.getInsets().left, (int)y + this.getInsets().top, 70, 70);
				this.add(b);
				
				b.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// button text is restaurant name
						JPanel temp = new CustomerDateSelection(((JButton)e.getSource()).getText(), buttonText);
						GUIMain.mainPanel.add(temp);
						CustomerSeatSelection.this.setVisible(false);
						temp.setVisible(true);
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
