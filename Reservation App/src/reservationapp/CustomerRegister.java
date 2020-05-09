package reservationapp;

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

public class CustomerRegister extends JPanel {
	
			// properties
			JLabel name;
			JLabel email;
			JLabel password;
			JTextField nameF;
			JTextField mailF;
			JTextField passwordF;
			JButton register;
			DBConnection con;
			Statement st;
			ResultSet rs;
			
			// constructors
			public CustomerRegister()
			{
				con = new DBConnection();
				st = con.connect();
				
				
				setLayout(new GridLayout(4,2));
				
				name = new JLabel("Enter your full name");
				email = new JLabel("Enter your email");
				password = new JLabel("Enter your password");
				nameF = new JTextField();
				mailF = new JTextField();
				passwordF = new JTextField();
				register = new JButton("Register");
				
				this.add(name);
				this.add(nameF);
				this.add(email);
				this.add(mailF);
				this.add(password);
				this.add(passwordF);
				this.add(register);

				register.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
				
						// DATABASE STUFF
						
						if( nameF.getText().isEmpty() || mailF.getText().isEmpty() || passwordF.getText().isEmpty())
						{
							
						}
						else
						{
						try {
							st.execute("INSERT INTO Customers (Name,Email,Password,Money,Points) "
									+ "VALUES ('" + nameF.getText() + "','" + mailF.getText() + "','" + passwordF.getText() + "',0,0)");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						int input = JOptionPane.showOptionDialog(null, "You are registered! Please sign in", "Success!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

						if(input == JOptionPane.OK_OPTION)
						{
							GUIMain.customerRegister.setVisible(false);
						    GUIMain.customerSignIn.setVisible(true);
						}
						}
						
						try {
							con.getConnection().close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				});
			}

}
