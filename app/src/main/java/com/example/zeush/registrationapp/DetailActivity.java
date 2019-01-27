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

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameUpdate,emailUpdate;
    Button updateInfo,changePassword;
    public  String password;
    public String name;
    public String email;
    public String api_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nameUpdate=findViewById(R.id.nameUpdate);
        emailUpdate=findViewById(R.id.emailUpdate);
        updateInfo=findViewById(R.id.updateInfo);
        changePassword=findViewById(R.id.changePassword);
        updateInfo.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        Intent i = getIntent();
         name=i.getStringExtra("name");
        email=i.getStringExtra("email");
        password=i.getStringExtra("password");
         api_token=i.getStringExtra("api_token");
        nameUpdate.setText(name);
        emailUpdate.setText(email);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.updateInfo)
        {
                if(!nameUpdate.getText().toString().isEmpty()&&!emailUpdate.getText().toString().isEmpty())
                {
                    new UpdateApi(this).execute(api_token,nameUpdate.getText().toString(),emailUpdate.getText().toString());
                }
        }
        else if(view.getId()==R.id.changePassword)
        {
            Intent i = new Intent(this,ChangePasswordActivity.class);
            i.putExtra("API_token",api_token);
            startActivity(i);
        }
    }

    public class UpdateApi extends AsyncTask<String,Void,ArrayList<String>>
    {
        Context context;
        public UpdateApi(Context context)
        {
            this.context=context;
        }
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }
            URL updateUrl=null;
            updateUrl= NetworkUtil.buildUrlForUpdate();
            RequestBody formBody = new FormBody.Builder()
                    .add("api_token",strings[0])
                    .add("name", strings[1])
                    .add("email", strings[2])
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
                Toast.makeText(context,"Info Updated successfully",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,LoginActivity.class);
                startActivity(i);


            }

        }
    }
}
