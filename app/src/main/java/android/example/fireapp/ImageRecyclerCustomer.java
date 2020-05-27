package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.WindowManager;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*
 *
 *@date 27.05.2020
 *@author Group 3C
 */

public class ImageRecyclerCustomer extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private DatabaseReference databaseReference;
    private List<Upload> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_recycler);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploads = new ArrayList<>();
        final String restaurant_id = getIntent().getStringExtra("restaurant_id");

        databaseReference = FirebaseDatabase.getInstance().getReference("Restaurants");
        databaseReference.child(restaurant_id).child("Pictures").child("Gallery").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //dataSnapshot is a object that store datas like arraylists.
                for( DataSnapshot snapshot : dataSnapshot.getChildren()){ //checking the every object of data
                    Upload upload = snapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                adapter = new Adapter(ImageRecyclerCustomer.this, uploads);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ImageRecyclerCustomer.this,"failed to save the data" + databaseError.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }
}