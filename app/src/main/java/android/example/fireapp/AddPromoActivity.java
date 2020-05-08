package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPromoActivity extends AppCompatActivity {
    EditText etPromo;
    Button add;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promo);

        etPromo = (EditText)findViewById(R.id.editTextPromoAdd);
        add = (Button)findViewById(R.id.btnAddPromoAdd);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("Promotions");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String promo = etPromo.getText().toString();

                if(promo.isEmpty()){
                    etPromo.setError("Enter your promotion!");
                    etPromo.requestFocus();
                } else{
                    String uid = mRef.child(user.getUid()).push().getKey();

                    mRef.child(user.getUid()).child(uid).setValue(new Promotion(promo, "restaurantsName",uid));
                    startActivity(new Intent(AddPromoActivity.this, RestaurantProfile.class));
                    finish();
                }
            }
        });
    }
}
