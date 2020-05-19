package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class PromotionsPOVCustomer extends AppCompatActivity {
    //Properties
    ListView  listViewPromotions;
    ArrayAdapter myAdapter;
    ArrayList<String> promotions = new ArrayList<>();
    DatabaseReference reference2;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_promotions_p_o_v_customer);

        //Initialize
        listViewPromotions = (ListView) findViewById(R.id.listViewAllPromotionsPage);
        searchView = (SearchView) findViewById(R.id.searchView);
        myAdapter = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, promotions);
        listViewPromotions.setAdapter(myAdapter);
        reference2 = FirebaseDatabase.getInstance().getReference();

        //Methods called
        displayPromotions();
        promotionsClick();
        searchPromotions();
    }

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

    private void displayPromotions() {
        reference2.child("Promotions").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    final String key = ds.getKey();
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
                                String promo = resName + "   " + name;
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

    private void promotionsClick()
    {
        listViewPromotions.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Get restaurants name
                String promo = promotions.get(position);
                int index = promo.indexOf("   ");
                final String resName = promo.substring(0, index);

                //Go to restaurants profile
                final DatabaseReference refRests = FirebaseDatabase.getInstance().getReference("Restaurants");
                refRests.addValueEventListener(new ValueEventListener()
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
