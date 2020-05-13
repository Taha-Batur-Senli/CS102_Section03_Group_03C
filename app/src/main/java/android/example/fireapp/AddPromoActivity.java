package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPromoActivity extends AppCompatActivity {
    //Properties
    EditText etPromo;
    Button add;

    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promo);

        //Initialize
        etPromo = (EditText)findViewById(R.id.editTextPromoAdd);
        add = (Button)findViewById(R.id.btnAddPromoAdd);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("Promotions");

        etPromo.setTextColor(ContextCompat.getColor(this, R.color.white));

        //Adds promotion to firebase
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String promo = etPromo.getText().toString();

                //Ensures that promotion is not empty
                if(promo.isEmpty()){
                    etPromo.setError("Enter your promotion!");
                    etPromo.requestFocus();
                } else {
                    //Adds promotion to firebase and returns to restaurants profile
                    final String uid = mRef.child(user.getUid()).push().getKey();

                    DatabaseReference mRefRes = FirebaseDatabase.getInstance().getReference("Restaurants");
                    mRefRes.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String restaurantsName =  dataSnapshot.child(user.getUid()).child("name").getValue().toString();
                            mRef.child(user.getUid()).child(uid).setValue(new Promotion(promo, restaurantsName,uid));
                            startActivity(new Intent(AddPromoActivity.this, RestaurantProfile.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }
}
