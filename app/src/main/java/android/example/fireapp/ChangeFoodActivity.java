package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class ChangeFoodActivity extends AppCompatActivity {
    Button save;
    EditText etName, etIngredients, etPrice;
    TextView nameTV;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_food);

        //Initialization
        etName = (EditText)findViewById(R.id.etFoodNameChange);
        etIngredients = (EditText)findViewById(R.id.etFoodIngredientChange);
        etPrice = (EditText)findViewById(R.id.etFoodPriceChange);
        save = (Button)findViewById(R.id.bttnSaveFoodChange);
        nameTV = (TextView)findViewById(R.id.txtFoodNameChange);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference("Restaurants");

        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        nameTV.setText(name);

        saveChangesAction();

    }
    //TODO ikinci defa change ettiğimde bozuluyor
    private void saveChangesAction() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("NAME");
                mRef.child(user.getUid()).child("menu").orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
                    int i, j, k = 0;
                    final String nameUpdate = etName.getText().toString();
                    final String ingredientsUpdate = etIngredients.getText().toString();
                    final String priceUpdate = etPrice.getText().toString();

                    @Override
                    public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                        if ( i < 1 && !nameUpdate.isEmpty()) {
                            mRef.child(user.getUid()).child("menu").child(dataSnapshot.getChildren().iterator().next().getKey())
                                    .child("name").setValue(nameUpdate);
                            i++;
                        }
                        if (j < 1 && !ingredientsUpdate.isEmpty()) {
                          mRef.child(user.getUid()).child("menu").child(dataSnapshot.getChildren().iterator().next().getKey())
                                    .child("ingredients").setValue(ingredientsUpdate);
                            j++;
                        }
                        if (k < 1 && !priceUpdate.isEmpty()) {
                           mRef.child(user.getUid()).child("menu").child(dataSnapshot.getChildren().iterator().next().getKey())
                                   .child("price").setValue(priceUpdate);
                           k++;
                        }
                        startActivity(new Intent(ChangeFoodActivity.this, RestaurantProfile.class));
                        finish();
                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError){

                    }
                });
            }
        });
    }
}

