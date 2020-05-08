package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class PromotionsDisplayActivity extends AppCompatActivity {
    Button add;
    ListView listViewResPromotions;
    ArrayAdapter myAdapter;
    ArrayList<String> resPromotions= new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions_display);

        add = (Button)findViewById(R.id.btnAddPromotion);
        listViewResPromotions = (ListView)findViewById(R.id.lvResPromotions);
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resPromotions);
        listViewResPromotions.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Promotions");
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Promotions");
        user = mAuth.getCurrentUser();

        addPromoActivity();
        displayRestPromo();
        listOnLongClickAction();

    }

    private void addPromoActivity() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PromotionsDisplayActivity.this, AddPromoActivity.class));
            }
        });
    }

    private void displayRestPromo () {
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resPromotions.clear();
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {

                    DataSnapshot item = items.next();
                    String name;
                    name = item.child("name").getValue().toString();// + ", " + item.child("genre").getValue().toString();

                    resPromotions.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    int index;
    private void listOnLongClickAction() {
        listViewResPromotions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;

                new AlertDialog.Builder(PromotionsDisplayActivity.this)
                        .setIcon(android.R.drawable.ic_input_add)
                        .setTitle("Deleting promotion!")
                        .setMessage("Are you sure to delete this promotion?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String item = myAdapter.getItem(index).toString();

                                reference.child(user.getUid()).orderByChild("name").equalTo(item).addValueEventListener(new ValueEventListener() {
                                    int i  = 0;
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if ( i < 1) {
                                            reference.child(user.getUid()).child(dataSnapshot.getChildren().iterator().next().getKey())
                                                    .removeValue();
                                            i++;
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }
}
