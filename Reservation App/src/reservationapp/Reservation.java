package reservationapp;
import java.time.LocalDateTime;

public class Reservation {

	public Reservation(Restaurant restaurant, Seat seat, LocalDateTime dateAndTime) {
		restaurant.getCalendar().setRelatedSlotsReserved(dateAndTime, true);
	}
}
