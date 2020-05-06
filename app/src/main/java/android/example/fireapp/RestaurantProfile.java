package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RestaurantProfile extends AppCompatActivity {
    //PROPERTIES
    Button logOut, editProfile, help, takeALookAtYourRestaurant, changeMenu;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser user;
    TextView resNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile2);

        //Initialization
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Restaurants");
        user = mAuth.getCurrentUser();

        resNameTV =(TextView)findViewById(R.id.txtNameRestaurantProfile);
        takeALookAtYourRestaurant = (Button)findViewById(R.id.takeALookAtYourRestaurant);
        editProfile =  (Button)findViewById(R.id.resEditProfile);
        help = (Button)findViewById(R.id.resHelp);
        logOut = (Button)findViewById(R.id.logOutResProfile);
        changeMenu = (Button)findViewById(R.id.btnChangeMenu);

        //Call methods
        takeALookAtYourRestaurantAction();
        logOffAction();
        helpAction();
        editTextAction();
        changeMenuAction();

        //Get customer info and display
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final String userName = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                resNameTV.setText("Welcome " + userName + "!");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //METHODS
    private void changeMenuAction() {
        changeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestaurantProfile.this, ChangeMenuActivity.class));
            }
        });
    }

    private void editTextAction() {
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestaurantProfile.this, RestaurantEditProfileActivity.class));
            }
        });
    }

    private void helpAction() {
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestaurantProfile.this, HelpRestaurant.class));
            }
        });
    }

   /* private void logOffAction() {
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent( RestaurantProfile.this, MainActivity.class));
                finish();
            }
        });

    }*/
   private void logOffAction() {
       logOut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new AlertDialog.Builder(RestaurantProfile.this)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .setTitle("Are you sure?")
                       .setMessage("Do you want to log out?")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               FirebaseAuth.getInstance().signOut();
                               startActivity(new Intent( RestaurantProfile.this, MainActivity.class));

                           }
                       })
                       .setNegativeButton("No", null)
                       .show();
           }
       });

   }

    private void takeALookAtYourRestaurantAction() {
        takeALookAtYourRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(RestaurantProfile.this, RestaurantProfileDisplay.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
