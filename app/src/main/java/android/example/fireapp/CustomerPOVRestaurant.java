package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerPOVRestaurant extends AppCompatActivity {
    TextView tvName, tvRating, tvDescription, tvGenre, tvWorkingHours, tvMinPriceToPreOrder, tvPhone, tvAdress;
    DatabaseReference mRefRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_p_o_v_restaurant);

        tvName = (TextView)findViewById(R.id.txtNamePOV);
        tvRating = (TextView)findViewById(R.id.txtRatingPOV);
        tvDescription = (TextView)findViewById(R.id.txtDescriptionPOV);
        tvGenre = (TextView)findViewById(R.id.txtGenrePOV);
        tvWorkingHours = (TextView)findViewById(R.id.txtWorkingHoursPOV);
        tvMinPriceToPreOrder = (TextView)findViewById(R.id.txtMinPricePreOrderPOV);
        tvPhone = (TextView)findViewById(R.id.txtPhonePOV);
        tvAdress = (TextView)findViewById(R.id.txtAdressPOV);

        mRefRes = FirebaseDatabase.getInstance().getReference("Restaurants");

        placeDatatoTVs();
    }

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
                final String resWH = dataSnapshot.child("working hours").getValue(String.class);
                final double resMinPrice = dataSnapshot.child("min price to pre-order").getValue(Double.class);
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
}
