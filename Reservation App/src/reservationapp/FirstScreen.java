package reservationapp;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FirstScreen extends JPanel{

	// properties
	JButton imCustomer;
	JButton imOwner;
	JLabel hello;
	
	// constructors
	public FirstScreen()
	{
		setLayout(new GridLayout(3,1));
		imCustomer = new JButton("I am Customer");
		imOwner = new JButton("I am Restaurant Owner");
		hello = new JLabel("MARABA");
		hello.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(hello);
		this.add(imCustomer);
		this.add(imOwner);

		imCustomer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GUIMain.firstScreen.setVisible(false);
				GUIMain.customerLoginScreen.setVisible(true);
			}
			
		});
		
		imOwner.addActionListener(new ActionListener( ) {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GUIMain.firstScreen.setVisible(false);
				GUIMain.ownerLoginScreen.setVisible(true);
			}
			
		});
		
	}
	
	// methods
	
}
