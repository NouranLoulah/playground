package com.example.nouran.playground.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.nouran.playground.Adapters.galleryRecyclerAdapter;
import com.example.nouran.playground.Models.galler;
import com.example.nouran.playground.R;
import com.example.nouran.playground.Services.Services;
import com.example.nouran.playground.Services.playgroundAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryActivity extends AppCompatActivity {
    RecyclerView gallery_recycle;
    galleryRecyclerAdapter adapter;
    ArrayList<galler> gallary;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        GetDataFromIntent();
        initView();
        GetDataFromServer();
    }

    private void GetDataFromIntent() {

        Intent i = getIntent();
        if (!i.equals("")) {
            id = i.getStringExtra("info");
        }
    }

    private void initView() {
        gallery_recycle = findViewById(R.id.gallery_recycle);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(GalleryActivity.this,2);
        gallery_recycle.setLayoutManager(layoutManager);
        gallary = new ArrayList<>();
        adapter=new galleryRecyclerAdapter(gallary,this);
//        adapter = new galleryRecyclerAdapter(gallary, this);
        gallery_recycle.setAdapter(adapter);

    }


    private void GetDataFromServer() {
        Services services = playgroundAPI.getclient().create(Services.class);

        Call<List<galler>> call = services.gallery(id);
        call.enqueue(new Callback<List<galler>>() {
            @Override
            public void onResponse(Call<List<galler>> call, Response<List<galler>> response) {
                if (response.isSuccessful()) {
                    //  Toast.makeText(GalleryActivity.this, "id"+id, Toast.LENGTH_SHORT).show();
                    Toast.makeText(GalleryActivity.this, "" + response.body().get(0).getImg(), Toast.LENGTH_SHORT).show();
                  gallary.clear();
                    gallary.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<galler>> call, Throwable t) {


            }
        });

    }
}
