package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class AllRestaurantsDisplay extends AppCompatActivity {
    ListView listViewAllRestaurants;
    ArrayAdapter myAdapter;
    ArrayList<String> allRestaurants = new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference reference;
    SearchView searchViewRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_restaurants_display);

        listViewAllRestaurants = (ListView)findViewById(R.id.lvAllRestaurants2);
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allRestaurants);
        listViewAllRestaurants.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        searchViewRestaurant = (SearchView)findViewById(R.id.searchViewRestaurant);
        searchViewRestaurant.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });

        displayAllRestaurants();
        listOnLongClickAction();


    }
    private void displayAllRestaurants () {
        reference.child("Restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {

                    DataSnapshot item = items.next();
                    String name, genre;
                    name =  item.child("name").getValue().toString() + "/  Genre: " + item.child("genre").getValue().toString();

                    allRestaurants.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listOnLongClickAction() {
        listViewAllRestaurants.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int index;
                index = position;

                new AlertDialog.Builder(AllRestaurantsDisplay.this)
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
