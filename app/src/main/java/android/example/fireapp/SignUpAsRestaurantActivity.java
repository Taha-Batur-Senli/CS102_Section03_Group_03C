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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;

/*
This class enables restaurant owners to sign up to our app. Their info are stored both on authentic
firebase and realtime database for different purposes.
 */
public class SignUpAsRestaurantActivity extends AppCompatActivity{
    //Properties
    EditText etEmail, etPassword, etName, etPhone;//, etNumOfTables;
    Button next;
    private FirebaseAuth mAuth;
    //ProgressBar progressBar;
    //Button btnRegister;
    //Spinner spinner;
    FirebaseDatabase database;
    DatabaseReference mRef;
    DatabaseReference mRefSeatPlans;
    DatabaseReference mRef2;

    /*<Button
        android:id="@+id/btnRegisterSignUpAsRest"
        android:layout_width="159dp"
        android:layout_height="51dp"
        android:background="@drawable/button"
        android:fontFamily="@font/roboto_light"
        android:text="Sign up"
        android:textAllCaps="false"
        android:textColor="@color/white"
        android:textSize="14sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.814" />

         <Spinner
        android:id="@+id/spinnerGenre"
        android:layout_width="210dp"
        android:layout_height="41dp"
        android:background="@drawable/button"
        android:entries="@array/genres"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.497"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.681" />

        <ProgressBar
        android:id="@+id/progressBarResSignUp"
        style="?android:attr/progressBarStyle"
        android:visibility="gone"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="@+id/textView14"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
 <EditText
        android:id="@+id/etNumOfTablesSignUp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:ems="10"
        android:hint=" Table Number"
        android:inputType="number"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.489"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btnRegisterSignUpAsRest"
        app:layout_constraintVertical_bias="0.0" />
  <ImageView
        android:id="@+id/imageView7"
        android:layout_width="12dp"
        android:layout_height="16dp"
        app:layout_constraintBottom_toBottomOf="@+id/spinnerGenre"
        app:layout_constraintEnd_toEndOf="@+id/spinnerGenre"
        app:layout_constraintHorizontal_bias="0.9"
        app:layout_constraintStart_toStartOf="@+id/spinnerGenre"
        app:layout_constraintTop_toTopOf="@+id/spinnerGenre"
        app:layout_constraintVertical_bias="0.55"
        app:srcCompat="@drawable/polygon" />
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_as_restaurant);

        //Initialize
        etEmail = findViewById(R.id.etEmailSignUpAsRest);
        etPassword = findViewById(R.id.etPasswordSignUpAsRest);
        etName = findViewById(R.id.etNameResSignUp);
        etPhone = findViewById(R.id.etPhoneResSignUp);
        next = (Button)findViewById(R.id.btnNextSignUpAsRest);
        //etNumOfTables = findViewById(R.id.etNumOfTablesSignUp);
        //progressBar = (ProgressBar)findViewById(R.id.progressBarResSignUp);
        //spinner = findViewById(R.id.spinnerGenre);
        //btnRegister = findViewById(R.id.btnRegisterSignUpAsRest);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference( "Restaurants");
        mRefSeatPlans = database.getReference("SeatPlans");
        mRef2 = database.getReference("Best Restaurants");

        etEmail.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPassword.setTextColor(ContextCompat.getColor(this, R.color.white));
        etName.setTextColor(ContextCompat.getColor(this, R.color.white));
        etPhone.setTextColor(ContextCompat.getColor(this, R.color.white));

        /*btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRestaurant();
            }
        });*/

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                final String name = etName.getText().toString();
                final String phone = etPhone.getText().toString();

                if( name.isEmpty()){
                    etName.setError("Enter a name!");
                    etName.requestFocus();
                    return;
                }

                if( email.isEmpty()){
                    etEmail.setError("Enter an email!");
                    etEmail.requestFocus();
                    return;
                }

                if( phone.isEmpty()){
                    etPhone.setError("Enter a phone!");
                    etPhone.requestFocus();
                    return;
                }

                if( password.isEmpty()){
                    etPassword.setError("Enter a password!");
                    etPassword.requestFocus();
                    return;
                }

                if ( password.length() < 6){
                    etPassword.setError("Password must be at least 6 characters!");
                    etPassword.requestFocus();
                    return;
                }

                Intent i = new Intent(SignUpAsRestaurantActivity.this, SignUpAsRestaurantP2.class);
                i.putExtra("NAME", name);
                i.putExtra("EMAIL", email);
                i.putExtra("PASSWORD", password);
                i.putExtra("PHONE", phone);
                startActivity(i);
            }
        });

    }

    //METHODS
   /* private void registerRestaurant() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        final String name = etName.getText().toString();
        final String phone = etPhone.getText().toString();
        final String genre = spinner.getSelectedItem().toString();
        final String numOfTables = etNumOfTables.getText().toString();

        final int minPrice = 25;
        final int maxDuration = 90;

        if (numOfTables.isEmpty()){
            etNumOfTables.setError("Enter numbers of tables you have");
            etNumOfTables.requestFocus();
            return;
        }

        if( email.isEmpty()){
            etEmail.setError("Enter an email!");
            etEmail.requestFocus();
            return;
        }

        if( name.isEmpty()){
            etName.setError("Enter a name!");
            etName.requestFocus();
            return;
        }

        if( phone.isEmpty()){
            etPhone.setError("Enter a phone!");
            etPhone.requestFocus();
            return;
        }

        if( password.isEmpty()){
            etPassword.setError("Enter a password!");
            etPassword.requestFocus();
            return;
        }

        if ( password.length() < 6){
            etPassword.setError("Password must be at least 6 characters!");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if( task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();

                    //Get user's info
                    String email = user.getEmail();
                    String uid = user.getUid();

                    //Setting Firebase realtime database
                    mRef.child(uid).setValue(new Restaurant(name, email, genre, phone, uid));
                    //mRef2.child(uid).setValue(new Restaurant(name, email, genre, phone, uid));

                    //add num of seats
                    HashMap<String, HashMap<String, HashMap>> seats = new HashMap();
                    String day = LocalDate.now().toString();
                    String seat = "";
                    HashMap <String, HashMap> days;
                    for (int i = 0; i < Integer.parseInt(numOfTables); i++){

                        seat = "seat" + (i + 1);

                        //add 7 days
                        days  = new HashMap();
                        for (int k = 0; k < 7; k++){

                            if (k > 0)
                            {
                                day = LocalDate.now().plusDays(k).toString();
                            }

                            System.out.println((k + 1) + ". day : " + day);
                            //add timeslots
                            SeatCalendar sc = new SeatCalendar(LocalDate.parse(day),
                                    LocalTime.of(0,5),LocalTime.of(23,0));
                            days.put(day, sc);
                        }
                        seats.put(seat, days);
                    }
                    mRefSeatPlans.child(uid).setValue(seats);


                    Toast.makeText( SignUpAsRestaurantActivity.this, "Welcome " + name, Toast.LENGTH_SHORT);
                    startActivity( new Intent(SignUpAsRestaurantActivity.this, RestaurantProfile.class));
                    finish();
                }
                else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "This email has an account!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }*/
}
