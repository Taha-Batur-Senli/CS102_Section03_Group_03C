package android.example.fireapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
 *
 *@date 27.05.2020
 *@author Group 3C
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list;
    private Context context;
    ImageView delete, edit;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String food;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;

        database = FirebaseDatabase.getInstance();
        reference = database.getReference( "Restaurants");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listrow_edit_menu, null);
        }



        //Handle TextView and display string from your list
        TextView tvContact= (TextView)view.findViewById(R.id.itemtv_listrow_edit_menu);
        tvContact.setText(list.get(position));

        //Handle buttons and add onClickListeners
        delete = view.findViewById(R.id.imageView23);
        edit = view.findViewById(R.id.imageView21);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( list.size() != 0 ) {
                    food = list.get(position);
                }
                int index = food.indexOf(",");
                String foodName = food.substring(0, index);
                final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Restaurants");
                mRef.child(user.getUid()).child("menu").orderByChild("name").equalTo(foodName).addValueEventListener(new ValueEventListener() {
                    int i = 0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (i < 1) {
                            mRef.child(user.getUid()).child("menu").child(dataSnapshot.getChildren().iterator().next().getKey())
                                    .removeValue();
                            i++;
                        }
                        Toast.makeText(MyCustomAdapter.this.context, "Dish Deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                String item = list.get(position);
                int indexOfName = item.indexOf(",");
                String name = item.substring(0,indexOfName);

                //passes the name of te food to the next activity
                Intent intent = new Intent(context, ChangeFoodActivity.class);
                intent.putExtra("NAME", name);
                context.startActivity( intent);
            }
        });

        return view;
    }
}
