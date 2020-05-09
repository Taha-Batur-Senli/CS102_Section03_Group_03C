package reservationapp;
import java.util.ArrayList;

public class Promotions {

	// properties
	private ArrayList<Promotion> promotions;
	// constructors
	public Promotions() {
		promotions = new ArrayList<Promotion>();
	}

	// methods
	public void add(Promotion p) {
		promotions.add(p);
	}

}