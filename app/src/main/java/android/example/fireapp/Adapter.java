package android.example.fireapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * This is an adapter class for customizing the list views.
 * Date: 27.05.2020
 * Author: Group 3C
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ImageViewHolder> {

    //properties

    private Context mContext;
    private List<Upload> uploadList;

    //constructors

    public Adapter (Context mContext, List<Upload> uploadList){
        this.mContext = mContext;
        this.uploadList = uploadList;
    }

    //methods

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.my_row_picture,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload current = uploadList.get(position);
        holder.textViewName.setText(current.getmName());
        Picasso.with(mContext)
                .load(current.getmImageURL())
                .fit()
                .centerCrop()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_uploaded);
        }
    }
}
