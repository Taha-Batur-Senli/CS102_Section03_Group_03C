package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class CustomerProfile extends AppCompatActivity {
    //Properties
    Button logOut;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser user;
    TextView cusNameTV;
    ListView listViewAllRestaurants;
    ArrayAdapter myAdapter;
    ArrayList<String> allRestaurants = new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        //Initialize
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Customers");
        user = mAuth.getCurrentUser();
        cusNameTV = (TextView)findViewById(R.id.txtNameCustomerProfile);
        listViewAllRestaurants = (ListView)findViewById(R.id.lvAllRestaurants);

        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allRestaurants);
        listViewAllRestaurants.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        //Methods called
        displayAllRestaurants();
        listOnLongClickAction();
        logOutAction();

        //Get name and display a welcome message
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final String userName = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                cusNameTV.setText("Welcome " + userName + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    //METHODS
    private void displayAllRestaurants() {
        reference.child("Restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()){

                    DataSnapshot item = items.next();
                    String name, genre;
                    name = "Name : " + item.child("name").getValue().toString() + "  Genre: "  + item.child("genre").getValue().toString();

                    allRestaurants.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void logOutAction() {
        logOut = (Button) findViewById(R.id.btnLogOutCustomer);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent( CustomerProfile.this, MainActivity.class));
                finish();
            }
        });
    }

    private void listOnLongClickAction() {
        listViewAllRestaurants.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int index;
                index = position;

                new AlertDialog.Builder(CustomerProfile.this)
                        .setIcon(android.R.drawable.ic_input_add)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to add this restaurant to your favorite restaurants?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO  favorite restaurantsa  ekle
                                myAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }
}
