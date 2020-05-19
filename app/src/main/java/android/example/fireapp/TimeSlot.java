package android.example.fireapp;

import android.os.Build;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeSlot{


    // properties

    private LocalDateTime dateAndTimeStart;
    private LocalDateTime dateAndTimeEnd;
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