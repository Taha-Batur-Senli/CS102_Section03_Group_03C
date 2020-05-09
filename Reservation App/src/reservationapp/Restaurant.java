package reservationapp;
import java.time.LocalTime;

public class Restaurant{
	
		// properties
		String name;
		String phoneNum;
		String description;
		Type type;
		Menu menu;
		RestaurantPlan restaurantPlan; // (pictures, seating plan, floor plan hepsi burda)
		Promotions promotions;
		SeatCalendar calendar;
		// constructors
		public Restaurant( RestaurantPlan plan, Menu menu, String restaurantName, Type type, LocalTime availableHoursStart, LocalTime availableHoursEnd, String phoneNum, String adress, String description)
		{
			calendar = new SeatCalendar(availableHoursStart, availableHoursEnd);
			name = restaurantName;
		}
		//methods
		public void setPromotions( Promotion promotion)
		{
			promotions.add( promotion);
		}
		
		public void getReservations() {
			// TODO Auto-generated method stub
			
		}
		public void addReservation(Reservation r) {
			// TODO Auto-generated method stub
			
		}
		public void setMenu(Menu m) {
			// TODO Auto-generated method stub
			
		}
		public void setSeatingPlan(SeatingPlan sp) {
			// TODO Auto-generated method stub
			
		}
		public void setName(String name2) {
			// TODO Auto-generated method stub
			
		}
		public void setDescription(String description2) {
			// TODO Auto-generated method stub
			
		}
		/*public void setAvailableHours( String start, String end)
		{
			availableHoursStart = start;
			availableHoursEnd = end;
		}*/
		public void setType(Type type2) {
			// TODO Auto-generated method stub
			
		}
		public SeatCalendar getCalendar()
		{
			return calendar;
		}
		public void setAvailableHours(LocalTime availableHoursStart, LocalTime availableHoursEnd) {
			// TODO Auto-generated method stub
			
		}
		
}

