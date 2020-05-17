package android.example.fireapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list;
    private Context context;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;


    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;

        database = FirebaseDatabase.getInstance();
        reference = database.getReference( "Restaurants");
        mAuth = FirebaseAuth.getInstance();
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
        ImageView delete = view.findViewById(R.id.imageView23);
        ImageView edit = view.findViewById(R.id.imageView21);

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //do something
            }
        });
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something

                notifyDataSetChanged();
            }
        });

        return view;
    }
}
