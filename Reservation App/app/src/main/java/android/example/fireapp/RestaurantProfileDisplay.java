package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestaurantProfileDisplay extends AppCompatActivity {
    TextView name, wh, adress, phone, description, genre, rating;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile_display);

        name = (TextView)findViewById(R.id.txtResDisplayName);
        wh = (TextView)findViewById(R.id.txtResDisplayWH);
        adress = (TextView)findViewById(R.id.txtResDisplayAdress);
        phone = (TextView)findViewById(R.id.txtResDisplayPhone);
        description = (TextView)findViewById(R.id.txtResDisplayDescription);
        genre = (TextView)findViewById(R.id.txtResDisplayGenre);
        rating = (TextView)findViewById(R.id.txtRating);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Restaurants");
        user = mAuth.getCurrentUser();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String nameString = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                final String whString = dataSnapshot.child(user.getUid()).child("working hours").getValue(String.class);
                final String adressString = dataSnapshot.child(user.getUid()).child("adress").getValue(String.class);
                final String phoneString = dataSnapshot.child(user.getUid()).child("phone").getValue(String.class);
                final String descriptionString = dataSnapshot.child(user.getUid()).child("description").getValue(String.class);
                final String genreString = dataSnapshot.child(user.getUid()).child("genre").getValue(String.class);
                final int ratingString = dataSnapshot.child(user.getUid()).child("rating").getValue(Integer.class);

                name.setText( nameString );
                wh.setText( "Working hours: " + whString );
                adress.setText( "Adress: " + adressString );
                phone.setText( "Phone: " + phoneString );
                description.setText( "Description: " + descriptionString );
                genre.setText( "    Genre: " + genreString );
                rating.setText(ratingString + "/5");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
