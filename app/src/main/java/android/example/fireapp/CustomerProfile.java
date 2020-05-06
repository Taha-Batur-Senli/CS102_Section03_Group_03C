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
    Button logOut, myAccount, help, allRestaurantsDisplay;;
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

        myAccount = (Button)findViewById(R.id.btnMyAccount);
        help = (Button)findViewById(R.id.btnHelpCustomer);
        allRestaurantsDisplay = (Button)findViewById(R.id.btnAllRestaurants);


        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allRestaurants);
        listViewAllRestaurants.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        //Methods called
        displayAllRestaurants();
        listOnLongClickAction();
        myAccountAction();
        logOutAction();
        helpActivity();
        allRestaurantsDisplayActivity();


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
    private void allRestaurantsDisplayActivity() {
        allRestaurantsDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerProfile.this, AllRestaurantsDisplay.class));
            }
        });
    }

    private void helpActivity() {
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerProfile.this,CustHelpActivity.class));
            }
        });
    }

    private void displayAllRestaurants() {
        reference.child("Best Restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()){

                    DataSnapshot item = items.next();
                    String name;
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
    /*private void logOutAction() {
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CustomerProfile.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent( CustomerProfile.this, MainActivity.class));

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

    }*/

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
                                String item = allRestaurants.get(index);
                                int index = item.indexOf(" ");
                                String s = item.substring(5,index);
                                mRef.child(user.getUid()).child("fav restaurants").child("name").setValue(s);
                                myAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }

    //TODO fav restorant olanların yanına * imgesi eklenir belki
    private void myAccountAction() {
        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerProfile.this, CustomerMyAccountActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}