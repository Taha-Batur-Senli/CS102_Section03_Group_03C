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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * This class is the class users face for the first time when they run our app. They can
 * login to their account or create a new account if they don't have one.
 *@date 27.05.2020
 *@author Group 3C
 */

public class LogInActivity extends AppCompatActivity {
    //Properties
    Button logIn;
    EditText etEmail, etPassword;
    ProgressBar pb;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Restaurants");
//        ref.setValue(null);
//        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Reservations");
//        ref2.setValue(null);
//        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Customers");
//        ref3.setValue(null);
//        DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference("Promotions");
//        ref4.setValue(null);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);

        //Initialize
        etEmail = findViewById(R.id.etEmailLogIn);
        etPassword = findViewById(R.id.etPasswordLogIn);
        logIn = findViewById(R.id.btnLogInActivity);
        mAuth = FirebaseAuth.getInstance();

        etEmail.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPassword.setTextColor(ContextCompat.getColor(this, R.color.white));
        pb = (ProgressBar)findViewById(R.id.progressBarLogIn);

        //Make login button functional.
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                if (email.isEmpty()){
                    etEmail.setError("Enter an email!");
                    etEmail.requestFocus();
                    return;
                }
                String password = etPassword.getText().toString().trim();
                if (password.isEmpty()){
                    etPassword.setError("Enter a password!");
                    etPassword.requestFocus();
                    return;
                }

                pb.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       pb.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID = user.getUid();
                            Toast.makeText(LogInActivity.this, "Welcome back!", Toast.LENGTH_SHORT).show();

                            //Distinquish User type
                            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Customers");
                            ref.orderByChild("uid").equalTo(userID).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        startActivity(new Intent(LogInActivity.this, RestaurantProfile.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });

                        } else {
                            Toast.makeText(LogInActivity.this, "WRONG USER OR PASSWORD!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Button button = findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openActivity2()
    {
        Intent intent;
        intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        
    }
}
