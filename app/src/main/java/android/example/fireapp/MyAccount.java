package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyAccount extends AppCompatActivity {
    /*ListView listViewAllRestaurants;
    ArrayList<String> allRestaurants = new ArrayList<String>();
    ArrayAdapter myAdapter;
    FirebaseDatabase database;
    DatabaseReference reference;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        /*listViewAllRestaurants = (ListView)findViewById(R.id.lvAllRestaurants);

        allRestaurants.add("Örnektir:Nusret");
        allRestaurants.add("Örnektir: salatalık");
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allRestaurants);
        listViewAllRestaurants.setAdapter(myAdapter);

        database = FirebaseDatabase.getInstance();

         reference = database.getReference();


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
        });*/
    }
}
