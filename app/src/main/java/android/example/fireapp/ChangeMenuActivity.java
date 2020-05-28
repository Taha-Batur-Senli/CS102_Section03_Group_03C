package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.Change;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * changing menu by using add dish button or delete button.
 *@date 25.05.2020
 *@author Group 3C
 */

public class ChangeMenuActivity extends AppCompatActivity implements addFoodDialog.addFoodListener {

    //Properties

    ImageView removeDish, addDish;
    ListView lvMenuRes;
    MyCustomAdapter myAdapter;
    ArrayList<String> menu = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //making the activity full screen
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_menu);

        //Initialize

        lvMenuRes = (ListView)findViewById(R.id.lvMenuRes);
        myAdapter = new MyCustomAdapter(menu, this);
        lvMenuRes.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference( "Restaurants");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        addDish = findViewById(R.id.add_new_dish);

        //Display Menu

        /**
         * EventListener to retrieve the menu information from the firebase.
         */
        reference.child(user.getUid()).child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menu.clear();
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {
                    DataSnapshot item = items.next();
                    String name;
                    name = "" + item.child("name").getValue().toString() + ", " + item.child("price").getValue().toString()
                            + "$\n" + item.child("ingredients").getValue().toString();

                    menu.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /**
         * Adding the dish to the menu by calling addFragment method.
         */
        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment();
            }
        });


        //Methods called
        listOnLongClickAction();
    }

    /**
    This method makes each dish clickable. When restaurant owners click to a dish on their menu,
    they are directed to a page in which they can edit their dish's name, şngredients and price.
     */
    public void addFragment()
    {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        FragmentMenu fragmentMenu = new FragmentMenu();
        fragmentTransaction.add( R.id.change_menu, fragmentMenu );
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.change_menu);
        if( fragment != null)
        {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
        else {
            super.onBackPressed();
            //TODO crash
        }
    }

    /**
     * putting these three parameters into the firebase under the right child.
     * @param name
     * @param ingredients
     * @param price
     */
    @Override
    public void applyTexts(String name, String ingredients, String price) {
     String foodUid = reference.child(user.getUid()).child("menu").push().getKey();
        reference.child(user.getUid()).child("menu").child(foodUid).setValue(new Food(name, ingredients, Integer.parseInt(price)));
    }

    int index;

    /**
    This method makes menu long-clickable. When a restaurant owner long-clicks on a dish on their menu
    an alert dialog pop outs and asks if they want to delete the dish from their menu or not. If they
    select "delete" option, the dish is deleted from database.
     */
    private void listOnLongClickAction() {
        lvMenuRes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                index = position;

                new AlertDialog.Builder(ChangeMenuActivity.this)
                        .setIcon(android.R.drawable.ic_input_add)
                        .setTitle("This dish will be deleted from your menu!")
                        .setMessage("Do you want to proceed?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String food = menu.get(position);
                                int index = food.indexOf(",");
                                String foodName = food.substring(0, index);
                                final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Restaurants");
                                mRef.child(user.getUid()).child("menu").orderByChild("name").equalTo(foodName).addValueEventListener(new ValueEventListener() {
                                    int i = 0;
                                    @Override
                                    public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                                        if ( i < 1) {
                                            mRef.child(user.getUid()).child("menu").child(dataSnapshot.getChildren().iterator().next().getKey())
                                                    .removeValue();
                                            i++;
                                        }

                                        startActivity(new Intent(ChangeMenuActivity.this, RestaurantProfile.class));
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled (@NonNull DatabaseError databaseError){

                                    }
                                });


                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            }
        });
    }
}
