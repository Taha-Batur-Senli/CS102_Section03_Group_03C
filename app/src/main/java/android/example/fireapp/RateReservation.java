package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class RateReservation extends AppCompatActivity {
    //Properties
    TextView tvReserv;
    Button rate;
    EditText etRating;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_reservation);

        //Initialize
        etRating = (EditText)findViewById(R.id.etRate);
        tvReserv = (TextView)findViewById(R.id.reservTextRatePage);
        rate = (Button)findViewById(R.id.btnRate);
        user = FirebaseAuth.getInstance().getCurrentUser();

        //SET TV
        Intent i = getIntent();
        String reservText = i.getStringExtra("RESERVTEXT");
        final String resName = i.getStringExtra("RESTNAME");
        //final String seat = i.getStringExtra("SEAT");
        //final String date = i.getStringExtra("DATE");
        //final String timeSlot = i.getStringExtra("TIMESLOT");
        tvReserv.setText( reservText);

        //TODO find the reservation id
        /*DatabaseReference mRez = FirebaseDatabase.getInstance().getReference("Reservations").child("PastReservations");
        mRez.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()){
                    DataSnapshot item1 = items.next();
                    if (item1.child("restaurantName").getValue().toString().equals(resName)
                    && item1.child("cusID").getValue().toString().equals(user.getUid())
                    && item1.child("date").toString().equals(date)
                    && item1.child("seat").toString().equals(seat)
                    && item1.child("timeSlot").toString().equals(timeSlot)){
                        if (item1.child("hasReserved").toString().equals("true")){
                            etRating.setError("You have already rated this reservation!");
                            etRating.requestFocus();
                            return;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        //Methods called
        rateAction();
    }

    //METHODS
    private void rateAction() {
       rate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final String ratingString = etRating.getText().toString();

               //TODO if(reservastonun hasRated'i true ise error ver you have rated this rez already)
               if (ratingString.isEmpty()){
                   etRating.setError("Cannot be empty!");
                   etRating.requestFocus();
                   return;
               } else if ( Integer.parseInt(ratingString) > 5) {
                   etRating.setError("You cannot rate more than five!");
                   etRating.requestFocus();
                   return;
               }
               Intent i = getIntent();
               final String resName = i.getStringExtra("RESTNAME");

               final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Restaurants");
               mRef.addValueEventListener(new ValueEventListener() {
                   int i  = 0;
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        while (items.hasNext()){
                            DataSnapshot item1 = items.next();
                            if(item1.child("name").getValue().toString().equals(resName))
                            {
                                if (i < 1) {
                                    double currentRating = Double.parseDouble(item1.child("rating").getValue().toString());
                                    int numOfTimesRated = Integer.parseInt(item1.child("numOfTimesRated").getValue().toString());
                                    double sum = Double.parseDouble(ratingString) + (currentRating * numOfTimesRated);
                                    numOfTimesRated++;
                                    double avg = (double)(sum / numOfTimesRated);
                                    item1.getRef().child("rating").setValue(avg);
                                    item1.getRef().child("numOfTimesRated").setValue(numOfTimesRated);
                                    //mRef.child(resName).child("numOfTimesRated").setValue(String.valueOf(numOfTimesRated));
                                    //mRef.child(resName).child("rating").setValue(String.valueOf(avg));
                                    //dataSnapshot.getRef().child("numOfTimesRated").setValue(numOfTimesRated);
                                    //dataSnapshot.getRef().child("rating").setValue(avg);
                                    i++;

                                }
                            }
                        }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
               //TODO reservasyonun hasRatedini false yap
               startActivity(new Intent(RateReservation.this, MyReservations.class));
               finish();

           }
       });

    }
}
