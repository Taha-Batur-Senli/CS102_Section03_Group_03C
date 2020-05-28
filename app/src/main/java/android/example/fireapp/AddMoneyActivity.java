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
    import android.widget.Toast;

    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    /**
     * Adding Money Activity
     *@date 25.04.2020
     *@author Group_g3C
     */

    public class AddMoneyActivity extends AppCompatActivity {

        //Properties

        Button addMoney;
        EditText toBeAdded;
        TextView moneyTextView;

        DatabaseReference mRef;
        FirebaseAuth mAuth;
        FirebaseUser user;
        Customer customer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_add_money);

            //Initialize

            addMoney = findViewById(R.id.addMoney3);
            toBeAdded = findViewById(R.id.etMoneyToAdded3);
            moneyTextView = findViewById(R.id.moneyTextView);
            toBeAdded.setTextColor(ContextCompat.getColor(this, R.color.white));
            mAuth = FirebaseAuth.getInstance();
            mRef = FirebaseDatabase.getInstance().getReference("Customers");
            user = mAuth.getCurrentUser();

            //ValueEventListener to retrieve the current money and illustrate it.
        assert user != null;
        mRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customer = dataSnapshot.getValue(Customer.class);
                assert customer != null;
                String money = customer.getMoney();
                moneyTextView.setText("You currently have \n" + money + " g3Coins in your account.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


            /**
             * Adds customers wallet the amount of money specified.
             */
            addMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String moneyToAdd = toBeAdded.getText().toString();

                Intent intent = getIntent();
                String from = intent.getStringExtra("FROM");

                //Ensure that customer entered a value
                if ( moneyToAdd.isEmpty())
                {
                    toBeAdded.requestFocus();
                    toBeAdded.setError("Enter amount of money!");
                }
                else if ( moneyToAdd.equals("619")){ //easter egg :)
                    mRef.child(user.getUid()).child("money").setValue("500000");
                }
                else {
                    //gets the current money, adds the amount wished to be added, updates the firebase
                    double currentMoney = Double.parseDouble(customer.getMoney());
                    double moneyFinal = currentMoney + Double.parseDouble(moneyToAdd);
                    mRef.child(user.getUid()).child("money").setValue("" + moneyFinal);
                }

                if( from.equals("CustomerMyAccountActivity")){
                    Intent i = new Intent(AddMoneyActivity.this, CustomerMyAccountActivity.class);
                    Toast.makeText(getApplicationContext(), "Money has been added", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    finish();
                }
                else if( from.equals("PreOrderActivity")){
                    Toast.makeText(getApplicationContext(), "Money has been added", Toast.LENGTH_SHORT).show();
                    onBackPressed();
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
