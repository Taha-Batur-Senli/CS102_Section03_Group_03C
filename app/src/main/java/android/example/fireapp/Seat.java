package android.example.fireapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
/*
 *
 *@date 27.05.2020
 *@author Group 3C
 */
public class Seat extends HashMap<String, Object>{

    public String seatName;
    private Restaurant r;

    public Seat(){

    }

    public Seat( String seatName, Restaurant r){
        this.seatName = seatName;
        this.r = r;
        createWeeklySeatPlan1( r.getWorkingHours() );
    }

    private void createWeeklySeatPlan1( String workingHours ) {
        String[] temp = workingHours.split("-");
        String[] temp2 = r.getOpeningTime().split(":");
        String[] temp3 = r.getClosingTime().split(":");
        LocalTime startingHour = LocalTime.of(Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]));
        LocalTime closingHour = LocalTime.of(Integer.parseInt(temp3[0]), Integer.parseInt(temp3[1]));

        this.putAll(createSeatWeeklyPlan2( startingHour, closingHour));
        System.out.println(this.toString());
    }

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

    public boolean needsToBeUpdated(){
        for ( String s : this.keySet()){
            System.out.println("NEEDS TO BE UPDATED ICINE GIRDIM");
            if ( LocalDate.parse(s).isBefore(LocalDate.now())){
                System.out.println("NEEDS TO BE UPDATEDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                return true;
            }
        }
        return false;
    }


}
