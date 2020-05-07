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
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MyFavActivities extends AppCompatActivity {
    ListView listViewFavRestaurants;
    ArrayAdapter myAdapter;
    ArrayList<String> favRestaurants = new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fav_activities);

        listViewFavRestaurants = (ListView)findViewById(R.id.lvMyFavRestaurants);
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favRestaurants);
        listViewFavRestaurants.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Customers");
        mAuth = FirebaseAuth.getInstance();
        user =  mAuth.getCurrentUser();

        displayFavRestaurants();
        listOnLongClickAction();
        displayRestProfileAction();

    }

    private void displayFavRestaurants () {
        reference.child(user.getUid()).child("fav restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favRestaurants.clear();
               Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {

                    DataSnapshot item = items.next();
                    String name;
                    name =  item.child("name").getValue().toString() + "   ";// + "  Genre: " + item.child("genre").getValue().toString();

                    favRestaurants.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    int index;
    private void listOnLongClickAction() {
        listViewFavRestaurants.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;

                new AlertDialog.Builder(MyFavActivities.this)
                        .setIcon(android.R.drawable.ic_input_add)
                        .setTitle("Remove from favorite restaurants")
                        .setMessage("Do you want to remove this restaurant from your favorite restaurants?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String item = myAdapter.getItem(index).toString();
                                int index1 = item.indexOf("   ");
                                final String s = item.substring(0,index1);
                                reference.child(user.getUid()).child("fav restaurants").orderByChild("name").
                                        equalTo(s).addValueEventListener(new ValueEventListener() {
                                    int i = 0;
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if ( i < 1) {
                                            reference.child(user.getUid()).child("fav restaurants").child(dataSnapshot.getChildren().iterator().next().getKey())
                                                    .removeValue();
                                            i++;
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

    private void displayRestProfileAction() {
        listViewFavRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //gets the name of the dish
                String item = favRestaurants.get(position);
                int indexOfName = item.indexOf("   ");
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
                                Intent intent = new Intent(MyFavActivities.this, CustomerPOVRestaurant.class);
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
