package android.example.fireapp;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/*
This class enables restaurants to edit their data ny updating them on firebase.
 */
public class RestaurantEditProfileActivity extends AppCompatActivity{
    //Properties
    Button save, upload, changeGenre;
    EditText etDescription, etPhone, etName, etWH,etAdress, etMaxDuration, etMinPrice;
    Spinner genre;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_restaurant_edit_profile);

        //Initialize properties
        upload = (Button)findViewById(R.id.upload_image);
        save = (Button)findViewById(R.id.btnSaveEditProfileRes);
        etDescription = (EditText)findViewById(R.id.etResProfileDescription);
        etPhone = (EditText)findViewById(R.id.etResProfilePhone);
        etName = (EditText)findViewById(R.id.etResProfileName);
        etWH = (EditText)findViewById(R.id.etResProfileWH);
        etAdress = (EditText)findViewById(R.id.etResProfileAdress);
        etMaxDuration = (EditText)findViewById(R.id.etMaxDurationEdit);
        etMinPrice = (EditText)findViewById(R.id.etMinPriceEdit);
        genre = (Spinner)findViewById(R.id.spinnerEdit);
        changeGenre = findViewById(R.id.genre_button);

        etDescription.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPhone.setTextColor(ContextCompat.getColor(this, R.color.white));
        etName.setTextColor(ContextCompat.getColor(this, R.color.white));
        etWH.setTextColor(ContextCompat.getColor(this, R.color.white));
        etAdress.setTextColor(ContextCompat.getColor(this, R.color.white));
        etMaxDuration.setTextColor(ContextCompat.getColor(this, R.color.white));
        etMinPrice.setTextColor(ContextCompat.getColor(this, R.color.white));

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Restaurants");
        user = mAuth.getCurrentUser();

        //setting onClickListener update button
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantEditProfileActivity.this, UploadPicture.class);
                startActivity(intent);
            }
        });

        //onClickListener for genre button
        changeGenre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity( new Intent(RestaurantEditProfileActivity.this, Genres.class));
            }

        });

        //Save button  function to update firebase database
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res;
                final String nameUpdate = etName.getText().toString();
                final String descriptionUpdate = etDescription.getText().toString();
                final String phoneUpdate = etPhone.getText().toString();
                final String whUpdate = etWH.getText().toString();
                final String adressUpdate = etAdress.getText().toString();
                final String genreUpdate = genre.getSelectedItem().toString();
                final String minPriceUpdate = etMinPrice.getText().toString();
                final String maxDurationUpdate = etMaxDuration.getText().toString();
                res = false;

                //Only the fields that are not empty will be updated. This prevents a bug.
                if (!maxDurationUpdate.isEmpty()) {
                    mRef.child(user.getUid()).child("maxSeatingDuration").setValue(Integer.parseInt(maxDurationUpdate));
                    res = true;
                }
                if (!minPriceUpdate.isEmpty()) {
                    mRef.child(user.getUid()).child("minPriceToPreOrder").setValue(Integer.parseInt(minPriceUpdate));
                    res = true;
                }
                if(!nameUpdate.isEmpty()) {
                    mRef.child(user.getUid()).child("name").setValue(nameUpdate);
                    res = true;
                }
                if(!descriptionUpdate.isEmpty()) {
                    mRef.child(user.getUid()).child("description").setValue(descriptionUpdate);
                    res = true;
                }
                if(!phoneUpdate.isEmpty()) {
                    mRef.child(user.getUid()).child("phone").setValue(phoneUpdate);
                    res = true;
                }
                if(!whUpdate.isEmpty()) {
                    mRef.child(user.getUid()).child("workingHours").setValue(whUpdate);
                    res = true;
                }
                if(!adressUpdate.isEmpty()) {
                    mRef.child(user.getUid()).child("adress").setValue(adressUpdate);
                    res = true;
                }
                //TODO bug should be fixed
                if(!genreUpdate.isEmpty()) {
                    mRef.child(user.getUid()).child("genre").setValue(genreUpdate);
                }

                if( res ) {
                    Toast.makeText(RestaurantEditProfileActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                }
                startActivity( new Intent(RestaurantEditProfileActivity.this, RestaurantProfile.class));
                finish();
            }
        });
    }
}
