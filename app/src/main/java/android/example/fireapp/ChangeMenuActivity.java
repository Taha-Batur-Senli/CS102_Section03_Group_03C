package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class ChangeMenuActivity extends AppCompatActivity implements addFoodDialog.addFoodListener {
    Button addDish;
    ListView lvMenuRes;
    ArrayAdapter myAdapter;
    ArrayList<String> menu = new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_menu);

        addDish = (Button)findViewById(R.id.btnAddFood);
        lvMenuRes = (ListView)findViewById(R.id.lvMenuRes);

        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        lvMenuRes.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference( "Restaurants");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        menu.add("Deneme");
        menu.add("Deneme2");
        menu.add("Deneme3");
        menu.add("Deneme4");
        //TODO abi firebase e ekliyo yemeği ama listview da çağırmıyo

        //Display Menu
        reference.child("Restaurants").child(user.getUid()).child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {

                    DataSnapshot item = items.next();
                    String name;
                    name = "" + item.child("name").getValue().toString() + "_________________________________________"
                            + item.child("ingredients").getValue().toString() +
                            "                " + item.child("price").getValue().toString() + "TL";

                    menu.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addDishAction();
    }

    private void addDishAction() {
        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog(){
        addFoodDialog addFoodDialog = new addFoodDialog();
        addFoodDialog.show(getSupportFragmentManager(), "Add Dish To Menu");
    }

    @Override
    public void applyTexts(String name, String ingredients, String price) {
     String foodUid = reference.child(user.getUid()).child("menu").push().getKey();
        reference.child(user.getUid()).child("menu").child(foodUid).child("name").setValue(name);
        reference.child(user.getUid()).child("menu").child(foodUid).child("ingredients").setValue(ingredients);
        reference.child(user.getUid()).child("menu").child(foodUid).child("price").setValue(price);
    }







}
