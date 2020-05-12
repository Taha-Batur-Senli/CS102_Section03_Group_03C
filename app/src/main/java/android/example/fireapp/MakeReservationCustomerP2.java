package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/*
In this class, customers are displayed the available timeslots of the table they have selected on
a specified date. Then they select a time slot and can either finish their reservation or can pre-order.
 */
public class MakeReservationCustomerP2 extends AppCompatActivity {
   //Properties
    TextView askPreOrder;
    Button yes, no;
    ListView lvAvailableTimeSlots;
    ArrayAdapter myAdapter;
    ArrayList<String> allTimes = new ArrayList<>();
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation_customer_p2);

        //Initialize
        lvAvailableTimeSlots = (ListView)findViewById(R.id.listViewAvailableTimeSlots);
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allTimes);
        lvAvailableTimeSlots.setAdapter(myAdapter);
        reference = FirebaseDatabase.getInstance().getReference();

        Intent i = getIntent();
        String uidRestaurant = i.getStringExtra("UID");
        String seat = i.getStringExtra("SEAT");
        String date = i.getStringExtra("DATE");

        //Print out only the non reserved time slots.
         final DatabaseReference refAvailableHours = FirebaseDatabase.getInstance().getReference("SeatPlans")
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

    /*
    This method makes timeslots clickable. When customer selects a time slot, they are asked if they
    want to pre-order or finish their reservation.
     */
    private void selectTime(){
        lvAvailableTimeSlots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MakeReservationCustomerP2.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("There is a minimum price limit to pre-order! ")
                        .setMessage("Do you want to pre-order?")
                        .setPositiveButton("Pre-order", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //FirebaseAuth.getInstance().signOut();
                                //startActivity(new Intent( RestaurantProfile.this, MainActivity.class));
                                Intent intent = getIntent();
                                String resUid = intent.getStringExtra("UID");
                                String minPrice = intent.getStringExtra("MINPRICE");

                                Intent intent2 = new Intent(MakeReservationCustomerP2.this, PreOrderActivity.class);
                                intent2.putExtra("UID", resUid);
                                intent2.putExtra("MINPRICE", minPrice);
                                startActivity(intent2);
                                finish();


                            }
                        })
                        .setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO finish reservation
                                startActivity(new Intent(MakeReservationCustomerP2.this, MyReservations.class));
                                finish();
                            }
                        })
                        .show();
            }
        });
    }

    /*
    This method prevents some bugs.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
