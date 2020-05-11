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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;


public class SignUpAsRestaurantActivity extends AppCompatActivity{
    EditText etEmail, etPassword, etName, etPhone, etNumOfTables;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button btnRegister;
    Spinner spinner;
    FirebaseDatabase database;
    DatabaseReference mRef;
    DatabaseReference mRefSeatPlans;
    DatabaseReference mRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_as_restaurant);

        etEmail = findViewById(R.id.etEmailSignUpAsRest);
        etPassword = findViewById(R.id.etPasswordSignUpAsRest);
        etName = findViewById(R.id.etNameResSignUp);
        etPhone = findViewById(R.id.etPhoneResSignUp);
        etNumOfTables = findViewById(R.id.etNumOfTablesSignUp);
        progressBar = (ProgressBar)findViewById(R.id.progressBarResSignUp);

        spinner = findViewById(R.id.spinnerGenre);
        mAuth = FirebaseAuth.getInstance();
        btnRegister = findViewById(R.id.btnRegisterSignUpAsRest);
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference( "Restaurants");
        mRefSeatPlans = database.getReference("SeatPlans");
        mRef2 = database.getReference("Best Restaurants");

        etEmail.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPassword.setTextColor(ContextCompat.getColor(this, R.color.white));
        etName.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPhone.setTextColor(ContextCompat.getColor(this, R.color.white));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRestaurant();
            }
        });

    }

    private void registerRestaurant() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        final String name = etName.getText().toString();
        final String phone = etPhone.getText().toString();
        final String genre = spinner.getSelectedItem().toString();
        final String numOfTables = etNumOfTables.getText().toString();

        final int minPrice = 25;
        final int maxDuration = 90;
        /*
        if( minPrice.isEmpty()){
            etMinPrice.setError("You have to specify a lower limit for customers to pre-order from your restaurant! This choice can be changed later on.");
            etMinPrice.requestFocus();
            return;
        }

        if( maxDuration.isEmpty()){
            etMaxDuration.setError("You need to enter an average seating duration for your customers! This choice can be changed later on.");
            etMaxDuration.requestFocus();
            return;
        } */

        if (numOfTables.isEmpty()){
            etNumOfTables.setError("Enter numbers of tables you have");
            etNumOfTables.requestFocus();
            return;
        }

        //if (Integer.parseInt(numOfTables) > 10){
            //etNumOfTables.setError("You cannot have more than 10 tables");
            //etNumOfTables.requestFocus();
          //  return;
        //}

        if( email.isEmpty()){
            etEmail.setError("Enter an email!");
            etEmail.requestFocus();
            return;
        }

        if( name.isEmpty()){
            etName.setError("Enter a name!");
            etName.requestFocus();
            return;
        }

        if( phone.isEmpty()){
            etPhone.setError("Enter a phone!");
            etPhone.requestFocus();
            return;
        }

        if( password.isEmpty()){
            etPassword.setError("Enter a password!");
            etPassword.requestFocus();
            return;
        }

        if ( password.length() < 6){
            etPassword.setError("Password must be at least 6 characters!");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if( task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();

                    //Get user's info
                    String email = user.getEmail();
                    String uid = user.getUid();

                    //Setting Firebase realtime database
                    mRef.child(uid).setValue(new Restaurant(name, email, genre, phone, uid));
                    //mRef2.child(uid).setValue(new Restaurant(name, email, genre, phone, uid));

                    //add num of seats
                    HashMap<String, HashMap<String, HashMap>> seats = new HashMap();
                    String day = LocalDate.now().toString();
                    String seat = "";
                    HashMap <String, HashMap> days;
                    for (int i = 0; i < Integer.parseInt(numOfTables); i++){

                        seat = "seat" + (i + 1);
                        //add 7 days

                        days  = new HashMap();
                        for (int k = 0; k < 7; k++){

                            if (k > 0)
                            {
                                day = LocalDate.now().plusDays(k).toString();
                            }

                            System.out.println((k + 1) + ". day : " + day);
                            //add timeslots
                            SeatCalendar sc = new SeatCalendar(LocalDate.parse(day),
                                    LocalTime.of(0,5),LocalTime.of(23,0));
                            days.put(day, sc);
                        }
                        seats.put(seat, days);
                    }
                    mRefSeatPlans.child(uid).setValue(seats);
                    //add num of seats
                    /*HashMap<String, HashMap<String, HashMap>> seats = new HashMap();

                    for (int i = 0; i < Integer.parseInt(numOfTables); i++){
                        String seat = "seat" + (i + 1);

                        //add 7 days
                        HashMap <String, HashMap> days = new HashMap();
                        for (int k = 0; k < 7; k++){

                            String day = LocalDate.now().toString();
                            if (k > 0)
                               day = LocalDate.now().plusDays(k).toString();
                            //add timeslots
                            SeatCalendar sc = new SeatCalendar(LocalDate.parse(day),
                                    LocalTime.of(8,0),LocalTime.of(23,0));
                            days.put(day, sc);
                        }
                        seats.put(seat, days);
                    }
                    mRefSeatPlans.child(uid).setValue(seats);*/


                    Toast.makeText( SignUpAsRestaurantActivity.this, "Welcome " + name, Toast.LENGTH_SHORT);
                    startActivity( new Intent(SignUpAsRestaurantActivity.this, RestaurantProfile.class));
                    finish();
                }
                else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "This email has an account!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
