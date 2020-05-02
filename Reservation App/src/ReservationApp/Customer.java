package ReservationApp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Customer extends User {

	// properties
	
	private double money;
	private int points;
	private ArrayList<Restaurant> favRestaurants;
	private ArrayList<Reservation> reservations;
	
	// constructors
	
	public Customer( String email, String userName, String password) {
		setEmail( email);
		setUserName( userName);
		setPassword( password);
		money = 0;
		points = 0;
		favRestaurants = new ArrayList<Restaurant>();
		reservations = new ArrayList<Reservation>();
	}

	// methods
	
	public int getPoints()
	{
		return points;
	}
	
	public void increasePoints(int amount)
	{
		points = points + amount;
		AllCustomers.rankCustomer(this);
	}
	
	// sipariþi verirken paranýn yeterli olup olmamasýna bakýlmalý
	public double getMoney()
	{
		return money;
	}
	
	public void addMoney( double amount)
	{
		money = money + amount;
	}
	
	// preorder yapýnca veya tip verirken paranýn azalmasý gerekiyor
	public void substractMoney( double amount)
	{
		money = money - amount;
	}

	public void addToFavRestaurants( Restaurant r)
	{
		favRestaurants.add(r);
	}
	
	public void removeFromFavRestaurants( Restaurant r)
	{
		favRestaurants.remove(r);
	}
	
	public void showPreviousReservations()
	{
		
	}
	
	public void showCurrentReservations()
	{
		
	}
	
	public void makeReservation( Restaurant r, Seat s, LocalDateTime dateAndTime)
	{
		if( dateAndTime.toLocalTime().isBefore(r.getCalendar().getLastReservationTime().plusMinutes(1)))
		{
			if(r.getCalendar().isTimeSlotAvailable(dateAndTime.toLocalTime()))
			{
				Reservation res = new Reservation(r, s, dateAndTime);
				r.addReservation(res);
				System.out.println("Reservation has been created");
			}
			else 
			{
				System.out.println("The table is already reserved");
			}
		}
		else
		{
			System.out.println("Restoran kapandi bu saatte calismiyor");
		}
		
	}
	
	public void deleteReservation()
	{
		
	}
	
}
