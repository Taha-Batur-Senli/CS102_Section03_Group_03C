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
    public static int durationOfMeal = 10; // in terms of minute (60 minute)
    public boolean reservedStatus;

    // constructors

    public TimeSlot(){

    }

    public TimeSlot( LocalDate date, LocalTime firstTime)
    {
        this.dateAndTimeStart = LocalDateTime.of(date, firstTime);
        this.dateAndTimeEnd = LocalDateTime.of(date, firstTime.plusMinutes(durationOfMeal));
        this.timeSlot = firstTime + " - " + firstTime.plusMinutes(durationOfMeal);
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

}