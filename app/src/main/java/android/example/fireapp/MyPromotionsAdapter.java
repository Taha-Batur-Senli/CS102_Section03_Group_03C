package android.example.fireapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPromotionsAdapter extends RecyclerView.Adapter<MyPromotionsAdapter.MyViewFolder> {

    ArrayList<String> data;
    int[] images;
    Context context;

    public MyPromotionsAdapter(Context ct, ArrayList<String> s1, int[] img){
        context = ct;
        data = s1;
        images = img;
    }

    @NonNull
    @Override
    public MyViewFolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_promotions_row, parent, false);
        return new MyViewFolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewFolder holder, int position) {
        holder.t1.setText(data.get(position));
        holder.imgView.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewFolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView t1;
        public MyViewFolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.MyTextView1);
            imgView = itemView.findViewById(R.id.promotionsImageView);
        }
    }

}

