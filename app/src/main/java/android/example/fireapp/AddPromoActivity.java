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

    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    /**
     * Adding promotions to the specific restaurant.
     *@date 23.05.2020
     *@author Group 3C
     */

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
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_add_promo);

            //Initialize

            etPromo = (EditText)findViewById(R.id.editTextPromoAdd);
            etPromo.setTextColor(ContextCompat.getColor(this, R.color.white));
            add = (Button)findViewById(R.id.btnAddPromoAdd);
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            mRef = FirebaseDatabase.getInstance().getReference("Promotions");

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
                        //create an id for promo and set it under that id
                        final String uid = mRef.child(user.getUid()).push().getKey();

                        DatabaseReference mRefRes = FirebaseDatabase.getInstance().getReference("Restaurants");
                        mRefRes.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String restaurantsName =  dataSnapshot.child(user.getUid()).child("name").getValue().toString();
                                mRef.child(user.getUid()).child(uid).setValue(new Promotion(promo, restaurantsName,uid));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
            });
        }

        /**
         * if customer presses to back with out taking any action, we delete this activity from activity history
         */
        @Override
        public void onBackPressed() {
            super.onBackPressed();
            finish();
        }
    }
