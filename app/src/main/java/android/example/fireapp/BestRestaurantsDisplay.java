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
        import android.widget.ListView;
        import android.widget.SearchView;

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
         * Showing the restaurant that is picked as favorite by users.
         * @date 03.05.2020
         * @author Group 3C
         */

        public class BestRestaurantsDisplay extends AppCompatActivity {

            //Properties

            ListView listViewBestRestaurants;
            ArrayAdapter myAdapter;
            ArrayList<String> bestRestaurants = new ArrayList<String>();
            DatabaseReference reference;
            SearchView searchView;
            FirebaseUser user;
            DatabaseReference mRef;

            @Override
            protected void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setContentView(R.layout.activity_best_restaurants_display);

                //Initialize

                listViewBestRestaurants = (ListView)findViewById(R.id.lvBestRestaurants2);
                searchView = (SearchView) findViewById(R.id.searchView);
                myAdapter = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, bestRestaurants);
                listViewBestRestaurants.setAdapter(myAdapter);
                reference = FirebaseDatabase.getInstance().getReference();
                mRef = FirebaseDatabase.getInstance().getReference("Customers");
                user = FirebaseAuth.getInstance().getCurrentUser();

                //Methods called

                displayBestRestaurants();
                makeLvClickable();
                searchRestaurant();
                listOnLongClickAction();
            }

            //Methods

            /**
             This method makes all best restaurants clickable. If a customer clicks on a restaurant, they are
             directed to the profile of that restaurant.
             */
            private void makeLvClickable() {
                listViewBestRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //gets the name of the restaurant
                        String item = bestRestaurants.get(position);
                        int indexOfName = item.indexOf(", ");
                        final String name = item.substring(0,indexOfName);

                        final DatabaseReference refRests = FirebaseDatabase.getInstance().getReference("Restaurants");
                        refRests.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                                while (items.hasNext()) {
                                    DataSnapshot item1 = items.next();
                                    String searchedId;
                                    if (item1.child("name").getValue().toString().equals(name)) {

                                        searchedId = item1.child("uid").getValue().toString();
                                        Intent intent = new Intent(BestRestaurantsDisplay.this, CustomerPOVRestaurant.class);
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

            public void searchRestaurant(){
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
            Prints all of the restaurants on the related list view. Iterates trough firebase and adds each
            restaurant to best restaurants list view.
          */
            private void displayBestRestaurants() {
                reference.child("Restaurants").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        bestRestaurants.clear();
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        while (items.hasNext()) {

                            DataSnapshot item = items.next();
                            if (item.child("rating").exists()) {
                                String ratingS = (String) item.child("rating").getValue().toString();
                                double rating = Double.parseDouble(ratingS);

                                if (rating >= 4) {
                                    String name;
                                    name = item.child("name").getValue().toString() + ", " + item.child("genre").getValue().toString();

                                    bestRestaurants.add(name);
                                    myAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            /**
            This method makes restaurants long-clickable. If a customers long-clicks on a restaurant, they are
            asked if they want to add that restaurant to favorite restaurants list.
         */
            private void listOnLongClickAction() {
                listViewBestRestaurants.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        //final int index;
                        new AlertDialog.Builder(BestRestaurantsDisplay.this)
                                .setIcon(android.R.drawable.ic_input_add)
                                .setTitle("Are you sure?")
                                .setMessage("Do you want to add this restaurant to your favorite restaurants?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final String item = myAdapter.getItem(position).toString();
                                        int index1 = item.indexOf(", ");
                                        final String s = item.substring(0,index1);
                                        reference.child("Restaurants").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                                                while(items.hasNext()) {
                                                    DataSnapshot item1 = items.next();
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

            /**
             * if customer presses to back with out taking any action, we delete this activity from activity history
             */
            @Override
            public void onBackPressed() {
                super.onBackPressed();
                finish();
            }
        }
