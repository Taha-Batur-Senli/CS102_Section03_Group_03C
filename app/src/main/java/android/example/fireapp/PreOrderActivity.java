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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/*
In this class, customers can pre-order and finalize their reservation.
 */
public class PreOrderActivity extends AppCompatActivity {
    //Properties
    TextView minPricetv, yourMoneyTv, totalTv;
    Button pay;
    ListView lvMenu, lvMyOrder;
    ArrayAdapter myAdapter, myAdapter2;
    ArrayList<String> menu = new ArrayList<String>();
    ArrayList<String> preOrder = new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference reference, mRefUser;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);

        //Get restaurants data
        Intent i = getIntent();
        String minPrice = i.getStringExtra("MINPRICE");
        String resUid = i.getStringExtra("UID");

        //Initialize
        minPricetv = (TextView) findViewById(R.id.txtPreOrderMinPrice);
        pay = (Button)findViewById(R.id.btnFinishAndPay);
        lvMenu = (ListView) findViewById(R.id.lvMenuResPreOrder);
        lvMyOrder = (ListView)findViewById(R.id.lvMyOrder);
        totalTv = (TextView)findViewById(R.id.txtTotal);
        yourMoneyTv = (TextView)findViewById(R.id.txtYourMoney);

        myAdapter = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, menu);
        myAdapter2 = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, preOrder);
        lvMenu.setAdapter(myAdapter);
        lvMyOrder.setAdapter(myAdapter2);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Restaurants");
        mRefUser = database.getReference("Customers");
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Methods called
        displayMenu(resUid, minPrice);
        removeFromMyOrder(minPrice);
        finishAndPay();


        //Set requires text views
        minPricetv.setText(minPrice + " TL left to satisfy the minimum limit to pre-order.");
        mRefUser.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String money = dataSnapshot.child("money").getValue().toString();
                yourMoneyTv.setText("You have " + money+ "TL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //METHODS

    /*
    This method displays the menu of the restaurant. It iterates trough restaurant's menu and places
    each dish to the related list view.
     */
    private void displayMenu(String uid, final String minPrice) {
        reference.child(uid).child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {
                    DataSnapshot item = items.next();
                    String name;
                    name = "" + item.child("name").getValue().toString() + ": "
                            + item.child("ingredients").getValue().toString() +
                            "___" + item.child("price").getValue().toString() + "TL";

                    menu.add(name);
                    myAdapter.notifyDataSetChanged();
                    addToMyOrder(minPrice);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*
    This method makes each dish on the menu clickable. When a customer selects a dish, it is displayed
    on their order as well. Also, the total price is updated and the minimum limit to pre-order is
    decreased.
     */
    private void addToMyOrder(final String minPrice) {
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get price
                String item = menu.get(position);
                int index = item.indexOf("___");
                String price = item.substring((index + 3), (item.length()-2) );

                double minPriceLimit = Double.parseDouble(minPrice);
                double priceOfDish = Double.parseDouble(price);


                String totalS = totalTv.getText().toString();
                double total = Double.parseDouble(totalS);

                total += priceOfDish;
                if ( minPriceLimit - total <= 0)
                    minPricetv.setText("You satisfied the limit!");
                else
                    minPricetv.setText( minPriceLimit - total + " TL left to satisfy the minimum limit to pre-order.");
                totalTv.setText( total +"");
                preOrder.add(item);
                myAdapter2.notifyDataSetChanged();
            }
        });
    }

    /*
    This method makes 'my order' long clickable. If a customer wishes to remove a dish from their order,
    they can long-click on it and select 'remove' option. When a dish is removed from order, total
    price is reduced and minimum limit to pre-order is checked again.
     */
    private void removeFromMyOrder(final String minPrice) {
        lvMyOrder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PreOrderActivity.this);
                builder.setMessage("Are you sure you want to remove this dish from your order?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                               //Get dish's price
                               String item = preOrder.get(position);
                               int index = item.indexOf("___");
                               double price = Double.parseDouble(item.substring((index + 3), (item.length()-2) ));

                                //get total price and reduce dish's price
                                String total = totalTv.getText().toString();
                                totalTv.setText((Double.parseDouble(total) - price) + "");

                                //remove from my order
                                preOrder.remove(position);
                                myAdapter2.notifyDataSetChanged();

                                //Set how much more needed to satisfy the minimum pre order
                                double remaining = Double.parseDouble(minPrice) - Double.parseDouble(totalTv.getText().toString());
                                if ( remaining <= 0)
                                    minPricetv.setText("You satisfied the limit!");
                                else
                                    minPricetv.setText( remaining + " TL left to satisfy the minimum limit to pre-order.");

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return false;
            }
        });
    }

    /*
    This method finalizes the pre-order process. If customer doesn't have enough money, they are
    directed to an activity in which they add money to their wallet. Else, their reservation is finalized.
     */
    private void finishAndPay() {
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = minPricetv.getText().toString();
                if (!check.equals("You satisfied the limit!")){
                    minPricetv.setError("!!");
                    minPricetv.requestFocus();
                    return;
                } else {
                    //check if you have enough money
                    String money = yourMoneyTv.getText().toString();
                    final double moneyOnAccount = Double.parseDouble(money.substring(9, money.length() - 2));
                    final double priceTotal = Double.parseDouble(totalTv.getText().toString());
                    if ( moneyOnAccount < priceTotal) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PreOrderActivity.this);
                        builder.setMessage("You don't have enough money!")
                                .setCancelable(false)
                                .setPositiveButton("Add money to my account.", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        startActivity(new Intent(PreOrderActivity.this, AddMoneyActivity.class));
                                        finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                    } else {
                        //TODO add reservations
                        AlertDialog.Builder builder = new AlertDialog.Builder(PreOrderActivity.this);
                        builder.setMessage("Reservation made successfully!")
                                .setCancelable(false)
                                .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        double remainingMoney = moneyOnAccount - priceTotal;
                                        mRefUser.child(user.getUid()).child("money").setValue(String.valueOf(remainingMoney));
                                        startActivity(new Intent(PreOrderActivity.this, CustomerProfile.class));
                                        finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            }
        });
    }
}