package com.example.nouran.playground.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nouran.playground.Models.Model;
import com.example.nouran.playground.Models.galler;
import com.example.nouran.playground.R;

public class DetailsActivity extends AppCompatActivity {
    ImageView image;
    TextView name, address, phone, capacity, cost, info;
    Button mapBT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        image = findViewById(R.id.imagedt);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        capacity = findViewById(R.id.capacity);
        cost = findViewById(R.id.cost);
        info = findViewById(R.id.info);
        mapBT = findViewById(R.id.mapBT);

        final Model x = (Model) getIntent().getSerializableExtra("data");
        final double lat = x.getPlayground_google_lat();
        final double lng = x.getPlayground_google_lng();


        final String informat=x.getPlayground_id_pk();
//

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailsActivity.this,GalleryActivity.class);
                intent.putExtra("info",informat);
                startActivity(intent);


            }
        });

        mapBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsActivity.this, MapsActivity.class);
                i.putExtra("latitude", lng);
                i.putExtra("longitude", lat);
                startActivity(i);

            }
        });


        Glide.with(this).
                load(x.getImage_name()).
                into(image);
        name.setText("الاسم: " + x.getPlayground_name());
        address.setText("Address : " + x.getPlayground_address());
        cost.setText("cost : " + x.getPlayground_cost());
        capacity.setText("capacity : " + x.getPlayground_capacity());
        phone.setText("phone : " + x.getPlayground_phone());


    }

}
