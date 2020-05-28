package android.example.fireapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * The calender for seat objects
 * @date 30.04.2020
 * @author Group_g3C
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

    public SeatCalendar( int maxSeatingDuration, LocalDate date, LocalTime start, LocalTime end){
        this.maxSeatingDuration = maxSeatingDuration;
        currentDate = date;
        availableHoursEnd = end;

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

    public SeatCalendar(Restaurant r, LocalDate date, LocalTime start, LocalTime end) {
        this.r = r;
        maxSeatingDuration = r.getMaxSeatingDuration();
        currentDate = date;
        availableHoursEnd = end;

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

    // Methods

    private void createTimeSlots( LocalTime start, LocalTime end)
    {
        TimeSlot ts;
        System.out.println(start.toString());
        System.out.println(end.toString());
        for( LocalTime i = start; i.isBefore(end); i = i.plusMinutes(15)) // 15 represent intervals, we can change if needed
        {
            if ( r != null) maxSeatingDuration = r.getMaxSeatingDuration();
            ts = new TimeSlot(maxSeatingDuration, currentDate, i);
            ts.setReservedStatus(false);
            this.put("" + (i.getHour() * 60 + i.getMinute()), ts);
        }
    }

//    public boolean isTimeSlotAvailable(LocalTime time) {
//        return !this.get("" + time.getHour()*60 + time.getMinute()).isReserved();
//    }

//    public void setRelatedSlotsReserved(LocalDateTime dateAndTime, boolean b) {
//        for ( int i = dateAndTime.getHour()*60 + dateAndTime.getMinute();
//              (i < getLastReservationTime().getHour()*60 + getLastReservationTime().getMinute() + 1) && (i < dateAndTime.getHour()*60 + dateAndTime.getMinute() + TimeSlot.durationOfMeal);
//              i++) {
//              this.get(i).setReserved(b);
//        }
//    }
//
//    public TimeSlot getTimeSlotByStartTime(int time) // here time is a minute representation
//    {
//        TimeSlot ts;
//
//        for( String s : this.keySet()){
//            if(("" + time).equals( this.get(s)))
//                return this.get(s);
//        }
//        return null;
//    }

    private LocalTime getLastReservationTime() {
        // to do
        return availableHoursEnd.minusMinutes(maxSeatingDuration);

    }
}