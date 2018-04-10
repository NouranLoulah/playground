package com.example.nouran.playground.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nouran.playground.Activities.DetailsActivity;
import com.example.nouran.playground.Activities.LoginActivity;
import com.example.nouran.playground.Models.login;
import com.example.nouran.playground.Models.playdata;
import com.example.nouran.playground.R;

import java.util.ArrayList;

/**
 * Created by Nouran on 3/25/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<playdata> contents;
    Context context;

    public RecyclerViewAdapter(ArrayList<playdata> contents , Context context ) {
        this.contents = contents;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final playdata playground = contents.get(position);
//        final double lat= playground.getPlayground_google_lat();
//        final double lng =playground.getPlayground_google_lng();
        holder.text.setText(playground.getPlayground_name());
        Glide.with(context.getApplicationContext())
                .load(contents.get(position).getImage_name())
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(context,DetailsActivity.class);
                i.putExtra("data",playground);

//                i.putExtra("latitude",lat);
//                i.putExtra("longitude",lng);
                context.startActivity(i);

            }
        });





    }

    @Override
    public int getItemCount() {
        return contents.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
        }
    }
}
