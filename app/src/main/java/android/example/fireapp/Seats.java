package android.example.fireapp;

import java.util.HashMap;
/**
 * This class represents the collection of seats found in a restaurant.
 * String key holds the name of the seats (seat1, seat2) and the value holds the related seat object.
 * @date 10.05.2020
 * @author Group 3C
 */
public class Seats extends HashMap<String, Object>{

    // Properties
    private Restaurant restaurant;

    // Constructors
    // Empty constructor required for the firebase
    public Seats(){}

    public Seats( int numOfTables, Restaurant r) {
        restaurant = r;
        for ( int i = 0; i < numOfTables; i++){
            this.put( "seat" + (i+1), new Seat("seat" + (i+1), r));
        }
    }
}
