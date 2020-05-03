package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String[] data1, data2;
    Context context;


    public MyAdapter(Context context, String[] s1, String[] s2){ //s1 == dishes and s2 == indexes of food
        context = context;
        data1= s1;
        data2 = s2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt1.setText(data1[position]);
        holder.txt2.setText(data2[position]);

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;
        ImageView view1, view2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textView3);
            txt2 = itemView.findViewById(R.id.textView4);
            view1 = itemView.findViewById(R.id.imageView);
            view2 = itemView.findViewById(R.id.imageView2);
        }
    }
}
