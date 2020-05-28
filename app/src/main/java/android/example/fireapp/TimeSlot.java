package android.example.fireapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class represents a time slot in which reservations can be made.
 * For example (15:00 – 16:30) can be considered as a timeslot.
 * @date 09.05.2020
 * @author Group 3C
 */
public class TimeSlot{

    // properties
    private LocalDateTime dateAndTimeStart; // It was local and was visible on Firebase but some bugs were occuring
    private LocalDateTime dateAndTimeEnd; // It was local and was visible on Firebase but some bugs were occuring
    public String timeSlot;
    public boolean reservedStatus;
    private int maxSeatingDuration;
    public int layer;

    // constructors
    public TimeSlot(){

    }

    public TimeSlot( int maxSeatingDuration, LocalDate date, LocalTime firstTime)
    {
        layer = 0;
        this.maxSeatingDuration = maxSeatingDuration;
        this.dateAndTimeStart = LocalDateTime.of(date, firstTime);
        this.dateAndTimeEnd = LocalDateTime.of(date, firstTime.plusMinutes(maxSeatingDuration));
        this.timeSlot = firstTime + " - " + firstTime.plusMinutes(maxSeatingDuration);
    }

    // methods

    // getter and setters
    public void setReservedStatus( boolean a) {
        reservedStatus = a;
    }

    public boolean getReservedStatus() {
        return reservedStatus;
    }

    public String getTimeSlot(){
        return timeSlot;
    }

    public int getLayer(){
        return layer;
    }
}