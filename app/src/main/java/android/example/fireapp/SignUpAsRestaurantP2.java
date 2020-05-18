package android.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class SignUpAsRestaurantP2 extends AppCompatActivity {
    Button register;
    EditText etNumOfTables, etMinPrice, etMaxDuration;
    TextView tvGenre;
    ListView lvGenre;
    ArrayAdapter myAdapter;
    ArrayList<String> allGenre = new ArrayList<String>();

    FirebaseAuth mAuth;
    DatabaseReference mRef, mRefSeatPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_restaurant_p2);


        //Initialize
        register = (Button)findViewById(R.id.btnRegisterSignUpAsRest2);
        etNumOfTables = (EditText)findViewById(R.id.etNumOfTables2);
        etMinPrice = (EditText)findViewById(R.id.etMinPriceSignUp2);
        etMaxDuration = (EditText)findViewById(R.id.etMaxDurationSignUp2);
        tvGenre = (TextView)findViewById(R.id.tvSelectGenre);
        lvGenre = (ListView)findViewById(R.id.lvGenreSelection);
        myAdapter = new ArrayAdapter<String>(this, R.layout.listrow, R.id.textView2, allGenre);
        lvGenre.setAdapter(myAdapter);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference( "Restaurants");
        mRefSeatPlans = FirebaseDatabase.getInstance().getReference("SeatPlans");

        Intent i = getIntent();
        final String name = i.getStringExtra("NAME");
        final String phone = i.getStringExtra("PHONE");
        final String password = i.getStringExtra("PASSWORD");
        final String email = i.getStringExtra("EMAIL");


        //GENRE ListView
        allGenre.add("Pizza");
        allGenre.add("Hamburger");
        allGenre.add("Sushi");
        allGenre.add("Dessert");
        allGenre.add("Steak");
        allGenre.add("Chicken");
        myAdapter.notifyDataSetChanged();
        lvGenre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = allGenre.get(position);
                tvGenre.setText( item );
            }
        });

       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               registerRestaurant(name, email, password, phone);
           }
       });
    }

    private void registerRestaurant(final String name, final String email, final String password,final String phone) {

        //final String genre = spinner.getSelectedItem().toString();
        final String numOfTables = etNumOfTables.getText().toString();
        final String maxDuration = etMaxDuration.getText().toString();
        final String minPrice = etMinPrice.getText().toString();
        final String genre = tvGenre.getText().toString();

        if (numOfTables.isEmpty()) {
            etNumOfTables.setError("Enter numbers of tables you have!");
            etNumOfTables.requestFocus();
            return;
        }

        if ( maxDuration.isEmpty()){
            etMaxDuration.setError("Enter the average duration of seating of your customers!");
            etMaxDuration.requestFocus();
            return;
        }

        if ( minPrice.isEmpty()){
            etMinPrice.setError("Enter the minimum price for your customers to pre-order!");
            etMinPrice.requestFocus();
            return;
        }

        if (genre.equals("Please select a genre to your restaurant")){
            tvGenre.setError("Select a genre first");
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();

                    //Get user's info
                    String email = user.getEmail();
                    String uid = user.getUid();

                    String genre = tvGenre.getText().toString();

                    //Setting Firebase realtime database
                    mRef.child(uid).setValue(new Restaurant(name, email, genre, phone, uid,
                            Integer.parseInt(maxDuration),Integer.parseInt( minPrice)));

                    //add num of seats
                    HashMap<String, HashMap<String, HashMap>> seats = new HashMap();
                    String day = LocalDate.now().toString();
                    String seat = "";
                    HashMap<String, HashMap> days;
                    for (int i = 0; i < Integer.parseInt(numOfTables); i++) {

                        seat = "seat" + (i + 1);

                        //add 7 days
                        days = new HashMap();
                        for (int k = 0; k < 7; k++) {

                            if (k > 0) {
                                day = LocalDate.now().plusDays(k).toString();
                            }

                            System.out.println((k + 1) + ". day : " + day);

                            //add timeslots
                            SeatCalendar sc = new SeatCalendar(LocalDate.parse(day),
                                    LocalTime.of(0, 5), LocalTime.of(23, 0));
                            days.put(day, sc);
                        }
                        seats.put(seat, days);
                    }
                    mRefSeatPlans.child(uid).setValue(seats);


                    Toast.makeText(SignUpAsRestaurantP2.this, "Welcome " + name, Toast.LENGTH_SHORT);
                    startActivity(new Intent(SignUpAsRestaurantP2.this, RestaurantProfile.class));
                    finish();

                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "This email has an account!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
