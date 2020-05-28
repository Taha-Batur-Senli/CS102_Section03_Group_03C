package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This is the activity where customers can see the promotions offered by restaurants
 *@date 28.04.2020
 *@author Group 3C
 */
public class PromotionsPOVCustomer extends AppCompatActivity {

    // Properties
    private ListView  listViewPromotions;
    private ArrayAdapter myAdapter;
    private ArrayList<String> promotions = new ArrayList<>();
    private DatabaseReference reference2;
    private SearchView searchView;

    // Methods

    /**
     * The method that is called when a customer opens promotions page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_promotions_p_o_v_customer);

        //Initializing variables
        listViewPromotions = (ListView) findViewById(R.id.listViewAllPromotionsPage);
        searchView = (SearchView) findViewById(R.id.searchView);
        myAdapter = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, promotions);
        listViewPromotions.setAdapter(myAdapter);
        reference2 = FirebaseDatabase.getInstance().getReference();

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(id);
        textView.setTextColor(Color.WHITE);

        //Calling methods
        displayPromotions();
        promotionsClick();
        searchPromotions();
    }

    /**
     * The method that makes it possible to search for a particular promotion by typing letters
     */
    public void searchPromotions(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    /**
     * The method that retrieves information about promotions from Firebase and displays them
     */
    private void displayPromotions() {
        reference2.child("Promotions").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    final String key = ds.getKey(); // key is the uid of the promotion
                    DatabaseReference mRef2 = FirebaseDatabase.getInstance().getReference("Promotions");
                    mRef2.child(key).addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            for (DataSnapshot ds2 : dataSnapshot.getChildren())
                            {
                                final String resName = ds2.child("restaurantName").getValue().toString();
                                final String name = ds2.child("name").getValue().toString();
                                String promo = resName + "\n" + name;
                                promotions.add(promo);
                                myAdapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    /**
     * This method directs customers to the reservation page of the restaurant when they click on a promotion
     */
    private void promotionsClick()
    {
        listViewPromotions.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Get restaurant's name
                String promo = promotions.get(position);
                int index = promo.indexOf("\n");
                final String resName = promo.substring(0, index);

                //Go to restaurant's profile
                final DatabaseReference refRests = FirebaseDatabase.getInstance().getReference("Restaurants");
                refRests.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        while (items.hasNext())
                        {
                            DataSnapshot item1 = items.next();
                            String searchedId;
                            if (item1.child("name").getValue().toString().equals( resName ))
                            {
                                searchedId = item1.child("uid").getValue().toString();
                                Intent intent = new Intent(PromotionsPOVCustomer.this, CustomerPOVRestaurant.class);
                                intent.putExtra("UID", searchedId);
                                startActivity( intent);
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
        });
    }
}
