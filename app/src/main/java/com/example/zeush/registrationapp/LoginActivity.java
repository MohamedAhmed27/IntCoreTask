package com.example.zeush.registrationapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zeush.registrationapp.JsonUtil.JsonParsing;
import com.example.zeush.registrationapp.Model.User;
import com.example.zeush.registrationapp.NetworkUtil.NetworkUtil;

import java.net.URL;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userName,password;
    Button login,signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName=findViewById(R.id.name);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        signUp=findViewById(R.id.signUp);
        login.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.login)
        {
            if(!userName.getText().toString().isEmpty()&&!password.getText().toString().isEmpty()) {
                new LoginAPI(this).execute(userName.getText().toString(), password.getText().toString());
            }
            else
            {
                Toast.makeText(this,"Empty Fields Found",Toast.LENGTH_SHORT).show();
            }
        }
        else if(view.getId()==R.id.signUp)
        {
            Intent i =new Intent(this,RegisterActivity.class);
            startActivity(i);

        }

    }

    public class LoginAPI extends AsyncTask<String,Void,User>
    {
        Context context;

        public LoginAPI(Context context)
        {
            this.context=context;
        }

        @Override
        protected User doInBackground(String... strings) {
            URL url= NetworkUtil.buildUrlForLogin();
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("name", strings[0])
                    .add("password", strings[1])
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .post(formBody)
                    .build();


            try {
                Response response=client.newCall(request).execute();


                String jsonData = response.body().string();

                User user=null;
                user=JsonParsing.ParseResponseFromLogin(jsonData);
                user.setPassword(strings[1]);
                return user;

            } catch (Exception e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(User user) {


            if(user==null)
            {

                Toast.makeText(context,"Wrong Credentials",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(context,"logged in successfully",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,DetailActivity.class);
                i.putExtra("name",user.getName());
                i.putExtra("email",user.getEmail());
                i.putExtra("phone",user.getPhone());
                i.putExtra("password",user.getPassword());
                i.putExtra("api_token",user.getApi_token());
                startActivity(i);


            }



        }
    }



}
