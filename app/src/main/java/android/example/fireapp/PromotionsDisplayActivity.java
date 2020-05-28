package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

/**
 * This class enables restaurant owners to manage their promotions. They can add or delete
 * promotions using this activity.
 *@date 26.04.2020
 *@author Group 3C
 */

public class PromotionsDisplayActivity extends AppCompatActivity {
    //Properties
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_promotions_display);

        //Initialize
        add = (Button)findViewById(R.id.btnAddPromotion);
        listViewResPromotions = (ListView)findViewById(R.id.lvResPromotions);
        myAdapter = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, resPromotions);
        listViewResPromotions.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Promotions");
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Promotions");
        user = mAuth.getCurrentUser();

        //Methods called
        addPromoActivity();
        displayRestPromo();
        listOnLongClickAction();

    }

    /*
    This method directs restaurant owners to an activity where they can add a new promotion.
     */
    private void addPromoActivity() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PromotionsDisplayActivity.this, AddPromoActivity.class));
            }
        });
    }

    /*
    This method prints out all of the promotions a restaurant have on the related list view by
    iterating trough firebase.
     */
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

    /*
    This method makes promotions long-clickable. When an owner wishes to delete a promotion, they
    can long-click on that promotion. Then, they are asked if they want to remove that
    promotion or not. If they choose to remove, that promotion is removed from firebase as well.
     */
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
