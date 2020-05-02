package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {
    Button signUpAsRestaurant, signUpAsCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpAsRestaurant =  (Button) findViewById(R.id.signUpAsRestaurant);
        signUpAsCustomer =  (Button) findViewById(R.id.signUpAsCustomer);

        signUpAsRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //directs to restaurant sign up activity
                startActivity(new Intent(SignUpActivity.this, SignUpAsRestaurantActivity.class));
            }
        });

       signUpAsCustomer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity( new Intent( SignUpActivity.this, SignUpAsCustomerActivity.class));
           }
       });
    }
}
