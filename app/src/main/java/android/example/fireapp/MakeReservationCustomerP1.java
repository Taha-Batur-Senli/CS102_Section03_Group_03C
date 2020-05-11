package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class MakeReservationCustomerP1 extends AppCompatActivity {
    CalendarView calendar;
    TextView tvDate;
    ListView lvTables;//, lvTimes;
    ArrayAdapter myAdapter;//, myAdapter2;
    ArrayList<String> allSeats = new ArrayList<>();
    //ArrayList<String> allTimes = new ArrayList<>();
    DatabaseReference reference;

    //TODO https://www.youtube.com/watch?v=yrpimdBRk5Q
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation_customer_p1);

        calendar = (CalendarView)findViewById(R.id.calendarView);
        tvDate = (TextView)findViewById(R.id.txtDate);
        lvTables = (ListView)findViewById(R.id.lvSeatSelection);
        //lvTimes = (ListView)findViewById(R.id.lvTimesSelection);


        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allSeats);
        //myAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allTimes);
        /*  <ListView
        android:id="@+id/lvTimesSelection"
        android:layout_width="409dp"
        android:layout_height="185dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="1.0" />*/
        lvTables.setAdapter(myAdapter);
        //lvTimes.setAdapter(myAdapter2);
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

                //LocalDate date1 = new LocalDate(year, month, dayOfMonth);
                displaySeats( year, month, dayOfMonth);
            }
        });
    }

    public void displaySeats( final int year, final int month, final int dayOfMonth){
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
                    String seatNameFinal = "Table " + seatName.charAt(seatName.length() - 1);

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

    private void selectTime(final int year, final int month, final int dayOfMonth){
        lvTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String seatName = allSeats.get(position);
                String seat  = "seat" + seatName.charAt(seatName.length()-1);
                //deneme1.setText(seat);
                String date = year + "-" + (month + 1 )+ "-"  + dayOfMonth ;
                if ( (month + 1) < 10 )
                    date =  year + "-0" + (month + 1 )+ "-"  + dayOfMonth ;

                //deneme2.setText(date);
                Intent intent = getIntent();
                String uidRestaurant = intent.getStringExtra("UID");
                String minPrice = intent.getStringExtra("MINPRICE");

                Intent intent2 = new Intent(MakeReservationCustomerP1.this, MakeReservationCustomerP2.class);
                intent2.putExtra("UID", uidRestaurant);
                intent2.putExtra("SEAT", seat);
                intent2.putExtra("DATE", date);
                intent2.putExtra("MINPRICE", minPrice);

                startActivity(intent2);


                /*final DatabaseReference refAvailableHours = FirebaseDatabase.getInstance().getReference("SeatPlans")
                        .child(uidRestaurant).child(seat).child(date);
                refAvailableHours.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        while(items.hasNext()) {
                            DataSnapshot item1 = items.next();
                            if (item1.child("reserved").getValue().toString().equals("false") ){

                                String timeSlot =  item1.child("firstTime").child("hour").getValue().toString() + ":" +
                                        item1.child("firstTime").child("minute").getValue().toString() + " - " +
                                        item1.child("endTime").child("hour").getValue().toString() + ":" +
                                        item1.child("endTime").child("minute").getValue().toString();
                                allTimes.add(timeSlot);
                                myAdapter2.notifyDataSetChanged();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

            }
        });
    }

}
