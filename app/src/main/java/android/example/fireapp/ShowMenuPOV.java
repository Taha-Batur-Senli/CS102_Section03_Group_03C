package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 *This class enables customers to display a restaurant's menu.
 *@date 05.05.2020
 *@author Group_g3C
 */
public class ShowMenuPOV extends AppCompatActivity {

    //Properties

    TextView tvName, tvGenre, tvAddress, tvPhone, tvWH;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button showGallery;
    Intent intent;
    String uid;

    //Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_menu_p_o_v);

        //get the uid of restaurant from previous activity
        intent = getIntent();
        uid = intent.getStringExtra("UID");

        tvName = (TextView)findViewById(R.id.txtNameShowMenuPOV);
        tvGenre = findViewById(R.id.txtGenrePOV2);
        tvAddress = findViewById(R.id.txtAdressPOV2);
        tvPhone = findViewById(R.id.txtPhonePOV2);
        tvWH = findViewById(R.id.txtWorkingHoursPOV2);
        showGallery = findViewById(R.id.button);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference( "Restaurants");


        showGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowMenuPOV.this, ImageRecyclerCustomer.class);
                intent.putExtra("restaurant_id", uid);
                startActivity(intent);
            }
        });

        //methods used
        placeDatatoTVs();

        //Set the name of the restaurant to the related text view.
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String nameof = dataSnapshot.child("name").getValue().toString();
                tvName.setText("Information of " + nameof);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void placeDatatoTVs() {
        Intent intent = getIntent();
        String uid = intent.getStringExtra("UID");
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String resName = dataSnapshot.child("name").getValue(String.class);
                final String resGenre = dataSnapshot.child("genre").getValue(String.class);
                final String resWH = dataSnapshot.child("workingHours").getValue(String.class);
                final String resPhone = dataSnapshot.child("phone").getValue(String.class);
                final String resAdress = dataSnapshot.child("adress").getValue(String.class);

                tvName.setText( "Information of " + resName );
                tvGenre.setText("Genre: " + resGenre );

                if ( !resWH.isEmpty())
                    tvWH.setText("Working hours: " + resWH);
                else
                    tvWH.setText("Working Hours: -");
                if ( !resPhone.isEmpty())
                    tvPhone.setText("Phone Number: +90 " + resPhone);
                else
                    tvPhone.setText("Phone Number: -");
                if ( !resAdress.isEmpty())
                    tvAddress.setText("Address: " + resAdress);
                else
                    tvAddress.setText("Address: -");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
