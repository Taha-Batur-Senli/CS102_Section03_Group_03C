package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
 * This class enables customers to edit their account. They can change their names and phone.
 * Their changes are also updated on firebase.
 *@date 27.05.2020
 *@author Group 3C
 */
public class EditCustomerAccountActivity extends AppCompatActivity {
    //Properities
    Button save;
    EditText phone, name;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_customer_account);

        //Initialize
        save = (Button)findViewById(R.id.btnSaveCustomerAccount);
        phone = (EditText)findViewById(R.id.etNewPhoneCustomer);
        name = (EditText)findViewById(R.id.etNewNameCustomer);

        phone.setTextColor(ContextCompat.getColor(this, R.color.white));
        name.setTextColor(ContextCompat.getColor(this, R.color.white));

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Customers");
        user = mAuth.getCurrentUser();

        //Make 'save' button functional.
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nameUpdate = name.getText().toString();
                final String phoneUpdate = phone.getText().toString();

                //If and only if name edit text is not empty, customers data is edited.
                if(!nameUpdate.isEmpty())
                    mRef.child(user.getUid()).child("name").setValue(nameUpdate);

                //If and only if phone edit text is not empty, customers data is edited.
                if(!phoneUpdate.isEmpty())
                    mRef.child(user.getUid()).child("phone").setValue(phoneUpdate);

                startActivity( new Intent(EditCustomerAccountActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
