package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        //TODO CONTROL ET
        listViewFavRestaurants = (ListView)findViewById(R.id.lvMyFavRestaurants);
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favRestaurants);
        listViewFavRestaurants.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Customers");
        mAuth = FirebaseAuth.getInstance();
        user =  mAuth.getCurrentUser();

        displayFavRestaurants();
    }

    private void displayFavRestaurants () {
        reference.child(user.getUid()).child("fav restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {

                    DataSnapshot item = items.next();
                    String name;
                    name = "Name : " + item.child("name").getValue().toString();// + "  Genre: " + item.child("genre").getValue().toString();

                    favRestaurants.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
