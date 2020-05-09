package reservationapp;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CustomerMainMenu extends JPanel{

	// properties
	JButton reservation;
	JButton showReservations;
	
	// constructors
	public CustomerMainMenu()
	{
		setLayout(new GridLayout(2,1));
		
		reservation = new JButton("Make a Reservation");
		showReservations = new JButton("Show my Reservations");
		
		add(reservation);
		add(showReservations);
		
		reservation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIMain.customerMainMenu.setVisible(false);
				GUIMain.customerAllRestaurants.setVisible(true);
				
			}
			
		});
		
		showReservations.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	// methods
}
