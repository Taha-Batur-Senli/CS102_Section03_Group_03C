package android.example.fireapp;

import android.os.Build;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlot{ // implements Reservable {

    // properties

    private LocalDate date;
    private LocalTime firstTime; // in terms of minute
    private LocalTime endTime;
    public static int durationOfMeal = 60; // in terms of minute (90 minute)
    private boolean reservedStatus;

    // constructors

    public TimeSlot( LocalDate date, LocalTime firstTime)
    {
        this.date = date;
        this.firstTime = firstTime;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.endTime = this.firstTime.plusMinutes(durationOfMeal);
        }


    }

    // methods


    public void setReserved( boolean a) {
        reservedStatus = a;

    }

    public boolean isReserved() {
        return reservedStatus;
    }

    public String toString()
    {
        return "Date: " + date + "  Time: " + firstTime + "-" + endTime;
    }

    public LocalTime getFirstTime()
    {
        return firstTime;
    }
}
