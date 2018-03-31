package com.example.nouran.playground.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nouran.playground.R;
import com.example.nouran.playground.Services.Services;
import com.example.nouran.playground.Models.login;
import com.example.nouran.playground.Services.playgroundAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
EditText nameED,passwordED,emailED,mobileED;
Button RegisterBT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameED = findViewById(R.id.name);
        passwordED = findViewById(R.id.password);
        emailED = findViewById(R.id.email);
        mobileED = findViewById(R.id.mobile);
        RegisterBT = findViewById(R.id.registerBT);
        RegisterBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validain()==false){
                    Toast.makeText(RegisterActivity.this, "failed register", Toast.LENGTH_SHORT).show();

                }else {
                    sendrequest();
                }


            }
        });


    }
    public  boolean validain(){
           boolean valid=true;
        String name= nameED.getText().toString().trim();
        String email=emailED.getText().toString().trim();
        String pass=passwordED.getText().toString().trim();
        String mobile=mobileED.getText().toString().trim();
        if (pass.length()<8 ){
            passwordED.setError("Password");
            valid=false;
        }else {
            passwordED.setError(null);
        }
        if(name.isEmpty()){
            nameED.setError("user_name");
            valid=false;
        }
        if(mobile.isEmpty()) {
            mobileED.setError("Mobile");
            valid=false;

        }
        if (email.isEmpty()){
            emailED.setError("Email");
            valid=false;
        }




            return valid;
    }


    public void sendrequest(){

        final String name= nameED.getText().toString();
        final String email=emailED.getText().toString();
        String pass=passwordED.getText().toString();
        String mobile=mobileED.getText().toString();

        Services services= playgroundAPI.getclient().create(Services.class);
        services.userSignUp(name,email,pass,mobile).enqueue(new Callback<login>() {
            @Override
            public void onResponse(Call<login> call, Response<login> response) {
                if(response.isSuccessful()){
                    if(response.body().getSuccess()==1) {


                        Toast.makeText(RegisterActivity.this, "register success", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                    }else {

                        Toast.makeText(RegisterActivity.this, "register failed", Toast.LENGTH_LONG).show();


                    }
                }

            }

            @Override
            public void onFailure(Call<login> call, Throwable t) {

                Toast.makeText(RegisterActivity.this,"Unable to submit post to API." + t.toString(),Toast.LENGTH_LONG).show();

            }
        });
    }
}
