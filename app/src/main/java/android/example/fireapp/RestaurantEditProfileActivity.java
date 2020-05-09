package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestaurantEditProfileActivity extends AppCompatActivity {
    //Properties
    Button save;
    EditText etDescription, etPhone, etName, etWH,etAdress, etMaxDuration, etMinPrice;
    Spinner genre;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_edit_profile);

        //Initialize properties
        save = (Button)findViewById(R.id.btnSaveEditProfileRes);
        etDescription = (EditText)findViewById(R.id.etResProfileDescription);
        etPhone = (EditText)findViewById(R.id.etResProfilePhone);
        etName = (EditText)findViewById(R.id.etResProfileName);
        etWH = (EditText)findViewById(R.id.etResProfileWH);
        etAdress = (EditText)findViewById(R.id.etResProfileAdress);
        etMaxDuration = (EditText)findViewById(R.id.etMaxDurationEdit);
        etMinPrice = (EditText)findViewById(R.id.etMinPriceEdit);
        genre = (Spinner)findViewById(R.id.spinnerEdit);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Restaurants");
        user = mAuth.getCurrentUser();

        //Save button  function to update firebase database
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameUpdate = etName.getText().toString();
                final String descriptionUpdate = etDescription.getText().toString();
                final String phoneUpdate = etPhone.getText().toString();
                final String whUpdate= etWH.getText().toString();
                final String adressUpdate = etAdress.getText().toString();
                final String genreUpdate = genre.getSelectedItem().toString();
                final String minPriceUpdate = etMinPrice.getText().toString();
                final String maxDurationUpdate = etMaxDuration.getText().toString();

                if(!maxDurationUpdate.isEmpty())
                    mRef.child(user.getUid()).child("maxSeatingDuration").setValue(Integer.parseInt(maxDurationUpdate));
                    // mRef.child(user.getUid()).child("max seating duration").setValue(Integer.parseInt(maxDurationUpdate));
                if(!minPriceUpdate.isEmpty())
                    mRef.child(user.getUid()).child("minPriceToPreOrder").setValue(Integer.parseInt(minPriceUpdate));
                    //mRef.child(user.getUid()).child("min price to pre-order").setValue(Integer.parseInt(minPriceUpdate));
                if(!nameUpdate.isEmpty())
                    mRef.child(user.getUid()).child("name").setValue(nameUpdate);
                if(!descriptionUpdate.isEmpty())
                    mRef.child(user.getUid()).child("description").setValue(descriptionUpdate);
                if(!phoneUpdate.isEmpty())
                    mRef.child(user.getUid()).child("phone").setValue(phoneUpdate);
                if(!whUpdate.isEmpty())
                    mRef.child(user.getUid()).child("workingHours").setValue(whUpdate);
                    //mRef.child(user.getUid()).child("working hours").setValue(whUpdate);
                if(!adressUpdate.isEmpty())
                    mRef.child(user.getUid()).child("adress").setValue(adressUpdate);
                //TODO bug düzelt
                if(!genreUpdate.isEmpty())
                    mRef.child(user.getUid()).child("genre").setValue(genreUpdate);

                startActivity( new Intent(RestaurantEditProfileActivity.this, RestaurantProfile.class));
                finish();
            }
        });
    }
}
