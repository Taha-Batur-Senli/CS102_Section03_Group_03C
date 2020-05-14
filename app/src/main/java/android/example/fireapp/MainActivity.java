package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    SearchView search;
    ViewFlipper mViewFlipper;
    TextView cusNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        //toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.text);
        search = findViewById(R.id.search);
        mViewFlipper = findViewById(R.id.view_flipper);

        //setSupportActionBar(toolbar);
        //toolbar.setTitle("");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        //Text color of searchView
        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = search.findViewById(id);
        textView.setTextColor(Color.WHITE);

        search.clearFocus();

        //giving images to flipperview
        int[] imgs = { R.drawable.food_photo, R.drawable.pizza, R.drawable.steak};
        for ( int x = 0; x < imgs.length; x++)
        {
            flipperImages( imgs[x]);
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

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
                startActivity( new Intent(MainActivity.this, LogInActivity.class));
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
