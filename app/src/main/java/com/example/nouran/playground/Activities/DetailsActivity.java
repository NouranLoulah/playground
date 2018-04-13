package com.example.nouran.playground.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.ETC1;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nouran.playground.Models.MSG;
import com.example.nouran.playground.Models.login;
import com.example.nouran.playground.Models.playdata;
import com.example.nouran.playground.R;
import com.example.nouran.playground.Services.Services;
import com.example.nouran.playground.Services.playgroundAPI;
import com.example.nouran.playground.SharedPref;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    EditText ETdate;
    Spinner ETtime;
    ImageView image;
    TextView name, address, phone, capacity, cost, info;
    Button mapBT, reserve, btnlogout;
    String spinner, hours;

    final Calendar mycalendar = Calendar.getInstance();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initia();
        listnerview();


    }


    public void listnerview() {
        btnlogout = findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref preferense = new SharedPref(DetailsActivity.this);
                preferense.clear();
                Intent i = new Intent(DetailsActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();


            }
        });


        //call to click of Edit_text to date
        ETdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepicker();
            }
        });

        // prevent keyboard to appear
        ETdate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = ETdate.getInputType(); // backup the input type
                ETdate.setInputType(InputType.TYPE_NULL); // disable soft input
                ETdate.onTouchEvent(event); // call native handler
                ETdate.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

// spinner to time of reservation
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ETtime.setAdapter(adapter);
        ETtime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner = ETtime.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


     /*  final TimePickerDialog.OnTimeSetListener time=new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker timePicker, int hour, int minute) {
               mycalendar.set(Calendar.HOUR,hour);
               mycalendar.set(Calendar.MINUTE,minute);
               ETtime.setText( hour + ":" + minute);


           }
       };

        ETtime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new TimePickerDialog(DetailsActivity.this,time,
                        mycalendar.get(Calendar.HOUR),mycalendar.get(Calendar.MINUTE),false).show();

            }
        });*/

    }

    public void datepicker() {
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DatePickerDialog datePickerDialog = new DatePickerDialog(DetailsActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar mycalendar = Calendar.getInstance();
                        mycalendar.set(year, monthOfYear, dayOfMonth);
                        String date = dateFormatter.format(mycalendar.getTime());


                        Date dateSpecified = mycalendar.getTime();
                        Date c = Calendar.getInstance().getTime();

                        if (dateSpecified.before(c)) {
                            AlertDialog alertDialog = new AlertDialog.Builder(DetailsActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Enter date after today");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            alertDialog.show();

                        } else {


                            ETdate.setText(date);


                        }


                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void initia() {
        image = findViewById(R.id.imagedt);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        capacity = findViewById(R.id.capacity);
        cost = findViewById(R.id.cost);
        info = findViewById(R.id.info);
        mapBT = findViewById(R.id.mapBT);
        ETdate = findViewById(R.id.date_reservation);
        ETtime = findViewById(R.id.time_reservation);

        reserve = findViewById(R.id.reserve);

        final playdata x = (playdata) getIntent().getSerializableExtra("data");
        final double lat = x.getPlayground_google_lat();
        final double lng = x.getPlayground_google_lng();


        final String ground_id = x.getPlayground_id_pk();
//

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, GalleryActivity.class);
                intent.putExtra("info", ground_id);
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


        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senddata();


            }
        });

    }

    public void senddata() {
        playdata d = (playdata) getIntent().getSerializableExtra("data");
        String ground_id = d.getPlayground_id_pk();
        String date_reservation = ETdate.getText().toString();
        String time_reservation = spinner;
        String user_id = LoginActivity.id;
        Toast.makeText(this, "" + user_id + " " + ground_id + date_reservation + time_reservation, Toast.LENGTH_SHORT).show();

        Services services = playgroundAPI.getclient().create(Services.class);
        Call<MSG> call = services.BookGroundAdmin(time_reservation, user_id, date_reservation, ground_id);
        call.enqueue(new Callback<MSG>() {

            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess() == 1) {

                        Toast.makeText(DetailsActivity.this, "in response", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "failed" + t, Toast.LENGTH_SHORT).show();
                Log.e("mmm", t.toString());
            }
        });


    }

}
