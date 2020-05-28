package android.example.fireapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * This class is a representation of each seat found in a restaurant.
 * It is a subclass of the hashmap class.
 * String key holds the date and the value holds the Seat Calendar of that date.
 * @date 10.05.2020
 * @author Group 3C
 */
public class Seat extends HashMap<String, Object>{

    // Properties
    public String seatName;
    private Restaurant r;

    // Constructors
    //Empty constructor required for the firebase
    public Seat(){

    }

    public Seat( String seatName, Restaurant r){
        this.seatName = seatName;
        this.r = r;
        createWeeklySeatPlan1( r.getWorkingHours() );
    }

    // Methods

    /**
     * It takes working hours as the parameter and splits them for further use. Then passes them to the second creation method.
     * @param workingHours Opening and closing hours of the restaurant
     */
    private void createWeeklySeatPlan1( String workingHours ) {
        String[] temp = workingHours.split("-");
        String[] temp2 = r.getOpeningTime().split(":");
        String[] temp3 = r.getClosingTime().split(":");
        LocalTime startingHour = LocalTime.of(Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]));
        LocalTime closingHour = LocalTime.of(Integer.parseInt(temp3[0]), Integer.parseInt(temp3[1]));

        // cloning the hashmap data into seat object
        this.putAll(createSeatWeeklyPlan2( startingHour, closingHour));
    }

    /**
     * This method creates seat calendars (a seat calendar represents only one day) of the seat by taking today’s date
     * as well as the opening and closing hours of the restaurant into consideration.
     * By this method, a weekly plan for the seat is formed.
     * @param openingHour opening hour of the restaurant in the form of LocalTime object
     * @param closingHour closing hour of the restaurant in the form of LocalTime object
     * @return It returns a hashmap which will be cloned by Seat object
     */
    private HashMap<String, SeatCalendar> createSeatWeeklyPlan2( LocalTime openingHour, LocalTime closingHour){

        HashMap<String, SeatCalendar> newSwp = new HashMap<String, SeatCalendar>();
        LocalDate date = LocalDate.now();
        for ( int i = 0; i < 7; i++ ){
            if( i > 0)
                date = date.plusDays(1);
            newSwp.put( date.toString(), new SeatCalendar( r, date, openingHour, closingHour));
        }
        return newSwp;
    }

    /**
     * It checks whether the restaurant should be updated in the onCreate method of CustomerPOVRestaurant activity,
     * which prevents the restaurant from being updated unnecessarily.
     * @return true if there is a need for update
     */
    public boolean needsToBeUpdated(){
        for ( String s : this.keySet()){
            if ( LocalDate.parse(s).isBefore(LocalDate.now())){
                return true;
            }
        }
        return false;
    }
}
