package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
 *
 *@date 27.05.2020
 *@author Group 3C
 */

public class HelpRestaurant extends AppCompatActivity {
    Button FAQ;
    Button how_to_use_app_owner;
    Button how_to_make_listing;
    Button how_to_discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_help_restaurant);
        FAQ = findViewById(R.id.FAQ);
        how_to_use_app_owner = findViewById(R.id.how_to_use_app_owner);
        how_to_make_listing = findViewById(R.id.how_to_make_listing);
        how_to_discount = findViewById(R.id.how_to_discount);

        how_to_use_app_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpRestaurant.this,HowToUseAppOwner.class);
                startActivity(intent);
            }
        });

        how_to_make_listing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpRestaurant.this,HowToMakeListing.class);
                startActivity(intent);
            }
        });

        how_to_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpRestaurant.this,HowToDiscount.class);
                startActivity(intent);
            }
        });

        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpRestaurant.this,FAQ.class);
                startActivity(intent);
            }
        });
    }
}
