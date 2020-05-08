package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

public class ShowMenuPOV extends AppCompatActivity {
    TextView tvName;
    ListView lvMenu;
    ArrayAdapter myAdapter;
    ArrayList<String> menu = new ArrayList<String>();
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu_p_o_v);

        Intent intent = getIntent();
        String uid = intent.getStringExtra("UID");

        lvMenu = (ListView)findViewById(R.id.lvMenuResPOV);
        tvName = (TextView)findViewById(R.id.txtNameShowMenuPOV);

        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        lvMenu.setAdapter(myAdapter);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference( "Restaurants");

        tvName.setText(uid);

        //Display Menu
        reference.child(uid).child("menu").addValueEventListener(new ValueEventListener() {
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
