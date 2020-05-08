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
    Button logOut, myAccount, help, allRestaurantsDisplay, myFavRestaurants;
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
        myFavRestaurants = (Button)findViewById(R.id.btnMyFavRestaurants);

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
        myFavRestaurantsActivity();
        displayRestProfileAction();


        //Get name and display a welcome message
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String userName = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                cusNameTV.setText("Welcome " + userName + "!");
                if (userName.toLowerCase().equals("david"))
                    cusNameTV.setText("Welcome hocam, we will miss you :(");
                if ( userName.toLowerCase().equals("naz"))
                    cusNameTV.setText("Selam Kuzu!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void myFavRestaurantsActivity() {
        myFavRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerProfile.this, MyFavActivities.class));
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
                    name = "" + item.child("name").getValue().toString() + ", "  + item.child("genre").getValue().toString();

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
                startActivity(new Intent( CustomerProfile.this, LogInActivity.class));
                finish();
            }
        });
    }

    int index;

    private void listOnLongClickAction() {
        listViewAllRestaurants.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //final int index;
                index = position;

                new AlertDialog.Builder(CustomerProfile.this)
                        .setIcon(android.R.drawable.ic_input_add)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to add this restaurant to your favorite restaurants?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String item = myAdapter.getItem(index).toString();
                                int index1 = item.indexOf(", ");
                                final String s = item.substring(0,index1);
                                reference.child("Restaurants").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                                        while(items.hasNext()) {
                                            DataSnapshot item1 = items.next();
                                            String searchName;
                                            String searchedId;
                                            if(item1.child("name").getValue().toString().equals(s)){

                                                searchedId = item1.child("uid").getValue().toString();
                                                mRef.child(user.getUid()).child("fav restaurants").child(searchedId).push();
                                                mRef.child(user.getUid()).child("fav restaurants").child(searchedId).child("name").setValue(s);
                                                mRef.child(user.getUid()).child("fav restaurants").child(searchedId).child("uid").setValue(searchedId);
                                                myAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log off?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(CustomerProfile.this, LogInActivity.class));
                        finish();
                        //CustomerProfile.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void displayRestProfileAction() {
        listViewAllRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //gets the name of the dish
                String item = allRestaurants.get(position);
                int indexOfName = item.indexOf(", ");
                final String name = item.substring(0,indexOfName);

                final DatabaseReference refRests = FirebaseDatabase.getInstance().getReference("Restaurants");
                refRests.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        while (items.hasNext()) {
                            DataSnapshot item1 = items.next();
                            String searchedId;
                            if (item1.child("name").getValue().toString().equals(name)) {

                                searchedId = item1.child("uid").getValue().toString();
                                Intent intent = new Intent(CustomerProfile.this, CustomerPOVRestaurant.class);
                                intent.putExtra("UID", searchedId);
                                startActivity( intent);
                                finish();
                                myAdapter .notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}