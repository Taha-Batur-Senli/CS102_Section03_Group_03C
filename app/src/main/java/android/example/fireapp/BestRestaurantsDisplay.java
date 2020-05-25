package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class BestRestaurantsDisplay extends AppCompatActivity {
    //Properties
    ListView listViewBestRestaurants;
    ArrayAdapter myAdapter;
    ArrayList<String> bestRestaurants = new ArrayList<String>();
    DatabaseReference reference;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_best_restaurants_display);

        //Initialize
        listViewBestRestaurants = (ListView)findViewById(R.id.lvBestRestaurants2);
        searchView = (SearchView) findViewById(R.id.searchView);
        myAdapter = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, bestRestaurants);
        listViewBestRestaurants.setAdapter(myAdapter);
        reference = FirebaseDatabase.getInstance().getReference();

        //Methods called
        displayBestRestaurants();
        makeLvClickable();
        searchRestaurant();
    }

    //Methods
    private void makeLvClickable() {
        listViewBestRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //gets the name of the dish
                String item = bestRestaurants.get(position);
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
                                Intent intent = new Intent(BestRestaurantsDisplay.this, CustomerPOVRestaurant.class);
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

    public void searchRestaurant(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }

    private void displayBestRestaurants() {
        reference.child("Restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bestRestaurants.clear();
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {

                    DataSnapshot item = items.next();
                    if (item.child("rating").exists()) {
                        String ratingS = (String) item.child("rating").getValue().toString();
                        double rating = Double.parseDouble(ratingS);

                        if (rating >= 3.75) {
                            String name;
                            name = item.child("name").getValue().toString() + ", " + item.child("genre").getValue().toString();

                            bestRestaurants.add(name);
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
