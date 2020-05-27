package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Lists all the restaurants with the selected genre from the main menu.
 * @date 27.05.2020
 * @author Group 3C
 */

public class Genres extends AppCompatActivity {

    //Properties

    CardView pizza, dessert, hamburger, chicken, sushi, steak;
    FirebaseUser user;
    DatabaseReference mRef;

    //Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_genres);

        //Initialize

        pizza = (CardView)findViewById(R.id.pizza_cardview2);
        steak = (CardView)findViewById(R.id.steakCardView2);
        sushi = (CardView)findViewById(R.id.sushiCardView2);
        dessert = (CardView)findViewById(R.id.dessertCardView2);
        chicken = (CardView)findViewById(R.id.chickenCardView2);
        hamburger = (CardView)findViewById(R.id.hamburgerCardView2);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("Restaurants");

        //OnClickListeners

        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child(user.getUid()).child("genre").setValue("Pizza");
                startActivity( new Intent( Genres.this, RestaurantEditProfileActivity.class));
                finish();
            }
        });

        steak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child(user.getUid()).child("genre").setValue("Steak");
                startActivity( new Intent( Genres.this, RestaurantEditProfileActivity.class));
                finish();
            }
        });

        sushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child(user.getUid()).child("genre").setValue("Sushi");
                startActivity( new Intent( Genres.this, RestaurantEditProfileActivity.class));
                finish();
            }
        });

        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child(user.getUid()).child("genre").setValue("Dessert");
                startActivity( new Intent( Genres.this, RestaurantEditProfileActivity.class));
                finish();
            }
        });

        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child(user.getUid()).child("genre").setValue("Chicken");
                startActivity( new Intent( Genres.this, RestaurantEditProfileActivity.class));
                finish();
            }
        });

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child(user.getUid()).child("genre").setValue("Hamburger");
                startActivity( new Intent( Genres.this, RestaurantEditProfileActivity.class));
                finish();
            }
        });

    }
}
