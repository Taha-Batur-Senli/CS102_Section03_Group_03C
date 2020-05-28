package android.example.fireapp;

import java.util.HashMap;
/**
 * Seats class, multiple seat objects
 * @date 30.04.2020
 * @author Group 3C
 */
public class Seats extends HashMap<String, Object>{

    //Properties
    private Restaurant restaurant;

    //Constructors

    //Empty constructor required for the firebase
    public Seats(){}

    public Seats( int numOfTables, Restaurant r) {
        restaurant = r;
        for ( int i = 0; i < numOfTables; i++){
            this.put( "seat" + (i+1), new Seat("seat" + (i+1), r));
        }
        System.out.println(this.toString());
    }
}
