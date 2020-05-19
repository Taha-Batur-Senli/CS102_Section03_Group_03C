package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 In this class, customers are displayed the available timeslots of the table they have selected on
 a specified date. Then they select a time slot and can either finish their reservation or can pre-order.
 */
public class MakeReservationCustomerP2 extends AppCompatActivity {
    //Properties
    ListView lvAvailableTimeSlots;
    ArrayAdapter myAdapter;
    ArrayList<String> allTimes = new ArrayList<>();
    DatabaseReference reference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_make_reservation_customer_p2);

        //Initialize
        lvAvailableTimeSlots = (ListView)findViewById(R.id.listViewAvailableTimeSlots);
        myAdapter = new ArrayAdapter<>(this, R.layout.listrow, R.id.textView2, allTimes);
        lvAvailableTimeSlots.setAdapter(myAdapter);
        reference = FirebaseDatabase.getInstance().getReference();


        user = FirebaseAuth.getInstance().getCurrentUser();

        Intent i = getIntent();
        String uidRestaurant = i.getStringExtra("UID");
        String seat = i.getStringExtra("SEAT");
        String date = i.getStringExtra("DATE");

        //Print out only the non reserved time slots.
        final DatabaseReference refAvailableHours = FirebaseDatabase.getInstance().getReference("Restaurants")
                .child(uidRestaurant).child("seats").child(seat).child(date);

        refAvailableHours.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while(items.hasNext()) {
                    DataSnapshot item1 = items.next();
                    if (item1.child("reservedStatus").getValue().toString().equals("false") ){

                        /*String timeSlot =  item1.child("firstTime").child("hour").getValue().toString() + ":" +
                                item1.child("firstTime").child("minute").getValue().toString() + "-" +
                                item1.child("endTime").child("hour").getValue().toString() + ":" +
                                item1.child("endTime").child("minute").getValue().toString();
                        //String timeSlot = item1.getKey();*/
                        String timeSlot = item1.child("timeSlot").getValue().toString();

                        allTimes.add(timeSlot);
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        selectTime();
    }

    /**
     This method makes timeslots clickable. When customer selects a time slot, they are asked if they
     want to pre-order or finish their reservation.
     */
    private void selectTime(){
        lvAvailableTimeSlots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MakeReservationCustomerP2.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("There is a minimum price limit to pre-order! ")
                        .setMessage("Do you want to pre-order?")
                        .setPositiveButton("Pre-order", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = getIntent();
                                String resUid = intent.getStringExtra("UID");
                                String minPrice = intent.getStringExtra("MINPRICE");
                                String seat = intent.getStringExtra("SEAT");
                                String date = intent.getStringExtra("DATE");

                                Intent intent2 = new Intent(MakeReservationCustomerP2.this, PreOrderActivity.class);
                                intent2.putExtra("UID", resUid);
                                intent2.putExtra("MINPRICE", minPrice);
                                intent2.putExtra("SEAT", seat);
                                intent2.putExtra("DATE", date);
                                String ts = allTimes.get(position);
                                intent2.putExtra("TIMESLOT", ts);
                                startActivity(intent2);
                                finish();


                            }
                        })
                        .setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = getIntent();
                                final String uidRestaurant = i.getStringExtra("UID");
                                final String seat = i.getStringExtra("SEAT");
                                final String date = i.getStringExtra("DATE");
                                final DatabaseReference mRefRez = FirebaseDatabase.getInstance().getReference("Restaurants");

                                //Determine timeslots numeric value
                                String ts = allTimes.get(position);
                                String[] temp = ts.split(" - ");
                                String[] temp2 = temp[0].split(":");
                                final int timeSlot = ((Integer.parseInt(temp2[0]) * 60 ) + Integer.parseInt(temp2[1]));

                                // setting related timeslots reserved
                                mRefRez.child(uidRestaurant).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // finding maxSeatingDuration
                                        long maxSeatingDura = (long)dataSnapshot.child("maxSeatingDuration").getValue();
                                        int maxSeatingDuration = (int)maxSeatingDura;
                                        // iteration through timeslots
                                        for ( DataSnapshot snapshot : dataSnapshot.child("seats").child(seat).child(date).getChildren()){
                                            String rTimeSlot = (String)snapshot.getKey();
                                            int relatedTimeSlot = Integer.parseInt(rTimeSlot);
                                            // setting related timeslots reserved
                                            if( Math.abs(timeSlot - relatedTimeSlot) + 1 <= (int)maxSeatingDuration){
                                                mRefRez.child(uidRestaurant).child("seats").child(seat).child(date).child(String.valueOf(relatedTimeSlot)).child("reservedStatus").setValue(true);
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                                // mRefRez.child(uidRestaurant).child("seats").child(seat).child(date).child(String.valueOf(timeSlot)).child("reservedStatus").setValue("true");

                                final DatabaseReference mCustomer = FirebaseDatabase.getInstance().getReference("Customers").child(user.getUid());
                                final DatabaseReference mRestaurant = FirebaseDatabase.getInstance().getReference("Restaurants").child(uidRestaurant);
                                final DatabaseReference mReservation = FirebaseDatabase.getInstance().getReference("Reservations").child("CurrentReservations");

                                final String rezID = mReservation.push().getKey();

                                mRestaurant.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        final String restaurantName = dataSnapshot.child("name").getValue().toString();
                                        final String restaurantPhone = dataSnapshot.child("phone").getValue().toString();

                                        mCustomer.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String cusName = dataSnapshot.child("name").getValue().toString();
                                                String cusPhone = dataSnapshot.child("phone").getValue().toString();

                                                Reservation reservation = new Reservation( rezID, user.getUid(), uidRestaurant, cusName, restaurantName,
                                                        cusPhone, restaurantPhone, "Pre-order cost", date, String.valueOf(timeSlot),
                                                        "0", seat );
                                                mReservation.child(rezID).setValue(reservation);
                                                mReservation.child(rezID).child("preOrderText").setValue("No pre-order!");
                                                // mCustomer.child("reservations").child(rezID).setValue(rezID);
                                                // mRestaurant.child("reservations").child(rezID).setValue(rezID);
                                                startActivity(new Intent(MakeReservationCustomerP2.this, MainActivity.class));
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }).show();
            }
        });
    }

    /**
     This method prevents some bugs.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
