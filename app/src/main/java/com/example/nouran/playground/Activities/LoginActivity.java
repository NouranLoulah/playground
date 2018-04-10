package com.example.nouran.playground.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nouran.playground.R;
import com.example.nouran.playground.Services.Services;
import com.example.nouran.playground.Models.login;
import com.example.nouran.playground.Services.playgroundAPI;
import com.example.nouran.playground.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText txtname, txtpassword;
    TextView register;
    Button login;
    static String id;
    public boolean isFirstStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initiaview();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Intro App Initialize SharedPreferences
                SharedPreferences getSharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                isFirstStart = getSharedPreferences.getBoolean("firstStart", true);

                //  Check either activity or app is open very first time or not and do action
                if (isFirstStart) {

                    //  Launch application introduction screen
                    Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
                    startActivity(i);
                    finish();
                    SharedPreferences.Editor e = getSharedPreferences.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });
        t.start();
//
        SharedPreferences sharedPreferences = getSharedPreferences("id", MODE_PRIVATE);
        String uid = sharedPreferences.getString("user_id", id);
//       if (!uid.isEmpty()) {
            SharedPref sharedPref = new SharedPref(this);
            sharedPref.createsharedpref("user_id");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("id", uid);


            startActivity(intent);
            finish();
        }
   // }

    public void initiaview() {

        txtname = findViewById(R.id.name);
        txtpassword = findViewById(R.id.password);
        register = findViewById(R.id.txtregister);
        login = findViewById(R.id.loginBT);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate() == false) {
                    Toast.makeText(LoginActivity.this, "you don't have account", Toast.LENGTH_LONG).show();


                } else {
                    sendrequest();

                }
            }
        });


    }

    public boolean validate() {
        boolean valid = true;
        String name = txtname.getText().toString();
        String pass = txtpassword.getText().toString();

        if (pass.isEmpty()) {

            txtpassword.setError("enter Password");
            valid = false;
        } else {
            txtpassword.setError(null);
        }
        if (name.isEmpty()) {
            txtname.setError("enter user_name");
            valid = false;
        }
        return valid;
    }

    public void sendrequest() {

        String user_name = txtname.getText().toString();
        String pass = txtpassword.getText().toString();
        Services services = playgroundAPI.getclient().create(Services.class);
        Call<com.example.nouran.playground.Models.login> call = services.userLogIn(user_name, pass);
        call.enqueue(new Callback<login>() {
            @Override
            public void onResponse(Call<login> call, Response<login> response) {

                if (response.isSuccessful()) {

//                    Toast.makeText(LoginActivity.this, "mmm", Toast.LENGTH_SHORT).show();
                    if (response.body().getSuccess() == 1) {
                        id = response.body().getId();
                        Toast.makeText(LoginActivity.this, ""+id, Toast.LENGTH_SHORT).show();
                        SharedPref sharedPref = new SharedPref(LoginActivity.this);
                        sharedPref.createsharedpref(id);
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);


                        i.putExtra("user_id", id);

                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();


                    } else {

                        Toast.makeText(LoginActivity.this, "you don't have account", Toast.LENGTH_LONG).show();


                    }
                }

            }

            @Override
            public void onFailure(Call<login> call, Throwable t) {


                Toast.makeText(LoginActivity.this, "Unable to submit post to API." , Toast.LENGTH_LONG).show();

            }
        });
        ///
    }


}
