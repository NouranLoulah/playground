package com.example.nouran.playground.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nouran.playground.Adapters.RecyclerViewAdapter;
import com.example.nouran.playground.Models.Model;
import com.example.nouran.playground.R;
import com.example.nouran.playground.Services.Services;
import com.example.nouran.playground.Services.playgroundAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    ArrayList<Model> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycleview);

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        data=new ArrayList<>();
        adapter =new RecyclerViewAdapter(data,this);
        recyclerView.setAdapter(adapter);



        Services services= playgroundAPI.getclient().create(Services.class);

        Call<List<Model>> call=  services.playground();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                if(response.isSuccessful()){
                    data.clear();
                    data.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {

            }
        });


    }

}
