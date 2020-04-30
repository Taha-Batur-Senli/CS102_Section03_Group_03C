package com.example.ownersmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView1;
    EditText inputtext1;
    Button btnAdd, btnUpdate;

    ArrayList<String> foods = new ArrayList<String>();
    ArrayAdapter myAdapter1;

    Integer indexVal;
    String item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView1 = (ListView)findViewById(R.id.listview1);
        inputtext1 = (EditText)findViewById(R.id.editText);
        btnAdd = (Button)findViewById(R.id.button1);
        btnUpdate = (Button)findViewById(R.id.button2);

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
                myAdapter1.notifyDataSetChanged();

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
