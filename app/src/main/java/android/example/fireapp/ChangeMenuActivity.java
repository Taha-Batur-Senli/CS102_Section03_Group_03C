package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class ChangeMenuActivity extends AppCompatActivity implements addFoodDialog.addFoodListener {
    Button addDish;
    ListView lvMenuRes;
    ArrayAdapter myAdapter;
    ArrayList<String> menu = new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_menu);

        addDish = (Button)findViewById(R.id.btnAddFood);
        lvMenuRes = (ListView)findViewById(R.id.lvMenuRes);

        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        lvMenuRes.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference( "Restaurants");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        //Display Menu
        reference.child(user.getUid()).child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menu.clear();
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {

                    DataSnapshot item = items.next();
                    String name;
                    name = "" + item.child("name").getValue().toString() + ": " //Eğer burda değişiklik yaparsanız kod bozulabbilir
                            //Bir değişiklik yapmdan önce beni arayın birlikte bakalım @Ege
                            //name'den sonraki iki nokta üst üste orda olmak zorunda şuan, değiştircekseniz başka bi yerde
                            //daha değiştirmeniz gerek. SubString alıyorum noktalı virgüle referansla
                            + item.child("ingredients").getValue().toString() +
                            "___" + item.child("price").getValue().toString() + "TL";

                    menu.add(name);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editFoodAction();
        addDishAction();
    }

    private void editFoodAction() {
        lvMenuRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //gets the name of the dish
                String item = menu.get(position);
                int indexOfName = item.indexOf(":");
                String name = item.substring(0,indexOfName);

                Intent intent = new Intent(ChangeMenuActivity.this, ChangeFoodActivity.class);
                intent.putExtra("NAME", name);
                startActivity( intent);
                finish();
            }
        });
    }

    private void addDishAction() {
        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog(){
        addFoodDialog addFoodDialog = new addFoodDialog();
        addFoodDialog.show(getSupportFragmentManager(), "Add Dish To Menu");
    }

    @Override
    public void applyTexts(String name, String ingredients, String price) {

     String foodUid = reference.child(user.getUid()).child("menu").push().getKey();
        reference.child(user.getUid()).child("menu").child(foodUid).setValue(new Food(name, ingredients, Integer.parseInt(price)));
        reference.child(user.getUid()).child("menu").child(foodUid).child("ingredients").setValue(ingredients);
        DatabaseReference reference1 = database.getReference( "Restaurants");;

        reference1.child(user.getUid()).child("menu").child(foodUid).child("price").setValue(price);
        //reference.child(user.getUid()).child("menu").child(foodUid).child("uid").setValue(foodUid);
        //reference.child(user.getUid()).child("menu").child(name).child("ingredients").setValue(ingredients);
        //reference.child(user.getUid()).child("menu").child(name).child("price").setValue(price);

    }
}
