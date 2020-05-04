package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.util.HashMap;

public class SignUpAsRestaurantActivity extends AppCompatActivity{
    EditText etEmail, etPassword, etName, etPhone;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button btnRegister;
    Spinner spinner;
    FirebaseDatabase database;
    DatabaseReference mRef, mRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_restaurant);

        etEmail = (EditText) findViewById(R.id.etEmailSignUpAsRest);
        etPassword = (EditText)findViewById(R.id.etPasswordSignUpAsRest);
        etName = (EditText)findViewById(R.id.etNameResSignUp);
        etPhone = (EditText)findViewById(R.id.etPasswordSignUpAsRest);
        spinner = (Spinner)findViewById(R.id.spinnerGenre);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnRegister = (Button)findViewById(R.id.btnRegisterSignUpAsRest);
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference( "Restaurants");
        mRef2 = database.getReference("Best Restaurants");

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

                    Toast.makeText( SignUpAsRestaurantActivity.this, "Restaurant Created", Toast.LENGTH_SHORT).show();
                    startActivity( new Intent(SignUpAsRestaurantActivity.this, RestaurantProfile.class));
                    finish();
                } else{
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
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
