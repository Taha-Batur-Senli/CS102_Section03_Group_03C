package android.example.fireapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * This class is the outline of the seatcalendar object which belongs to a seat and contains all possible timeslots
 * from the opening hour to the last reservation time of the restaurant.
 * String key is used to reach a particular timeslot.
 *@date 10.05.2020
 *@author Group 3C
 */
public class SeatCalendar extends HashMap<String, Object>{

    // Properties

    private LocalTime availableHoursStart;
    private LocalTime availableHoursEnd;
    private LocalDate currentDate;
    private Restaurant r;
    private int maxSeatingDuration;

    // Constructors

    //Empty constructor required for the firebase
    public SeatCalendar(){}

    // This constructor is used when a new restaurant is created
    public SeatCalendar(Restaurant r, LocalDate date, LocalTime start, LocalTime end) {
        this.r = r;
        maxSeatingDuration = r.getMaxSeatingDuration();
        currentDate = date;
        availableHoursEnd = end;

        // If the day is today, passed timeslots should not be created
        if( date.isEqual(LocalDate.now()))
        {
            LocalTime i;
            for ( i = start; i.isBefore(LocalTime.now()); i = i.plusMinutes(15))
            {
                // do nothing
            }
            availableHoursStart = i;
        }
        else
        {
            availableHoursStart = start;
        }

        createTimeSlots( availableHoursStart, getLastReservationTime().plusMinutes(1));
    }

    // This constructor is used for updating purposes
    public SeatCalendar( int maxSeatingDuration, LocalDate date, LocalTime start, LocalTime end){
        this.maxSeatingDuration = maxSeatingDuration;
        currentDate = date;
        availableHoursEnd = end;

        // If the day is today, passed timeslots should not be created
        if( date.isEqual(LocalDate.now()))
        {
            LocalTime i;
            for ( i = start; i.isBefore(LocalTime.now()); i = i.plusMinutes(15))
            {
                // do nothing
            }
            availableHoursStart = i;
        }
        else
        {
            availableHoursStart = start;
        }

        createTimeSlots( availableHoursStart, getLastReservationTime().plusMinutes(1));
    }

    // methods

    /**
     * It creates the calendar according to opening and closing hour of the restaurant,
     * and initializes the timeslots’ reserved status false
     * @param start opening hour of the restaurant( if it is today, first time after the current time)
     * @param end last reservation time of the restaurant
     */
    private void createTimeSlots( LocalTime start, LocalTime end)
    {
        TimeSlot ts;
        System.out.println(start.toString());
        System.out.println(end.toString());
        // 15 represent the intervals between two timeslots (09:15-10:00 and 09.30-10:15), we can change if needed
        for( LocalTime i = start; i.isBefore(end); i = i.plusMinutes(15))
        {
            if ( r != null) maxSeatingDuration = r.getMaxSeatingDuration();
            ts = new TimeSlot(maxSeatingDuration, currentDate, i);
            ts.setReservedStatus(false);
            this.put("" + (i.getHour() * 60 + i.getMinute()), ts);
        }
    }

    /**
     * for a restaurant that closes at 23:30 and have 1 hour of seating duration it will be 22:30
     * @return last reservation time of the restaurant
     */
    private LocalTime getLastReservationTime() {
        return availableHoursEnd.minusMinutes(maxSeatingDuration);
    }
}