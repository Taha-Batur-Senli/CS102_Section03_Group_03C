package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//A class that functions no more? To be deleted later on!

public class CustHelpActivity extends AppCompatActivity {
    Button how_to_use_app, how_to_make_reservations, how_to_pre_order,FAQ;
    ImageView logo, line;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cust_help);
        how_to_make_reservations = findViewById(R.id.how_to_make_reservation);
        how_to_use_app = findViewById(R.id.how_to_use_app);
        how_to_pre_order =findViewById(R.id.how_to_pre_order);
        FAQ = findViewById(R.id.FAQ);
        logo = findViewById(R.id.imageView12);
        line = findViewById(R.id.imageView13);
        txt = findViewById(R.id.textView4);

        how_to_use_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustHelpActivity.this,HowToUseApp.class);
                startActivity(intent);
            }
        });

        how_to_make_reservations.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustHelpActivity.this, HowToMakeReservation.class);
                startActivity(intent);
            }
        });

    }
}
