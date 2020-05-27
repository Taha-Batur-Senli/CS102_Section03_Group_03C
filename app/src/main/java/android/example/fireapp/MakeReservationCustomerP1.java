package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/*
* This class is the first page for reservation making process. Here, customers can see the available
* days that they can make a reservation to. After they select a date, they are asked to select a
* table as well. If a restaurant has five tables, customers will be asked to choose one from five.
 *@date 27.05.2020
 *@author Group 3C
 */

public class MakeReservationCustomerP1 extends AppCompatActivity {
    //Properties
    CalendarView calendar;
    ListView lvTables;
    ArrayAdapter myAdapter;
    ArrayList<String> allSeats = new ArrayList<>();
    DatabaseReference reference;
    TextView txtEditRes11;
    ImageView tableLine;
    Button showTables;
    String uidRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_make_reservation_customer_p1);

        //Initialize

        calendar = (CalendarView)findViewById(R.id.calendarView);
        lvTables = (ListView)findViewById(R.id.lvSeatSelection);
        txtEditRes11 = findViewById(R.id.txtEditRes11);
        tableLine = findViewById(R.id.imageView59);
        showTables = findViewById(R.id.tables_show);

        myAdapter = new ArrayAdapter<>(this, R.layout.listrow, R.id.textView2, allSeats);
        lvTables.setAdapter(myAdapter);

        reference = FirebaseDatabase.getInstance().getReference("Restaurants");

        Intent intent = getIntent();
        uidRestaurant = intent.getStringExtra("UID");

        //Make previous dates unclickable
        calendar.setMinDate((new Date().getTime()));

        //Make dates after one week unclickable
        Date today = new Date();
        Date weekAfter = new Date(today.getTime() + 7*(1000 * 60 * 60 * 24));
        calendar.setMaxDate( weekAfter.getTime());

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                allSeats.clear();
                txtEditRes11.setText("Select a Table");
                tableLine.setVisibility(View.VISIBLE);

                displaySeats( year, month, dayOfMonth);
            }
        });

        lvTables.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        showTables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeReservationCustomerP1.this, SeatingPlanPicsRecycler.class);
                intent.putExtra("restaurant_id", uidRestaurant);
                startActivity(intent);
            }
        });
    }

    //METHODS




    /**
    Gets the data of restaurant from database and creates a list view accordingly. Prints out
    all of the tables a restaurant have on the related list view.
     */
    public void displaySeats( final int year, final int month, final int dayOfMonth){
        //get restaurants uid from previous class


        reference.child(uidRestaurant).child("seats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allSeats.clear();
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()){

                    DataSnapshot item = items.next();
                    String seatName;
                    seatName = "" + item.getKey();
                    String seatNameFinal = "Table " + seatName.substring(4);

                    allSeats.add(seatNameFinal);
                    myAdapter.notifyDataSetChanged();

                    selectTime( year, month, dayOfMonth);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*
    This method makes the tables selectable. If a customer selects a table, then they are directed
    to the next activity. This method passes the data of selected date & table to next acitivity as well.
     */
    private void selectTime(final int year, final int month, final int dayOfMonth){
        lvTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String seatName = allSeats.get(position);
                String seat  = "seat" + seatName.substring(6);

                String date = year + "-" + (month + 1 )+ "-"  + dayOfMonth ;
                if ( (month + 1) < 10 )
                    date =  year + "-0" + (month + 1 )+ "-"  + dayOfMonth ;

                Intent intent = getIntent();
                String uidRestaurant = intent.getStringExtra("UID");
                String minPrice = intent.getStringExtra("MINPRICE");

                Intent intent2 = new Intent(MakeReservationCustomerP1.this, MakeReservationCustomerP2.class);
                intent2.putExtra("UID", uidRestaurant);
                intent2.putExtra("SEAT", seat);
                intent2.putExtra("DATE", date);
                intent2.putExtra("MINPRICE", minPrice);

                startActivity(intent2);
            }
        });
    }

}
