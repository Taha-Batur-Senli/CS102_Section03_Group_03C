package android.example.fireapp;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class CustomerProfile extends AppCompatActivity {
    //Properties
    private SearchView search;
    private ViewFlipper mViewFlipper;

    TextView cusNameTV;
    ListView listViewAllRestaurants, listViewPromotions;
    ArrayAdapter myAdapter,  myAdapter2;
    ArrayList<String> allRestaurants = new ArrayList<>();
    ArrayList<String> promotions = new ArrayList<String>();

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseUser user;
    DatabaseReference reference, reference2, mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_profile);

        //Initialize
        search = findViewById(R.id.search);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Customers");
        user = mAuth.getCurrentUser();
        cusNameTV = (TextView)findViewById(R.id.txtNameCustomerProfile);
        listViewAllRestaurants = findViewById(R.id.lvAllRestaurants);
        listViewPromotions = (ListView)findViewById(R.id.listViewAllPromotionsPOV);

        LinearLayout layout = findViewById(R.id.promotions_gallery);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < 16; i++)
        {
            View view1 = inflater.inflate(R.layout.promotions_items, layout ,false);
            TextView textView1 = view1.findViewById(R.id.textView20);
            textView1.setText("Seb's" + i);
            ImageView imageView1 = view1.findViewById(R.id.promotions_images);
            imageView1.setImageResource(R.drawable.pizza);

            layout.addView(view1);
        }


        mViewFlipper = findViewById(R.id.view_flipper);
        int[] images = { R.drawable.food_photo, R.drawable.pizza, R.drawable.steak};

        database = FirebaseDatabase.getInstance();
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allRestaurants);
        listViewAllRestaurants.setAdapter(myAdapter);
        reference = database.getReference();
        myAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, promotions);
        listViewPromotions.setAdapter(myAdapter2);
        reference2 = database.getReference();

        //Methods called
        displayBestRestaurants();
        addToFavRestaurants();
        displayPromotions();
        displayRestProfileAction();
        configureMenuButton();
        search.clearFocus();
        promotionsClick();

        //Adding the images!
        for ( int x = 0; x < images.length; x++)
        {
            flipperImages( images[x]);
        }

        //Get name and display a welcome message
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String userName = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                cusNameTV.setText("Welcome, " + userName + "!");
                if (userName.toLowerCase().equals("david"))
                    cusNameTV.setText("Welcome sir, we will miss you :(");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Methods

    /**
     * This method prints all of the promotions to related list view. It iterates trough firebase and
     * adds each promotion to promotions list view.
     */
    private void displayPromotions() {
        reference2.child("Promotions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String key = ds.getKey();
                    DatabaseReference mRef2 = FirebaseDatabase.getInstance().getReference("Promotions");
                    mRef2.child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                                final String resName = ds2.child("restaurantName").getValue().toString();
                                final String name = ds2.child("name").getValue().toString();
                                String promo = resName + "   " + name;
                                promotions.add(promo);
                                myAdapter2.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /*
     * This method prints all of the est restaurants on the related listview. It iterates trough firebase
     * and adds best restaurants to best restaurant list view.
     */
    private void displayBestRestaurants() {
        reference.child("Best Restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()){

                    DataSnapshot item = items.next();
                    String name;
                    name = "" + item.child("name").getValue().toString() + ", "  + item.child("genre").getValue().toString();

                    allRestaurants.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*
    This method enables best restaurants list view long-clickable. If a customer long-clicks to a best
    restaurant, they will be asked if they want to add that restaurant to their favorite restaurants
    list. They can choose yes or no.
     */
    int index;
    private void addToFavRestaurants() {
        listViewAllRestaurants.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //final int index;
                index = position;

                new AlertDialog.Builder(CustomerProfile.this)
                        .setIcon(android.R.drawable.ic_input_add)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to add this restaurant to your favorite restaurants?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String item = myAdapter.getItem(index).toString();
                                int index1 = item.indexOf(", ");
                                final String s = item.substring(0,index1);
                                reference.child("Restaurants").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                                        while(items.hasNext()) {
                                            DataSnapshot item1 = items.next();
                                            String searchName;
                                            String searchedId;
                                            if(item1.child("name").getValue().toString().equals(s)){

                                                searchedId = item1.child("uid").getValue().toString();
                                                mRef.child(user.getUid()).child("fav restaurants").child(searchedId).push();
                                                mRef.child(user.getUid()).child("fav restaurants").child(searchedId).child("name").setValue(s);
                                                mRef.child(user.getUid()).child("fav restaurants").child(searchedId).child("uid").setValue(searchedId);
                                                myAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                                myAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }

    /*
    This method makes promotions clickable. When a customer presses on a promotion, they are directed
    to the profile of restaurant who owns that promotion.
     */
    private void promotionsClick(){
        listViewPromotions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get restaurants name
                String promo = promotions.get(position);
                int index = promo.indexOf("   ");
                final String resName = promo.substring(0, index);

                //Go to restaurants profile
                final DatabaseReference refRests = FirebaseDatabase.getInstance().getReference("Restaurants");
                refRests.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        while (items.hasNext()) {
                            DataSnapshot item1 = items.next();
                            String searchedId;
                            if (item1.child("name").getValue().toString().equals(resName)) {

                                searchedId = item1.child("uid").getValue().toString();
                                Intent intent = new Intent(CustomerProfile.this, CustomerPOVRestaurant.class);
                                intent.putExtra("UID", searchedId);
                                startActivity( intent);
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    /*
    This method prevents a bug. When customer presses to back button, instead of logging off, they
    are asked whether they want to log off or not. If they choose not to log off, they will continue
    displaying their profile. If they log out, they will be directed to login page.
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log off?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(CustomerProfile.this, LogInActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    /*
    This method makes best restaurants list view clickable. When customer clicks on one best restaurant
    they are directed to that restaurant's profile.
     */
    private void displayRestProfileAction() {
        listViewAllRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //gets the name of the dish
                String item = allRestaurants.get(position);
                int indexOfName = item.indexOf(", ");
                final String name = item.substring(0,indexOfName);

                final DatabaseReference refRests = FirebaseDatabase.getInstance().getReference("Restaurants");
                refRests.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        while (items.hasNext()) {
                            DataSnapshot item1 = items.next();
                            String searchedId;
                            if (item1.child("name").getValue().toString().equals(name)) {

                                searchedId = item1.child("uid").getValue().toString();
                                Intent intent = new Intent(CustomerProfile.this, CustomerPOVRestaurant.class);
                                intent.putExtra("UID", searchedId);
                                startActivity( intent);
                                finish();
                                myAdapter .notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    public void configureMenuButton() {
        final Button menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                FragmentMenu menuFragment = new FragmentMenu();
                fragmentTransaction.add(R.id.activity_customer_profile, menuFragment);
                fragmentTransaction.commit();
                menuButton.setVisibility(View.INVISIBLE);
                menuButton.setClickable(false);
            }
        });
    }

    public void flipperImages (int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource( image);

        mViewFlipper.addView( imageView);
        mViewFlipper.setFlipInterval( 3000);
        mViewFlipper.setAutoStart( true);

        //Time to slide!
        mViewFlipper.setInAnimation( this, android.R.anim.slide_in_left);
        mViewFlipper.setOutAnimation( this, android.R.anim.slide_out_right);

    }
}
