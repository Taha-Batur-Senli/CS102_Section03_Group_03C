package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
// import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/*
 This class enables customers to see the restaurants profiles. They can see the menu of
 restaurants in another activity directed from here or they can initiate the reservation making
 process by clicking to the related button.
 */
public class CustomerPOVRestaurant extends AppCompatActivity {
    // properties
    private ImageView logo;
    private TextView tvName, tvRating, tvDescription, tvMinPriceToPreOrder;
    private DatabaseReference mRefRes;
    private Button showMenu, makeReservation;
    private ListView listView;
    private ArrayAdapter myAdapter;
    private ArrayList<String> menu = new ArrayList<String>();
    private Uri uri;
    private Upload upload;
    //Properities



    // methods
    @Override
    /**
     * This is a method that is called every time this activity is opened and in which we initialize properties.
     * Other methods are called inside it.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_p_o_v_restaurant);

        // Initializing
        logo = findViewById(R.id.logo);
        tvName = (TextView)findViewById(R.id.txtNamePOV);
        tvRating = (TextView)findViewById(R.id.txtRatingPOV);
        tvDescription = (TextView)findViewById(R.id.txtDescriptionPOV);

        tvMinPriceToPreOrder = (TextView)findViewById(R.id.txtMinPricePreOrderPOV);

        showMenu = (Button)findViewById(R.id.btnShowMenuPOV);
        makeReservation = (Button)findViewById(R.id.btnMakeReservationCustomer);
        listView = findViewById(R.id.lvMenuRestaurant);

        mRefRes = FirebaseDatabase.getInstance().getReference("Restaurants");
        myAdapter = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, menu);
        listView.setAdapter(myAdapter);



        Intent intent = getIntent();
        final String uid = intent.getStringExtra("UID");

            mRefRes.child(uid).child("Pictures").child("Logo").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //dataSnapshot is a object that store datas like arraylists.
                    if(dataSnapshot.getValue() != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //checking the every object of data
                            upload = snapshot.getValue(Upload.class);
                        }

                        if (upload.getmImageURL() != null) {
                            Picasso.with(CustomerPOVRestaurant.this).load(upload.getmImageURL()).into(logo);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        //Methods called

        placeDatatoTVs();
        showMenuAction();
        makeReservationAction();

        // updater

        mRefRes.child(uid).child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menu.clear();
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {

                    DataSnapshot item = items.next();
                    String name;
                    name = "" + item.child("name").getValue().toString() + ":\n"
                            + item.child("ingredients").getValue().toString() + "  "
                            + item.child("price").getValue().toString() + " g3Coins";

                    menu.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mRefRes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Object restaurant = dataSnapshot.child(uid).getValue();
                    HashMap<String, Object> r = (HashMap<String, Object>) restaurant;
                            int i = 1;
                            for (DataSnapshot snapshot : dataSnapshot.child(uid).child("seats").getChildren()) {
                                HashMap<String, Object> seatWeeklyPlan = new HashMap<String, Object>();
                                for (DataSnapshot snapshot2 : dataSnapshot.child(uid).child("seats").child("seat" + i).getChildren()) {
                                    String dateName = snapshot2.getKey();

                                    if (LocalDate.parse(dateName).isBefore(LocalDate.now())) {
                                        System.out.println("ONCEYIMMM " + dateName);

                                        long maxSeatingDura = (long) r.get("maxSeatingDuration");
                                        int maxSeatingDuration = (int)maxSeatingDura;

                                        String openingTime = (String)r.get("openingTime");
                                        String[] temp = openingTime.split(":");
                                        int otHour = Integer.parseInt( temp[0]);
                                        int otMinute = Integer.parseInt( temp[1]);

                                        String closingTime = (String)r.get("closingTime");
                                        String[] temp1 = closingTime.split(":");
                                        int ctHour = Integer.parseInt( temp1[0]);
                                        int ctMinute = Integer.parseInt( temp1[1]);

                                        HashMap<String, Object> newSeatCalendar = new SeatCalendar(maxSeatingDuration, LocalDate.parse(dateName).plusDays(7), LocalTime.of(otHour, otMinute), LocalTime.of(ctHour, ctMinute));
                                        seatWeeklyPlan.put(LocalDate.parse(dateName).plusDays(7).toString(), newSeatCalendar);

                                    } else if (LocalDate.parse(dateName).isAfter(LocalDate.now())) {
                                        System.out.println("SONRAYIMMM " + dateName);
                                        Object existingSeatCalendar = snapshot2.getValue(); // Object is HashMap<String, Object>
                                        seatWeeklyPlan.put(dateName, existingSeatCalendar);
                                    } else {
                                        System.out.println("BUGUNDEYIMMMMMMM " + dateName);
                                        Object existingSeatCalendar = snapshot2.getValue();
                                        HashMap<String, Object> todaysMap = (HashMap<String, Object>) existingSeatCalendar;
                                        Iterator it = todaysMap.keySet().iterator();
                                        while (it.hasNext()) {
                                            String str = it.next().toString();
                                            int minutes = Integer.parseInt(str);
                                            if (minutes < LocalTime.now().getHour() * 60 + LocalTime.now().getMinute()) {
                                                it.remove();
                                            }
                                        }
                                        seatWeeklyPlan.put(dateName, todaysMap);
                                    }

                                }
                                mRefRes.child(uid).child("seats").child("seat" + i).setValue(seatWeeklyPlan);
                                i++;
                            }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //METHODS

    /*
    This method initalizes reservation making process. The requires data are passed to the next activity.
     */
    private void makeReservationAction() {
        makeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String uid = intent.getStringExtra("UID");

                mRefRes.child(uid).child("minPriceToPreOrder").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String minPrice = dataSnapshot.getValue().toString();
                        Intent intent = getIntent();
                        String uid = intent.getStringExtra("UID");
                        Intent intent2 = new Intent(CustomerPOVRestaurant.this, MakeReservationCustomerP1.class);
                        intent2.putExtra("UID", uid);
                        intent2.putExtra("MINPRICE", minPrice);
                        startActivity( intent2);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    /*
    This method retrieves data from firebase and puts the related data to related text views.
     */
    private void placeDatatoTVs() {
        Intent intent = getIntent();
        String uid = intent.getStringExtra("UID");
        mRefRes.child( uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String resName = dataSnapshot.child("name").getValue(String.class);
                final double resRating = dataSnapshot.child("rating").getValue(Double.class);
                final String resDescription = dataSnapshot.child("description").getValue(String.class);
                final double resMinPrice = dataSnapshot.child("minPriceToPreOrder").getValue(Double.class);

                if( resRating >= 4)
                {
                    tvRating.setBackgroundColor( getResources().getColor(R.color.green));
                }
                else if( resRating >= 3)
                {
                    tvRating.setBackgroundColor( getResources().getColor(R.color.light_green));
                }
                else if ( resRating >= 2)
                {
                    tvRating.setBackgroundColor( getResources().getColor(R.color.orange));
                }
                else if ( resRating >= 1)
                {
                    tvRating.setBackgroundColor( getResources().getColor(R.color.light_red));
                }
                else
                    tvRating.setBackgroundColor( getResources().getColor(R.color.red));

                tvName.setText("" + resName );
                tvRating.setText("" + resRating + "/5");
                tvMinPriceToPreOrder.setText("Min price limit to pre-order: " + resMinPrice);

                if ( !resDescription.isEmpty())
                    tvDescription.setText("Description:\n" + resDescription);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*
    This overriden method prevents some bugs. It is crucial to finish this activity if customer
    presses to the back button. Else, multiple times of back button may lead back again to this activity.
*/
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();

    }

    private void showMenuAction() {
        showMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String uid = intent.getStringExtra("UID");
                Intent intent2 = new Intent(CustomerPOVRestaurant.this, ShowMenuPOV.class);
                intent2.putExtra("UID", uid);
                startActivity( intent2);
            }
        });
    }

}