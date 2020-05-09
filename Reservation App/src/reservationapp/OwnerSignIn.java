package reservationapp;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class OwnerSignIn extends Panel {
	
			// properties
			JLabel email;
			JLabel password;
			JTextField mailF;
			JTextField passwordF;
			JButton signIn;
			
			// constructors
			public OwnerSignIn()
			{
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
						// TODO Auto-generated method stub
						GUIMain.ownerSignIn.setVisible(false);
						GUIMain.ownerMainMenu.setVisible(true);
					}
					
				});
			}
}
