package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LogInActivity extends AppCompatActivity {
    Button logIn;
    EditText etEmail, etPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail = (EditText)findViewById(R.id.etEmailLogIn);
        etPassword = (EditText)findViewById(R.id.etPasswordLogIn);
        logIn = (Button)findViewById(R.id.btnLogInActivity);
        mAuth = FirebaseAuth.getInstance();

        etEmail.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPassword.setTextColor(ContextCompat.getColor(this, R.color.white));

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID = user.getUid();
                            Toast.makeText(LogInActivity.this, "LOGGED IN", Toast.LENGTH_SHORT).show();

                            //Distinquish User type
                            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Customers");
                            ref.orderByChild("uid").equalTo(userID).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        startActivity(new Intent(LogInActivity.this, CustomerProfile.class));
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
                            Toast.makeText(LogInActivity.this, "WRONG USER OR PASSWORD !!!", Toast.LENGTH_SHORT).show();

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
}
