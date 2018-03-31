package com.example.nouran.playground.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.nouran.playground.Activities.DetailsActivity;
import com.example.nouran.playground.Activities.GalleryActivity;
import com.example.nouran.playground.Models.galler;
import com.example.nouran.playground.R;

import java.util.ArrayList;

/**
 * Created by Nouran on 3/31/2018.
 */

public class galleryRecyclerAdapter extends RecyclerView.Adapter<galleryRecyclerAdapter.ViewHolder> {

    ArrayList<galler> contents;
    Context context;

    public galleryRecyclerAdapter(ArrayList<galler> contents, Context context) {
        this.contents = contents;
        this.context = context;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gellaryitems, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(context.getApplicationContext())
                .load(contents.get(position).getImg())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {

        return contents.size();
    }

     static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

         ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);


        }
    }


}
