package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 *
 *@date 27.05.2020
 *@author Group 3C
 */

public class CustomerMyAccountActivity extends AppCompatActivity {
    //Properties
    TextView name, phone, points, money, email, ranking;
    Button addMoney, editAccount;

    Dialog addMoneyPopUp;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_my_account);

        //Initialize
        phone = (TextView)findViewById(R.id.txtCustomerAccountPhone);
        ranking = (TextView)findViewById(R.id.txtCustomerAccountRanking);
        name = (TextView)findViewById(R.id.txtCustomerAccountName);
        points = (TextView)findViewById(R.id.txtCustomerAccountPoints);
        money = (TextView)findViewById(R.id.txtCustomerAccountMoney);
        email = (TextView)findViewById(R.id.txtCustomerAccountEmail);
        addMoney = (Button)findViewById(R.id.btnAddMoney);
        editAccount = (Button)findViewById(R.id.btnEditProfileCustomer);
        addMoneyPopUp = new Dialog(this);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Customers");
        user = mAuth.getCurrentUser();

        //Methods called
        addMoneyAction();
        editAccountAction();


        //Get data from database and place them to text views
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String nameString = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                final String moneyString = dataSnapshot.child(user.getUid()).child("money").getValue(String.class);
                final String pointsString = dataSnapshot.child(user.getUid()).child("points").getValue(String.class);
                final String phoneString = dataSnapshot.child(user.getUid()).child("phone").getValue(String.class);
                final String rankingString = dataSnapshot.child(user.getUid()).child("ranking").getValue(String.class);
                final String emailString = user.getEmail();

                name.setText( "Name: " + nameString );
                money.setText( "My Money: " + moneyString + " g3Coins" );
                points.setText(  "My Reservation Points: " + pointsString );
                phone.setText( "GSM: " + phoneString);
                email.setText("Email: " + emailString);
                ranking.setText("Your Ranking: " + rankingString);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Methods
    private void addMoneyAction(){
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerMyAccountActivity.this, AddMoneyActivity.class));
            }
        });

    }

    private void editAccountAction() {
        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerMyAccountActivity.this, EditCustomerAccountActivity.class));

            }
        });
    }
}
