package android.example.fireapp;

import java.util.HashMap;

public class Seats extends HashMap<String, Object>{

    private Restaurant restaurant;
    public Seats(){

    }

    public Seats( int numOfTables, Restaurant r) {
        restaurant = r;
        for ( int i = 0; i < numOfTables; i++){
            this.put( "seat" + (i+1), new Seat("seat" + (i+1), r));
        }
        System.out.println(this.toString());
    }
}
