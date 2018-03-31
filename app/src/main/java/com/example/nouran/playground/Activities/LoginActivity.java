package com.example.nouran.playground.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nouran.playground.R;
import com.example.nouran.playground.Services.Services;
import com.example.nouran.playground.Models.login;
import com.example.nouran.playground.Services.playgroundAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText txtname, txtpassword;
    TextView register;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtname = findViewById(R.id.name);
        txtpassword = findViewById(R.id.password);
        register=findViewById(R.id.txtregister);
        login = findViewById(R.id.loginBT);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);



            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if(validate()==false){
                  Toast.makeText(LoginActivity.this, "you don't have account", Toast.LENGTH_LONG).show();



              }
              else {
                  sendrequest();



                  }
              }
            });

    }
    public boolean validate(){
        boolean valid =true;
        String name = txtname.getText().toString();
        String pass = txtpassword.getText().toString();

        if (pass.isEmpty() ){

            txtpassword.setError("enter Password");
            valid=false;
        }else {
            txtpassword.setError(null);
        }
        if (name.isEmpty() ) {
            txtname.setError("enter user_name");
            valid=false;
        }
        return valid;
    }

    public void sendrequest() {

        String user_name = txtname.getText().toString();
        String pass = txtpassword.getText().toString();
        Services services = playgroundAPI.getclient().create(Services.class);
       Call<com.example.nouran.playground.Models.login> call=services.userLogIn(user_name, pass);
       call.enqueue(new Callback<login>() {
            @Override
            public void onResponse(Call<login> call, Response<login> response) {

                if (response.isSuccessful()) {

//                    Toast.makeText(LoginActivity.this, "mmm", Toast.LENGTH_SHORT).show();
                    if (response.body().getSuccess()==1) {

//                            Toast.makeText(LoginActivity.this, "register success", Toast.LENGTH_LONG).show();

                        Intent i= new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);

                        }

                    else {

                        Toast.makeText(LoginActivity.this, "you don't have account", Toast.LENGTH_LONG).show();


                    }
                }

            }

            @Override
            public void onFailure(Call<login> call, Throwable t) {


                Toast.makeText(LoginActivity.this, "Unable to submit post to API." + t.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
