package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class AddMoneyActivity extends AppCompatActivity {
    Button addMoney;
    EditText toBeAdded;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser user;

    //TODO ÇALIŞMIYOR! sonsuza kadar para ekeme bug ı var
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        addMoney = (Button)findViewById(R.id.addMoney3);
        toBeAdded = (EditText) findViewById(R.id.etMoneyToAdded3);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Customers");
        user = mAuth.getCurrentUser();




        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final String moneyToAdd = toBeAdded.getText().toString();


                mRef.addValueEventListener(new ValueEventListener() {
                   String sumF;
                    Double sum;
                    int i = 0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String moneyToAdd = toBeAdded.getText().toString();

                            final String moneyOnAccount = dataSnapshot.child(user.getUid()).child("money").getValue(String.class);
                             sum = Double.parseDouble(moneyToAdd) + Double.parseDouble(moneyOnAccount);
                             sumF = String.valueOf(sum);
                             if( i<1 ) {
                                 mRef.child(user.getUid()).child("money").setValue(sumF);
                                 i++;
                             }
                            startActivity(new Intent(AddMoneyActivity.this, CustomerMyAccountActivity.class));





                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(new Intent(AddMoneyActivity.this, CustomerMyAccountActivity.class));

                //startActivity(new Intent( AddMoneyActivity.this, CustomerMyAccountActivity.class ));
                finish();
            }
        });
    }
}
