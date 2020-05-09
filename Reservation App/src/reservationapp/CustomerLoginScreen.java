package reservationapp;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomerLoginScreen extends JPanel {

	// properties
	JLabel lSignIn;
	JLabel lSignUp;
	JButton bSignIn;
	JButton bSignUp;

	// constructors
	public CustomerLoginScreen()
	{
		setLayout(new GridLayout(2,2));
		
		lSignUp = new JLabel("If you don't have an account: ");
		bSignUp = new JButton("REGISTER");
		lSignIn = new JLabel("If you already have an account: ");
		bSignIn = new JButton("SIGN IN");
		
		add(lSignUp);
		add(bSignUp);
		add(lSignIn);
		add(bSignIn);
		
		bSignUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CustomerLoginScreen.this.setVisible(false);
				GUIMain.customerRegister.setVisible(true);
			}
			
		});
		
		bSignIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CustomerLoginScreen.this.setVisible(false);
				GUIMain.customerSignIn.setVisible(true);
			}
			
		});
		
	}

	
	// methods
	
	
}
