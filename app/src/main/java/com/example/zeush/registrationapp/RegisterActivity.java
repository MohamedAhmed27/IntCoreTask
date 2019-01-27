package com.example.zeush.registrationapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zeush.registrationapp.JsonUtil.JsonParsing;
import com.example.zeush.registrationapp.NetworkUtil.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name,phone,password,email;
    Button signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.userNameReg);
        phone=findViewById(R.id.phoneNo);
        password=findViewById(R.id.passwordReg);
        email=findViewById(R.id.emailReg);
        signUpBtn=findViewById(R.id.signupBtn);
        signUpBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(!name.getText().toString().isEmpty()&&!phone.getText().toString().isEmpty()&&!password.getText().toString().isEmpty()&&!email.getText().toString().isEmpty()) {
            new RegisterAPI(this).execute(name.getText().toString(), phone.getText().toString(), password.getText().toString(), email.getText().toString());
        }
        else
        {
            Toast.makeText(this,"Empty fields found",Toast.LENGTH_SHORT).show();
        }

    }

    public class RegisterAPI extends AsyncTask<String,Void,ArrayList<String>>
    {
        Context context;

        public RegisterAPI(Context context)
        {
            this.context=context;
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            URL url= NetworkUtil.buildUrlForRegister();
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("name", strings[0])
                    .add("phone", strings[1])
                    .add("password", strings[2])
                    .add("email", strings[3])
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
                ArrayList<String>errros=new ArrayList<>();
                errros= JsonParsing.ParseResponseFromRegister(jsonData);
                return errros;

            } catch (Exception e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String>errors) {


            if(errors!=null&&errors.size()>0)
            {
                String error="";
                for(int i=0;i<errors.size();i++)
                {
                    error+=errors.get(i)+" ";
                }
                Toast.makeText(context,error,Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(context,"Register successfully",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,LoginActivity.class);
                startActivity(i);


            }



        }
    }

}
