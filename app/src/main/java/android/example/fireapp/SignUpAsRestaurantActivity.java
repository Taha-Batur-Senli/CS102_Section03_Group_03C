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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpAsRestaurantActivity extends AppCompatActivity{
    EditText etEmail, etPassword, etName, etPhone;
    private FirebaseAuth mAuth;
    // ProgressBar progressBar;
    Button btnRegister;
    Spinner spinner;
    FirebaseDatabase database;
    DatabaseReference mRef;
    // DatabaseReference mRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_as_restaurant);

        etEmail = findViewById(R.id.etEmailSignUpAsRest);
        etPassword = findViewById(R.id.etPasswordSignUpAsRest);
        etName = findViewById(R.id.etNameResSignUp);
        etPhone = findViewById(R.id.etPhoneResSignUp);

        spinner = findViewById(R.id.spinnerGenre);
        mAuth = FirebaseAuth.getInstance();
        btnRegister = findViewById(R.id.btnRegisterSignUpAsRest);
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference( "Restaurants");
        // mRef2 = database.getReference("Best Restaurants");

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

        // progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // progressBar.setVisibility(View.GONE);
                if( task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();

                    //Get user's info
                    String email = user.getEmail();
                    String uid = user.getUid();

                    //Setting Firebase realtime database
                    mRef.child(uid).child("rating").setValue(0);
                    mRef.child(uid).child("numOfTimesRated").setValue(0);
                    mRef.child(uid).child("isRestaurant").setValue(true);
                    mRef.child(uid).child("uid").setValue(uid);
                    mRef.child(uid).child("name").setValue(name);
                    mRef.child(uid).child("genre").setValue(genre);
                    mRef.child(uid).child("phone").setValue(phone);
                    mRef.child(uid).child("description").setValue("");
                    mRef.child(uid).child("reservations").setValue("");
                    mRef.child(uid).child("promotions").setValue("");
                    mRef.child(uid).child("menu").setValue("");
                    mRef.child(uid).child("email").setValue(user.getEmail());
                    mRef.child(uid).child("working hours").setValue("");
                    mRef.child(uid).child("adress").setValue("");
                    mRef.child(uid).child("max seating duration").setValue(maxDuration);
                    mRef.child(uid).child("min price to pre-order").setValue(minPrice);

                    /* mRef2.child(uid).child("rating").setValue(0);
                    mRef2.child(uid).child("numOfTimesRated").setValue(0);
                    mRef2.child(uid).child("isRestaurant").setValue(true);
                    mRef2.child(uid).child("uid").setValue(uid);
                    mRef2.child(uid).child("name").setValue(name);
                    mRef2.child(uid).child("genre").setValue(genre);
                    mRef2.child(uid).child("phone").setValue(phone);
                    mRef2.child(uid).child("description").setValue("");
                    mRef2.child(uid).child("reservations").setValue("");
                    mRef2.child(uid).child("promotions").setValue("");
                    mRef2.child(uid).child("menu").setValue("");
                    mRef2.child(uid).child("email").setValue(user.getEmail());
                    mRef2.child(uid).child("working hours").setValue("");
                    mRef2.child(uid).child("adress").setValue("");
                    mRef2.child(uid).child("max seating duration").setValue(maxDuration);
                    mRef2.child(uid).child("min price to pre-order").setValue(minPrice); */

                    Toast.makeText( SignUpAsRestaurantActivity.this, "Restaurant Created", Toast.LENGTH_SHORT);
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
