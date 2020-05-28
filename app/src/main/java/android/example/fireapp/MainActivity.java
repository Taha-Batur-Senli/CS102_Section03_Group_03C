package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * This class is the customer main menu of our app from which a wide variety of tasks can be done.
 *@date 27.05.2020
 *@author Group 3C
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Properties
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView textView;
    ViewFlipper mViewFlipper;
    TextView cusNameTV, cusNameMenu;
    FirebaseUser user;
    DatabaseReference mRef;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    CardView steak, sushi, hamburger, chicken, dessert, pizza;
    Button promotions, bestRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        textView = findViewById(R.id.text);
        mViewFlipper = findViewById(R.id.view_flipper);
        cusNameMenu = findViewById(R.id.nav_customer_name);
        promotions = (Button)findViewById(R.id.btnPromotions);
        bestRestaurants = (Button)findViewById(R.id.btnBestRestaurants);

        steak = (CardView) findViewById(R.id.steakCardView);
        sushi = (CardView) findViewById(R.id.sushiCardView);
        pizza = (CardView) findViewById(R.id.pizza_cardview);
        hamburger = (CardView) findViewById(R.id.hamburgerCardView);
        dessert = (CardView) findViewById(R.id.dessertCardView);
        chicken = (CardView) findViewById(R.id.chickenCardView);

        //On click listeners of card views & buttons (Types of restaurants)
        steak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GenreSpecialRes.class);
                i.putExtra("GENRE", "Steak");
                startActivity(i);

            }
        });

        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GenreSpecialRes.class);
                i.putExtra("GENRE", "Pizza");
                startActivity(i);

            }
        });

        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GenreSpecialRes.class);
                i.putExtra("GENRE", "Chicken");
                startActivity(i);
            }
        });

        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GenreSpecialRes.class);
                i.putExtra("GENRE", "Dessert");
                startActivity(i);

            }
        });

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GenreSpecialRes.class);
                i.putExtra("GENRE", "Hamburger");
                startActivity(i);
            }
        });

        sushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GenreSpecialRes.class);
                i.putExtra("GENRE", "Sushi");
                startActivity(i);

            }
        });

        promotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PromotionsPOVCustomer.class));
            }
        });

        bestRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( MainActivity.this, BestRestaurantsDisplay.class));
            }
        });

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        cusNameTV = findViewById(R.id.customer_name_txt);
        mRef = FirebaseDatabase.getInstance().getReference("Customers");
        database = FirebaseDatabase.getInstance();

        //This part creates the welcome message that is specific to each user!
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String userName = (String)dataSnapshot.child(user.getUid()).child("name").getValue();
                cusNameTV.setText(("Welcome,\n" + userName + "!").toUpperCase());
                if (userName.toLowerCase().equals("david"))
                    cusNameTV.setText("Welcome David, thank you for everything sir :)");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Adding images to ViewFlipper
        int[] imgs = { R.drawable.food_photo, R.drawable.flipper_img};
        for ( int x = 0; x < imgs.length; x++)
        {
            flipperImages( imgs[x]);
        }
    }

    //This method is for the go back button in a phone.
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{}

    }

    /*
    *This method enables the ViewFlipper to function by handling the entrance and exit animations
    * as well as the screen time of the images inside the ViewFlipper.
    *@param an integer that corresponds to the index number of the image (which was added above!)
     */
    public void flipperImages (int image)
    {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource( image);

        mViewFlipper.addView( imageView);
        mViewFlipper.setFlipInterval( 3000);
        mViewFlipper.setAutoStart( true);

        //Slide part
        mViewFlipper.setInAnimation( this, android.R.anim.slide_in_left);
        mViewFlipper.setOutAnimation( this, android.R.anim.slide_out_right);
    }

    //This method is for the users to open the sliding menu and select the button they desire.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                break;
            case R.id.nav_show_all:
                startActivity( new Intent(MainActivity.this, AllRestaurantsDisplay.class));
                break;
            case R.id.nav_favourite_restaurants:
                startActivity(new Intent(MainActivity.this, MyFavActivities.class));
                break;
            case R.id.nav_my_reservations:
                startActivity(new Intent(MainActivity.this, MyReservations.class));
                break;
            case R.id.nav_help_call_us:
                startActivity(new Intent(MainActivity.this, CustHelpActivity.class));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(MainActivity.this, CustomerMyAccountActivity.class));
                break;
            case R.id.nav_log_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,
                        LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
