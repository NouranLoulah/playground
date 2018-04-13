package com.example.nouran.playground.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.nouran.playground.Adapters.RecyclerViewAdapter;
import com.example.nouran.playground.Models.playdata;
import com.example.nouran.playground.R;
import com.example.nouran.playground.Services.Services;
import com.example.nouran.playground.Services.playgroundAPI;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<playdata> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_recycle();
        sendrequest();

        Intent i = getIntent();

        String id = i.getStringExtra("id");
        Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra("id",id);


//        Toast.makeText(MainActivity.this, id + "", Toast.LENGTH_SHORT).show();


    }

    public void get_recycle() {
        recyclerView = findViewById(R.id.recycleview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        data = new ArrayList<>();
        adapter = new RecyclerViewAdapter(data, this);
        recyclerView.setAdapter(adapter);

    }

    public void sendrequest() {
        Services services = playgroundAPI.getclient().create(Services.class);

        Call<List<playdata>> call = services.playground();
        call.enqueue(new Callback<List<playdata>>() {
            @Override
            public void onResponse(Call<List<playdata>> call, Response<List<playdata>> response) {
                if (response.isSuccessful()) {
                    data.clear();
                    data.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<playdata>> call, Throwable t) {

            }
        });


    }



}
