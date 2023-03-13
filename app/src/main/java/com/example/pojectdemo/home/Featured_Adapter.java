package com.example.pojectdemo.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pojectdemo.R;

import java.util.ArrayList;

public class Featured_Adapter extends RecyclerView.Adapter<Featured_Adapter.FeaturedViewHolder> {
    ArrayList<featuredHelper> featuredLocations;
    public Featured_Adapter(ArrayList<featuredHelper> featuredLocations){
        this.featuredLocations = featuredLocations;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_design,parent,false);
        FeaturedViewHolder featuredViewHolder = new FeaturedViewHolder(view);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        featuredHelper fHelper = featuredLocations.get(position);
        holder.image.setImageResource(fHelper.getImage());
        holder.title.setText(fHelper.getTitle());
        holder.desc.setText(fHelper.getDescription());
    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }

    public static class FeaturedViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, desc;

        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.featured_image);
            title = itemView.findViewById(R.id.featured_name);
            desc = itemView.findViewById(R.id.featured_description);
        }
    }
}
