package reservationapp;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class GUIMain extends JFrame{
	
	public static FirstScreen firstScreen;
	public static CustomerLoginScreen customerLoginScreen;
	public static OwnerLoginScreen ownerLoginScreen;
	public static CustomerSignIn customerSignIn;
	public static CustomerRegister customerRegister;
	public static OwnerSignIn ownerSignIn;
	public static OwnerRegister ownerRegister;
	public static CustomerMainMenu customerMainMenu;
	public static OwnerMainMenu ownerMainMenu;
	public static CustomerAllRestaurants customerAllRestaurants;
	
	public static JPanel mainPanel;
	
	public GUIMain()
	{
		super("RESERVATION APP");

		mainPanel = new JPanel();
		
		firstScreen = new FirstScreen();
		customerLoginScreen = new CustomerLoginScreen();
		ownerLoginScreen = new OwnerLoginScreen();
		customerSignIn = new CustomerSignIn();
		customerRegister = new CustomerRegister();
		ownerSignIn = new OwnerSignIn();
		ownerRegister = new OwnerRegister();
		customerMainMenu = new CustomerMainMenu();
		ownerMainMenu = new OwnerMainMenu();
		customerAllRestaurants = new CustomerAllRestaurants();

		
		firstScreen.setVisible(true);
		customerLoginScreen.setVisible(false);
		ownerLoginScreen.setVisible(false);
		customerSignIn.setVisible(false);
		customerRegister.setVisible(false);
		ownerSignIn.setVisible(false);
		ownerRegister.setVisible(false);
		customerMainMenu.setVisible(false);
		ownerMainMenu.setVisible(false);
		customerAllRestaurants.setVisible(false);

		
		mainPanel.add(firstScreen);
		mainPanel.add(customerLoginScreen);
		mainPanel.add(ownerLoginScreen);
		mainPanel.add(customerSignIn);
		mainPanel.add(customerRegister);
		mainPanel.add(ownerSignIn);
		mainPanel.add(ownerRegister);
		mainPanel.add(customerMainMenu);
		mainPanel.add(ownerMainMenu);
		mainPanel.add(customerAllRestaurants);		

		
		add(mainPanel);

		setSize(1600,900);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		
		new GUIMain();

	}

}
