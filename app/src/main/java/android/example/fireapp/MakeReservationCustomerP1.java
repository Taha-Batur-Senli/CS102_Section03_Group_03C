package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

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

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + month + "/" + year;
                tvDate.setText(date);
            }
        });
    }
}
