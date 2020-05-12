package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 This class a class that enables customers to see the restaurants profiles. They can see the menu of
 restaurants in another activity directed from here or they can initiate the reservation making
 process by clicking to the related button.
 */
public class CustomerPOVRestaurant extends AppCompatActivity {
   //Properities
    TextView tvName, tvRating, tvDescription, tvGenre, tvWorkingHours, tvMinPriceToPreOrder, tvPhone, tvAdress;
    DatabaseReference mRefRes;
    Button showMenu, makeReservation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_p_o_v_restaurant);

        //Initialize
        tvName = (TextView)findViewById(R.id.txtNamePOV);
        tvRating = (TextView)findViewById(R.id.txtRatingPOV);
        tvDescription = (TextView)findViewById(R.id.txtDescriptionPOV);
        tvGenre = (TextView)findViewById(R.id.txtGenrePOV);
        tvWorkingHours = (TextView)findViewById(R.id.txtWorkingHoursPOV);
        tvMinPriceToPreOrder = (TextView)findViewById(R.id.txtMinPricePreOrderPOV);
        tvPhone = (TextView)findViewById(R.id.txtPhonePOV);
        tvAdress = (TextView)findViewById(R.id.txtAdressPOV);
        showMenu = (Button)findViewById(R.id.btnShowMenuPOV);
        makeReservation = (Button)findViewById(R.id.btnMakeReservationCustomer);

        mRefRes = FirebaseDatabase.getInstance().getReference("Restaurants");

        //Methods called
        placeDatatoTVs();
        showMenuAction();
        makeReservationAction();
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
                final String resGenre = dataSnapshot.child("genre").getValue(String.class);
                final String resWH = dataSnapshot.child("workingHours").getValue(String.class);
                final double resMinPrice = dataSnapshot.child("minPriceToPreOrder").getValue(Double.class);
                final String resPhone = dataSnapshot.child("phone").getValue(String.class);
                final String resAdress = dataSnapshot.child("adress").getValue(String.class);

                tvName.setText("" + resName );
                tvRating.setText("" + resRating + "/5");
                tvGenre.setText("Genre: " + resGenre );
                tvMinPriceToPreOrder.setText("Min price limit to pre-order: " + resMinPrice);

                if ( !resDescription.isEmpty())
                    tvDescription.setText("Description: " + resDescription);
                if ( !resWH.isEmpty())
                    tvWorkingHours.setText("Working hours: " + resWH);
                if ( !resPhone.isEmpty())
                    tvPhone.setText("GSM: +90 " + resPhone);
                if ( !resAdress.isEmpty())
                    tvAdress.setText("Adress: " + resAdress);
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
        startActivity(new Intent(getApplicationContext(),CustomerProfile.class));
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
