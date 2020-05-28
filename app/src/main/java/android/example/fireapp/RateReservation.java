package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

/**
 * This is the activity that enables customers to rate a restaurant after having their meal.
 *@date 18.05.2020
 *@author Group 3C
 */
public class RateReservation extends AppCompatActivity {
    // Properties
    TextView tvReserv, tvSetError;
    Button rate;
    EditText etRating;
    FirebaseUser user;

    // Methods

    /**
     * This method is called when a customer clicks on a past reservation
     * It checks whether the customer has rated the reservation before, and if not, makes it possible to rate.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rate_reservation);

        //Initializing variables
        etRating = (EditText)findViewById(R.id.etRate);
        tvReserv = (TextView)findViewById(R.id.reservTextRatePage);
        tvSetError = (TextView)findViewById(R.id.textView42);
        rate = (Button)findViewById(R.id.btnRate);
        user = FirebaseAuth.getInstance().getCurrentUser();
        etRating.setTextColor(ContextCompat.getColor(this, R.color.white));

        //SETTING TV
        Intent i = getIntent();
        String reservText = i.getStringExtra("RESERVTEXT");
        final String resName = i.getStringExtra("RESTNAME");
        tvReserv.setText( reservText);
        int indexOfIDStart = reservText.indexOf("ID:") + 3;
        int indexOfIDEnd  = reservText.indexOf("   ", indexOfIDStart);
        final String rezID = reservText.substring(indexOfIDStart, indexOfIDEnd);

        DatabaseReference reservation = FirebaseDatabase.getInstance().getReference("Reservations").child("PastReservations");
        reservation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String hasRated = (String)dataSnapshot.child(rezID).child("hasRated").getValue().toString();
                if (hasRated.equals("true")){
                    tvSetError.setText("You have rated this reservation before!");
                    etRating.setVisibility(View.INVISIBLE);
                    rate.setVisibility(View.INVISIBLE);
                    rate.setClickable(false);
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Calling methods
        rateAction( rezID);
    }

    // Methods

    /**
     *
     * @param rezID uid of the reservation wanted to be rated
     */
    private void rateAction(final String rezID) {
       rate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               final String ratingString = etRating.getText().toString();

               // The number inside field should be valid
               if (ratingString.isEmpty()){
                   etRating.setError("Cannot be empty!");
                   etRating.requestFocus();
                   return;
               }
               else if ( Integer.parseInt(ratingString) > 5) {
                   etRating.setError("You cannot rate more than five!");
                   etRating.requestFocus();
                   return;
               }

               Intent i = getIntent();
               final String resName = i.getStringExtra("RESTNAME");

               final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Restaurants");
               mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        while (items.hasNext()){
                            DataSnapshot item1 = items.next();
                            if(item1.child("name").getValue().toString().equals(resName))
                            {
                                    double currentRating = Double.parseDouble(item1.child("rating").getValue().toString());
                                    int numOfTimesRated = Integer.parseInt(item1.child("numOfTimesRated").getValue().toString());

                                    // calculating the total rating of the reservation after a customers rate it
                                    double sum = Double.parseDouble(ratingString) + (currentRating * numOfTimesRated);
                                    numOfTimesRated++;
                                    double avg = (double)(sum / numOfTimesRated);

                                    item1.getRef().child("rating").setValue(avg);
                                    item1.getRef().child("numOfTimesRated").setValue(numOfTimesRated);
                            }
                        }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
               DatabaseReference reservation = FirebaseDatabase.getInstance().getReference("Reservations").child("PastReservations");
               reservation.child(rezID).child("hasRated").setValue(true);
               startActivity(new Intent(RateReservation.this, MyReservations.class));
               finish();
           }
       });
    }

    /**
     * The method that gives slightly different function to the back button
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RateReservation.this,MyReservations.class));
        finish();
    }
}
