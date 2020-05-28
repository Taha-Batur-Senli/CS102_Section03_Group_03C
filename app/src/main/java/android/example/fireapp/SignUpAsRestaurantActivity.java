package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 *This class enables restaurant owners to sign up to our app. Their info are stored both on
 *authentic firebase and realtime database for different purposes.
 *@date 30.04.2020
 *@author Group_g3C
 */
public class SignUpAsRestaurantActivity extends AppCompatActivity{

    //Properties

    EditText etEmail, etPassword, etName, etPhone;
    Button next;
    private FirebaseAuth mAuth;

    FirebaseDatabase database;
    DatabaseReference mRef;
    DatabaseReference mRefSeatPlans;
    DatabaseReference mRef2;

    //Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_as_restaurant);

        //Initialize
        etEmail = findViewById(R.id.etEmailSignUpAsRest);
        etPassword = findViewById(R.id.etPasswordSignUpAsRest);
        etName = findViewById(R.id.etNameResSignUp);
        etPhone = findViewById(R.id.etPhoneResSignUp);
        next = (Button)findViewById(R.id.btnNextSignUpAsRest);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference( "Restaurants");
        mRefSeatPlans = database.getReference("SeatPlans");
        mRef2 = database.getReference("Best Restaurants");

        etEmail.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPassword.setTextColor(ContextCompat.getColor(this, R.color.white));
        etName.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPhone.setTextColor(ContextCompat.getColor(this, R.color.white));

        //OnClickListeners

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                final String name = etName.getText().toString();
                final String phone = etPhone.getText().toString();

                if( name.isEmpty()){
                    etName.setError("Enter a name!");
                    etName.requestFocus();
                    return;
                }

                if( email.isEmpty()){
                    etEmail.setError("Enter an email!");
                    etEmail.requestFocus();
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

                Intent i = new Intent(SignUpAsRestaurantActivity.this, SignUpAsRestaurantP2.class);
                i.putExtra("NAME", name);
                i.putExtra("EMAIL", email);
                i.putExtra("PASSWORD", password);
                i.putExtra("PHONE", phone);
                startActivity(i);
            }
        });

    }
}
