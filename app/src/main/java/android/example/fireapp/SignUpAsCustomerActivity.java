package android.example.fireapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpAsCustomerActivity extends AppCompatActivity {
    Button signUp;
    EditText etEmail, etPassword, etName, etPhone;
    private FirebaseAuth mAuth;
    ProgressBar pb;
    FirebaseDatabase database;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_customer);

        signUp = findViewById(R.id.btnRegisterCustomer);
        etEmail = findViewById(R.id.etEmailCustomer);
        etPassword = findViewById(R.id.etPasswordCustomer);
        etName = findViewById(R.id.etNameCustomer);
        etPhone = findViewById(R.id.etPhoneCustomer);
        mAuth = FirebaseAuth.getInstance();
        // pb = (ProgressBar)findViewById(R.id.progressBarCustomerSignUp);
        database = FirebaseDatabase.getInstance();
        mRef  = database.getReference( "Customers");

        etEmail.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPassword.setTextColor(ContextCompat.getColor(this, R.color.white));
        etName.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPhone.setTextColor(ContextCompat.getColor(this, R.color.white));

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCustomer();
            }
        });
    }

    private void registerCustomer() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        final String name = etName.getText().toString();
        final String phone = etPhone.getText().toString();

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

        //pb.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
          //      pb.setVisibility(View.GONE);
                if( task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();

                    //Get user's info
                    String email = user.getEmail();
                    String uid = user.getUid();

                    //add data to database
                    mRef.child(uid).child("isRestaurant").setValue(false);
                    mRef.child(uid).child("uid").setValue(uid);
                    mRef.child(uid).child("email").setValue(email);
                    mRef.child(uid).child("reservations").setValue("");
                    mRef.child(uid).child("notifications").setValue("");
                    mRef.child(uid).child("name").setValue(name);
                    mRef.child(uid).child("money").setValue("0");
                    mRef.child(uid).child("points").setValue("0");
                    mRef.child(uid).child("fav restaurants").setValue("");
                    mRef.child(uid).child("phone").setValue(phone);
                    mRef.child(uid).child("ranking").setValue("0");

                    Toast.makeText( SignUpAsCustomerActivity.this, "Restaurant Created", Toast.LENGTH_SHORT).show();
                    startActivity( new Intent(SignUpAsCustomerActivity.this, CustomerProfile.class));
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
