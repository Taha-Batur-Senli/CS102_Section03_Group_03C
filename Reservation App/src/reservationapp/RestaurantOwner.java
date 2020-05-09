package reservationapp;
import java.time.LocalDateTime;
import java.time.LocalTime;
public class RestaurantOwner extends User {
Restaurant restaurant; // we assume that 1 owner have 1 restaurant
	
	public RestaurantOwner(String email, String userName, String password) {
		setEmail( email);
		setUserName( userName);
		setPassword( password);
	}

	public void createRestaurant(RestaurantPlan plan, Menu menu, String restaurantName, Type type, LocalTime availableHoursStart, LocalTime availableHoursEnd, String phoneNum, String adress, String description)
	{
		restaurant = new Restaurant(plan, menu, restaurantName, type, availableHoursStart, availableHoursEnd, phoneNum, adress, description);
	}
	
	public void seeReservations()
	{
		restaurant.getReservations();	
	}
	
	public void addOtherReservations( Seat s, LocalDateTime ldt)
	{
		Reservation r = new Reservation(this.restaurant, s, ldt);
		restaurant.addReservation(r);
		
	}
	public void changeMenu( Menu m)
	{
		restaurant.setMenu( m);
	}
	
	public void changeSeatingPlan( SeatingPlan sp)
	{
		restaurant.setSeatingPlan( sp);
	}

	public void changeRestaurantName( String name)
	{
		restaurant.setName( name);
	}
	
	public void changeRestaurantPhone( String phone)
	{
		restaurant.setName( phone);
	}
	
	public void changeRestaurantDescription( String description)
	{
		restaurant.setDescription( description);
	}
	
	public void changeRestaurantType( Type type)
	{
		restaurant.setType( type);
	}
	
	public void changeRestaurantAvailableHours( LocalTime availableHoursStart, LocalTime availableHoursEnd)
	{
		restaurant.setAvailableHours( availableHoursStart, availableHoursEnd);
	}
}
