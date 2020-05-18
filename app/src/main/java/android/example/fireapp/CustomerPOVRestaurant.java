package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
 This class a class that enables customers to see the restaurants profiles. They can see the menu of
 restaurants in another activity directed from here or they can initiate the reservation making
 process by clicking to the related button.
 */
public class CustomerPOVRestaurant extends AppCompatActivity {
   //Properties
    TextView tvName, tvRating, tvDescription, tvMinPriceToPreOrder;
    DatabaseReference mRefRes;
    Button showMenu, makeReservation;
    ListView listView;
    ArrayAdapter myAdapter;
    ArrayList<String> menu = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_p_o_v_restaurant);

        //Initialize
        Intent intent = getIntent();
        String uid = intent.getStringExtra("UID");

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

        //Methods called
        placeDatatoTVs();
        showMenuAction();
        makeReservationAction();

        mRefRes.child(uid).child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menu.clear();
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {

                    DataSnapshot item = items.next();
                    String name;
                    name = "" + item.child("name").getValue().toString() + ": "
                            + item.child("ingredients").getValue().toString() +
                            "___" + item.child("price").getValue().toString() + "TL";

                    menu.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                tvMinPriceToPreOrder.setText("Minimum price for pre-order is " + resMinPrice + " g3Coins.");

                if ( !resDescription.isEmpty())
                    tvDescription.setText("Description: " + resDescription);
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
