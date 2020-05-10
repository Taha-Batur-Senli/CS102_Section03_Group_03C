package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class MakeReservationCustomerP1 extends AppCompatActivity {
    CalendarView calendar;
    TextView tvDate;
    ListView lvTables;
    ArrayAdapter myAdapter;
    ArrayList<String> allSeats = new ArrayList<>();
    DatabaseReference reference;


    //TODO https://www.youtube.com/watch?v=yrpimdBRk5Q
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation_customer_p1);

        calendar = (CalendarView)findViewById(R.id.calendarView);
        tvDate = (TextView)findViewById(R.id.txtDate);
        lvTables = (ListView)findViewById(R.id.lvSeatSelection);

        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allSeats);
        lvTables.setAdapter(myAdapter);
        reference = FirebaseDatabase.getInstance().getReference();

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
                date += "\n Please select a table now!";
                tvDate.setText(date);

                displaySeats();
            }
        });
    }

    public void displaySeats(){
        //get restaurants uid from previous class
        Intent intent = getIntent();
        String uidRestaurant = intent.getStringExtra("UID");

        reference.child("SeatPlans").child(uidRestaurant).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()){

                    DataSnapshot item = items.next();
                    String seatName;
                    seatName = "" + item.getKey();
                    seatName.replaceAll("seat", "Table ");

                    allSeats.add(seatName);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
