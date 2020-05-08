package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainMenu extends AppCompatActivity {

    private SearchView search;
    private ViewFlipper mViewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.menu_main);

        search = findViewById(R.id.search);

        mViewFlipper = findViewById(R.id.view_flipper);
        int[] images = { R.drawable.food_photo, R.drawable.pizza, R.drawable.steak};

        search.clearFocus();

        //Adding the images!
        for ( int x = 0; x < images.length; x++)
        {
            flipperImages( images[x]);
        }
    }

    public void flipperImages (int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource( image);

        mViewFlipper.addView( imageView);
        mViewFlipper.setFlipInterval( 3000);
        mViewFlipper.setAutoStart( true);

        //Time to slide!
        mViewFlipper.setInAnimation( this, android.R.anim.slide_in_left);
        mViewFlipper.setOutAnimation( this, android.R.anim.slide_out_right);

    }
}