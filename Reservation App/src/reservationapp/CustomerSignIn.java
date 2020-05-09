package reservationapp;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CustomerSignIn extends JPanel {
	
		// properties
		JLabel email;
		JLabel password;
		JTextField mailF;
		JTextField passwordF;
		JButton signIn;
		DBConnection con;
		Statement st;
		ResultSet rs;
		
		// constructors
		public CustomerSignIn()
		{
			con = new DBConnection();
			st = con.connect();
			
			setLayout(new GridLayout(3,2));
			
			email = new JLabel("Enter your email");
			password = new JLabel("Enter your password");
			mailF = new JTextField();
			passwordF = new JTextField();
			signIn = new JButton("Sign In");
			
			this.add(email);
			this.add(mailF);
			this.add(password);
			this.add(passwordF);
			this.add(signIn);

			signIn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					try {
						rs = st.executeQuery("SELECT * FROM Customers WHERE Email = '" + mailF.getText() + "' AND Password = '" + passwordF.getText() + "'" );
						if(rs.next())
						{
							GUIMain.customerSignIn.setVisible(false);
							GUIMain.customerMainMenu.setVisible(true);
						}
						else
						{
							JOptionPane.showOptionDialog(null, "Wrong email or password", "Error!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
						}
						
						con.getConnection().close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			});


		}
}
