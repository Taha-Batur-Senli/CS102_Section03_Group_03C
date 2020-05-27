package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/*
This class enables our users to distinguish their profile. If they are a regular customer, they
select 'I'm a customer' button and proceed with customer sign up activity. Else, they proceed with
restaurant sign up activity.
 */
public class SignUpActivity extends AppCompatActivity {
    //Properties
    Button signUpAsRestaurant, signUpAsCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //Initialize
        signUpAsRestaurant =  (Button) findViewById(R.id.signUpAsRestaurant);
        signUpAsCustomer =  (Button) findViewById(R.id.signUpAsCustomer);

        //Methods
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
