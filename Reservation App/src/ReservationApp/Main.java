package ReservationApp;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		AllCustomers customers = new AllCustomers();
		
		Customer a = new Customer("mail","ahmet","123");
		customers.addCustomer(a);
		Customer b = new Customer("mail","berkay","123");
		customers.addCustomer(b);
		Customer c = new Customer("mail","cem","123");
		customers.addCustomer(c);
		Customer d = new Customer("mail","deniz","123");
		customers.addCustomer(d);
		Customer e = new Customer("mail","ezel","123");
		customers.addCustomer(e);
		Customer f = new Customer("mail","feyza","123");
		customers.addCustomer(f);


		Restaurant kebapci = new Restaurant(null, null, "kebapci", null, LocalTime.of(10, 0), LocalTime.of(21, 0), "phonenum", "adress", "description");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("İlk kullanıcı saat 19.00 - 20.30 arasına rezerve yapmaya çalışıyor");
		f.makeReservation(kebapci, null, LocalDateTime.of(2020, 5, 2, 19, 0));

		System.out.println("");
		System.out.println("");
		System.out.println("Baska kullanıcı saat 19.30 - 21.00 arasına rezerve yapmaya çalışıyor (Zaten rezerveli)");
		e.makeReservation(kebapci, null, LocalDateTime.of(2020, 5, 2, 19, 30));
		
		System.out.println("");
		System.out.println("");
		System.out.println("Baska kullanıcı saat 20.30 - 22.00 arasına rezerve yapmaya çalışıyor (Restoran 21.00 da kapanıyor)");
		d.makeReservation(kebapci, null, LocalDateTime.of(2020, 5, 2, 20, 30));
		System.out.println("");
		System.out.println("");
		
		System.out.println("Baska kullanıcı saat 15.30 - 17.00 arasına rezerve yapmaya çalışıyor (Boş, bir sıkıntı yok)");
		d.makeReservation(kebapci, null, LocalDateTime.of(2020, 5, 2, 15, 30));
		System.out.println("");
		System.out.println("");
		System.out.print("Reserved Time Slots  ");
		System.out.print("Reserved Time Slots  ");
		System.out.print("Reserved Time Slots  ");
		System.out.println("Reserved Time Slots");
		System.out.println(kebapci.getCalendar().printReservedTimeSlots());
		
		
		
		
		/* JFrame frame = new JFrame();
		 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);
		JButton button = new JButton("BU MASA SU AN REZERVE");
		button.setBackground(Color.RED);
		frame.getContentPane().add(button);
		frame.setVisible(true);
		
		
		while(true)

		{
			try
			{
				Thread.sleep(1000);
				if( LocalTime.now().isAfter(LocalTime.of(22,15)))
				{
					button.setBackground(Color.GREEN);
					button.setText("MASA BOŞALDI");
				}
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		}
		*/
		
	}
}
