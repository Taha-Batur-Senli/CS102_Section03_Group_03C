package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class MakeReservationCustomerP1 extends AppCompatActivity {
    CalendarView calendar;
    TextView tvDate;

    //TODO https://www.youtube.com/watch?v=yrpimdBRk5Q
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation_customer_p1);

        calendar = (CalendarView)findViewById(R.id.calendarView);
        tvDate = (TextView)findViewById(R.id.txtDate);

        //Make previous dates unclickable
        calendar.setMinDate((new Date().getTime()));

        //Make dates after one week unclickable
        Date today = new Date();
        Date weekAfter = new Date(today.getTime() + 7*(1000 * 60 * 60 * 24));
        calendar.setMaxDate( weekAfter.getTime());

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                date += "\n Please select a time now!";
                tvDate.setText(date);
            }
        });
    }
}
