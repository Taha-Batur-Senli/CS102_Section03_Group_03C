package com.example.ownersmenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView1;
    EditText inputtext1;
    Button btnAdd, btnUpdate;

    ArrayList<String> foods = new ArrayList<String>();
    ArrayAdapter myAdapter1;

    Integer indexVal;
    String item;

    DatabaseReference reference ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView1 = (ListView)findViewById(R.id.listview1);
        inputtext1 = (EditText)findViewById(R.id.editText);
        btnAdd = (Button)findViewById(R.id.button1);
        btnUpdate = (Button)findViewById(R.id.button2);
        reference = FirebaseDatabase.getInstance().getReference();



        //setup listview
        foods.add("Havuç");
        foods.add("36cm salatalık");




        myAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foods);

        listView1.setAdapter(myAdapter1);

        //ADD FOODS
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringval = inputtext1.getText().toString();

                foods.add(stringval);
                reference.push().setValue(stringval);
                myAdapter1.notifyDataSetChanged();


            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String string = dataSnapshot.getValue(String.class);
                foods.add(string);
                myAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //selected item
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString() + "selected";
                indexVal = position;

                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
            }
        });


        //UPDATE MENU
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringval = inputtext1.getText().toString();
                foods.set(indexVal, stringval);

                myAdapter1.notifyDataSetChanged();
            }
        });

    }
}
