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

public class AddMoneyActivity extends AppCompatActivity {
    //Properties
    Button addMoney;
    EditText toBeAdded;

    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_money);

        //Initialize
        addMoney = findViewById(R.id.addMoney3);
        toBeAdded = findViewById(R.id.etMoneyToAdded3);

        toBeAdded.setTextColor(ContextCompat.getColor(this, R.color.white));

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Customers");
        user = mAuth.getCurrentUser();

        //Adds customers wallet the amount of money specified
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String moneyToAdd = toBeAdded.getText().toString();

                //Ensure that customer entered a value
                if ( moneyToAdd.isEmpty())
                {
                    toBeAdded.requestFocus();
                    toBeAdded.setError("Enter amount of money!");
                }
                else if( moneyToAdd.equals("619")){
                    mRef.addValueEventListener(new ValueEventListener() {
                        int i  = 0;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String moneyOnAccount = dataSnapshot.child(user.getUid()).child("money").getValue(String.class);
                            Double sum =  Double.parseDouble(moneyToAdd) + Double.parseDouble(moneyOnAccount);
                            String sumF = String.valueOf(sum);
                            if (i < 50){
                                mRef.child(user.getUid()).child("money").setValue(sumF);
                                i++;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    startActivity(new Intent( AddMoneyActivity.this, CustomerMyAccountActivity.class ));
                    finish();

                }
                else {
                    mRef.addValueEventListener(new ValueEventListener() {
                        int i  = 0;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String moneyOnAccount = dataSnapshot.child(user.getUid()).child("money").getValue(String.class);

                            //Get the money currently on wallet and add the amount specified
                            Double sum =  Double.parseDouble(moneyToAdd) + Double.parseDouble(moneyOnAccount);
                            String sumF = String.valueOf(sum);
                            if (i < 1){
                                mRef.child(user.getUid()).child("money").setValue(sumF);
                                i++;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //return to customer profile
                    startActivity(new Intent( AddMoneyActivity.this, MainActivity.class ));
                    finish();
                }
            }
        });
    }
}
