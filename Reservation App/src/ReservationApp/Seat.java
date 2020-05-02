package ReservationApp;

public class Seat implements Reservable {

	private boolean reservedStatus;
	@Override
	public void setReserved( boolean a) {
		reservedStatus = a;
	}

	@Override
	public boolean isReserved() {
		// TODO Auto-generated method stub
		return false;
	}

}
