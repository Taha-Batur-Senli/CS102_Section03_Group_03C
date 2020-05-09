package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//Easter egg test
//easter egg test 2

public class MainMenu extends AppCompatActivity {

    private SearchView search;
    private DatabaseReference mRef;
    private ViewFlipper mViewFlipper;
    private FirebaseUser user;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.menu_main);

        mRef = FirebaseDatabase.getInstance().getReference("Customers");
        search = findViewById(R.id.search);
        name = (TextView)findViewById(R.id.textView6);

        mViewFlipper = findViewById(R.id.view_flipper);
        int[] images = { R.drawable.food_photo, R.drawable.pizza, R.drawable.steak};

        search.clearFocus();

        //Adding the images!
        for ( int x = 0; x < images.length; x++)
        {
            flipperImages( images[x]);
        } //Done!

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String nameString = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                name.setText( "Welcome," + nameString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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