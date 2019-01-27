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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText oldPassword;
    EditText newPassword;
    Button changePassword;
    public String API_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldPassword=findViewById(R.id.oldPassword);
        newPassword=findViewById(R.id.newPassword);
        changePassword=findViewById(R.id.Changepassword);
        changePassword.setOnClickListener(this);
        Intent i =getIntent();

        API_token=i.getStringExtra("API_token");
    }

    @Override
    public void onClick(View view) {
            if(!oldPassword.getText().toString().isEmpty()&&!newPassword.getText().toString().isEmpty())
            {
                    new UpdatePasswordApi(this).execute(API_token, newPassword.getText().toString(), oldPassword.getText().toString());
                }


            else
            {
                Toast.makeText(this,"Empty Fields Found",Toast.LENGTH_SHORT).show();
            }
    }


    public class UpdatePasswordApi extends AsyncTask<String,Void,ArrayList<String>>
    {
        Context context;
        public UpdatePasswordApi(Context context)
        {
            this.context=context;
        }
        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            URL updateUrl=null;
            updateUrl= NetworkUtil.buildUrlForChangePassword();
            RequestBody formBody = new FormBody.Builder()
                    .add("api_token",strings[0])
                    .add("new_password", strings[1])
                    .add("old_password", strings[2])
                    .build();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(updateUrl)
                    .addHeader("Accept", "application/json")
                    .patch(formBody)
                    .build();

            ArrayList <String> error=new ArrayList<>();
            Response response= null;
            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                Log.v("yaw",jsonData);
                error= JsonParsing.ParseResponseFromRegister(jsonData);

                return error;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> errors) {

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
                Toast.makeText(context,"Password Changed successfully",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,LoginActivity.class);
                startActivity(i);


            }

        }
    }
}
