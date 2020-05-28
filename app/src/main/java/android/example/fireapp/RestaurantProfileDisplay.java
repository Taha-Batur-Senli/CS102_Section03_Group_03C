package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * This class enables restaurants to display their own profiles from the perspective of a customer.
 * @date 07.05.2020
 * @author Group_g3C
 */
public class RestaurantProfileDisplay extends AppCompatActivity {

    //Properties

    TextView name, wh, adress, phone, description, genre, minPrice, maxDuration;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser user;

    //Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_restaurant_profile_display);

        //Initialize
        name = (TextView)findViewById(R.id.txtResDisplayName);
        wh = (TextView)findViewById(R.id.txtResDisplayWH);
        adress = (TextView)findViewById(R.id.txtResDisplayAdress);
        phone = (TextView)findViewById(R.id.txtResDisplayPhone);
        description = (TextView)findViewById(R.id.txtResDisplayDescription);
        genre = (TextView)findViewById(R.id.txtResDisplayGenre);
        minPrice = (TextView)findViewById(R.id.txtMinPriceResDisplay);
        maxDuration = (TextView)findViewById(R.id.txtMaxDurationResDisplay);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Restaurants");
        user = mAuth.getCurrentUser();

        //Get the data from firebase and place related data to related text views.
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String nameString = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                final String whString = dataSnapshot.child(user.getUid()).child("workingHours").getValue(String.class);
                final String adressString = dataSnapshot.child(user.getUid()).child("adress").getValue(String.class);
                final String phoneString = dataSnapshot.child(user.getUid()).child("phone").getValue(String.class);
                final String descriptionString = dataSnapshot.child(user.getUid()).child("description").getValue(String.class);
                final String genreString = dataSnapshot.child(user.getUid()).child("genre").getValue(String.class);
                final double ratingString = dataSnapshot.child(user.getUid()).child("rating").getValue(Double.class);
                final int minPriceString = dataSnapshot.child(user.getUid()).child("minPriceToPreOrder").getValue(Integer.class);
                final int maxdurationString = dataSnapshot.child(user.getUid()).child("maxSeatingDuration").getValue(Integer.class);

                name.setText( nameString );
                wh.setText( "Working hours: " + whString );
                adress.setText( "Adress: " + adressString );
                phone.setText( "Phone: " + phoneString );
                description.setText( "Description: " + descriptionString );
                genre.setText( "    Genre: " + genreString );
                minPrice.setText("Minimum amount for customers to pre-order: " + minPriceString + " TL");
                maxDuration.setText("Maximum seating duration (This data will not be seen by customers): " + maxdurationString + " minutes");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
