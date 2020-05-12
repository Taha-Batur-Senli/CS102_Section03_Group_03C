package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

/*
This class enables customers to display restaurants menu.
 */
public class ShowMenuPOV extends AppCompatActivity {
    TextView tvName;
    ListView lvMenu;
    ArrayAdapter myAdapter;
    ArrayList<String> menu = new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu_p_o_v);

        //get the uid of restaurant from previous activity
        Intent intent = getIntent();
        String uid = intent.getStringExtra("UID");

        lvMenu = (ListView)findViewById(R.id.lvMenuResPOV);
        tvName = (TextView)findViewById(R.id.txtNameShowMenuPOV);

        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        lvMenu.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference( "Restaurants");

        //Set the name of the restaurant to the related text view.
        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String nameof = dataSnapshot.child("name").getValue().toString();
                tvName.setText(nameof);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Display Menu
        reference.child(uid).child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menu.clear();
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {

                    DataSnapshot item = items.next();
                    String name;
                    name = "" + item.child("name").getValue().toString() + ": "
                            + item.child("ingredients").getValue().toString() +
                            "___" + item.child("price").getValue().toString() + "TL";

                    menu.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*
    This method prevents some bugs.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
