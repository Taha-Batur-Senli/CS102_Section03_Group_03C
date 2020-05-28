package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * This class is for displaying reservations on restaurant owner's GUI
 * @date 11.05.2020
 * @author Group_g3C
 */
public class RestaurantReservationsActivity extends AppCompatActivity {

    //Properties

    ListView lvCurrentReservations, lvPastReservations;
    ArrayAdapter myAdapter, myAdapter2;
    ArrayList<String> pastReservations = new ArrayList<>();
    ArrayList<String> currentReservations = new ArrayList<>();

    FirebaseUser user;
    DatabaseReference refCurrentReservations, refPastReservations;

    //Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_restaurant_reservations);

        lvCurrentReservations = (ListView)findViewById(R.id.lvCurrentRezRestaurant);
        lvPastReservations = (ListView)findViewById(R.id.lvPastRezRestaurant);
        myAdapter = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, currentReservations);
        myAdapter2 = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, pastReservations);
        lvCurrentReservations.setAdapter(myAdapter);
        lvPastReservations.setAdapter(myAdapter2);

        refCurrentReservations = FirebaseDatabase.getInstance().getReference("Reservations").child("CurrentReservations");
        refPastReservations = FirebaseDatabase.getInstance().getReference("Reservations").child("PastReservations");
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Methods called
        updatePastReservations();
        displayCurrentReservations();
        displayPastReservations();
        showCurrentReservation();
        showPastReservation();
    }

    private void showPastReservation() {
        lvPastReservations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String reservationTxt = pastReservations.get(position);
                Intent i = new Intent(RestaurantReservationsActivity.this, ShowCurrentReservation.class);
                i.putExtra("RESERVATION", reservationTxt);
                startActivity(i);
            }
        });
    }

    private void showCurrentReservation() {
        lvCurrentReservations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String reservationTxt = currentReservations.get(position);
                Intent i = new Intent(RestaurantReservationsActivity.this, ShowCurrentReservation.class);
                i.putExtra("RESERVATION", reservationTxt);
                startActivity(i);
            }
        });
    }

    private void updatePastReservations() {
        refCurrentReservations.orderByChild("restaurantID").equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {
                    DataSnapshot item = items.next();
                    String timeSlot = item.child("timeSlot").getValue().toString();
                    String date = item.child("date").getValue().toString();

                    LocalDate dateOfRez = LocalDate.parse(date);
                    LocalDate today = LocalDate.now();

                    int h = (int) (Integer.parseInt(timeSlot) / 60);
                    int m = Integer.parseInt(timeSlot) % 60;

                    LocalTime rezTime = LocalTime.of(h, m);//.plusHours(1);
                    LocalTime now = LocalTime.now();

                    if (dateOfRez.isBefore(today) || (dateOfRez.isEqual(today) && rezTime.isBefore(now)))
                    {
                        //Clone the reservation & add it to past reservations & remove from current reservations
                        String resName = item.child("restaurantName").getValue().toString();
                        String resID = item.child("restaurantID").getValue().toString();
                        String cusName = item.child("cusName").getValue().toString();
                        String cusID = item.child("cusID").getValue().toString();
                        String resPhone = item.child("restaurantPhone").getValue().toString();
                        String cusPhone = item.child("cusPhone").getValue().toString();
                        String proOrder = item.child("preOrder").getValue().toString();
                        String preOrderTxt = item.child("preOrderText").getValue().toString();
                        String seat = item.child("seat").getValue().toString();
                        String totalPrice = item.child("totalPrice").getValue().toString();
                        String timeSlotClone = item.child("timeSlot").getValue().toString();
                        String dateClone = item.child("date").getValue().toString();
                        String uid = item.child("reservID").getValue().toString();
                        Reservation r = new Reservation(uid, cusID, resID, cusName, resName,
                                cusPhone, resPhone, proOrder, dateClone, timeSlotClone, totalPrice, seat);
                        refPastReservations.child(uid).setValue(r);
                        refPastReservations.child(uid).child("preOrderText").setValue(preOrderTxt);
                        refCurrentReservations.child(uid).removeValue();

                    }

                    //currentReservations.add(toString);
                    myAdapter.notifyDataSetChanged();
                    myAdapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void displayPastReservations() {
        refPastReservations.orderByChild("restaurantID").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pastReservations.clear();
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {
                    DataSnapshot item = items.next();
                    //String resName = item.child("restaurantName").getValue().toString();
                    String cusName = item.child("cusName").getValue().toString();
                    //String resPhone = item.child("restaurantPhone").getValue().toString();
                    String cusPhone = item.child("cusPhone").getValue().toString();
                    String proOrder = item.child("preOrder").getValue().toString();
                    String preOrderTxt = (String)item.child("preOrderText").getValue();
                    String seat = item.child("seat").getValue().toString();
                    String totalPrice = item.child("totalPrice").getValue().toString();
                    String timeSlot = item.child("timeSlot").getValue().toString();
                    String date = item.child("date").getValue().toString();
                    String table = "Table " + seat.substring(4);
                    int h = (int) (Integer.parseInt(timeSlot) / 60);
                    int m = Integer.parseInt(timeSlot) % 60;
                    String hS = String.valueOf(h);
                    if (h == 0)
                        hS = "00";
                    String mS = String.valueOf(m);
                    if (m == 0)
                        mS = "00";
                    String timeSlotString = hS + ":" + mS;

                    String toString = cusName + "\n" + date + "   " + timeSlotString + " " + "" + table + "\n" +
                            proOrder + "___" + totalPrice + " g3Coins\nRestaurant info: +90 " + cusPhone;
                    toString += "\n\n\nPre-order: \n" + preOrderTxt;

                    pastReservations.add(toString);
                    myAdapter2.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayCurrentReservations() {
        refCurrentReservations.orderByChild("restaurantID").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentReservations.clear();
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {
                    DataSnapshot item = items.next();
                    //String resName = item.child("restaurantName").getValue().toString();
                    String cusName = item.child("cusName").getValue().toString();
                    //String resPhone = item.child("restaurantPhone").getValue().toString();
                    String cusPhone = item.child("cusPhone").getValue().toString();
                    String proOrder = item.child("preOrder").getValue().toString();
                    String preOrderTxt = item.child("preOrderText").getValue().toString();
                    String seat = item.child("seat").getValue().toString();
                    String totalPrice = item.child("totalPrice").getValue().toString();
                    String timeSlot = item.child("timeSlot").getValue().toString();
                    String date = item.child("date").getValue().toString();
                    String table = "Table " + seat.substring(4);
                    int h = (int) (Integer.parseInt(timeSlot) / 60);
                    int m = Integer.parseInt(timeSlot) % 60;
                    String hS = String.valueOf(h);
                    if (h == 0)
                        hS = "00";
                    String mS = String.valueOf(m);
                    if (m == 0)
                        mS = "00";
                    String timeSlotString = hS + ":" + mS;

                    String toString = cusName + "\n" + date + "   " + timeSlotString + " " + "" + table + "\n" +
                            proOrder + "___" + totalPrice + " g3Coins\nCustomer info: +90 " + cusPhone;
                    toString += "\n\n\nPre-order: \n" + preOrderTxt;

                    currentReservations.add(toString);
                    myAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
