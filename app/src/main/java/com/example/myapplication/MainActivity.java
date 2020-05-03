package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] s1, s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = getResources().getStringArray(R.array.dishes);
        s2 = getResources().getStringArray(R.array.indexes);
        recyclerView = findViewById(R.id.recyclerView);

        MyAdapter adapter = new MyAdapter(this,s1,s2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}
