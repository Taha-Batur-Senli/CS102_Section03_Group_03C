    package android.example.fireapp;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.content.ContextCompat;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.view.WindowManager;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;

    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    /**
     * Changing the information of the food after pen image is pressed in the change menu.
     * @date 04.05.2020
     * @author Group_g3C
     */

    public class ChangeFoodActivity extends AppCompatActivity {

        //Properties

        Button save;
        EditText etName, etIngredients, etPrice;
        TextView nameTV;
        FirebaseAuth mAuth;
        FirebaseUser user;
        DatabaseReference mRef;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            //making the activity full screen

            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_change_food);

            //Initialization

            etName = (EditText)findViewById(R.id.etFoodNameChange);
            etIngredients = (EditText)findViewById(R.id.etFoodIngredientChange);
            etPrice = (EditText)findViewById(R.id.etFoodPriceChange);
            save = (Button)findViewById(R.id.bttnSaveFoodChange);

            etName.setTextColor(ContextCompat.getColor(this, R.color.white));
            etIngredients.setTextColor(ContextCompat.getColor(this, R.color.white));
            etPrice.setTextColor(ContextCompat.getColor(this, R.color.white));

            nameTV = (TextView)findViewById(R.id.txtFoodNameChange);

            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            mRef = FirebaseDatabase.getInstance().getReference("Restaurants");

            //Get data from previous activity
            Intent intent = getIntent();
            String name = intent.getStringExtra("NAME");
            nameTV.setText("Edit: " + name);

            //Methods called
            saveChangesAction();

        }

        //METHODS

        /**
        This method saves the changes made on dish on firebase. If user wants to change only one attribute,
        other properties are not effected despite the empty remaining edit texts.
         */
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
                            //Saves changes of name only if it is not empty
                            if ( i < 1 && !nameUpdate.isEmpty()) {
                                mRef.child(user.getUid()).child("menu").child(dataSnapshot.getChildren().iterator().next().getKey())
                                        .child("name").setValue(nameUpdate);
                                i++;
                            }

                            //Saves changes of ingredients only if it is not empty
                            if (j < 1 && !ingredientsUpdate.isEmpty()) {
                              mRef.child(user.getUid()).child("menu").child(dataSnapshot.getChildren().iterator().next().getKey())
                                        .child("ingredients").setValue(ingredientsUpdate);
                                j++;
                            }

                            //Saves changes of price only if it is not empty
                            if (k < 1 && !priceUpdate.isEmpty()) {
                               mRef.child(user.getUid()).child("menu").child(dataSnapshot.getChildren().iterator().next().getKey())
                                       .child("price").setValue(priceUpdate);
                               k++;
                            }
                            startActivity(new Intent(ChangeFoodActivity.this, ChangeMenuActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError databaseError){

                        }
                    });
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

