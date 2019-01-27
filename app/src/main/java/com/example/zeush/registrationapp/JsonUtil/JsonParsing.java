package com.example.zeush.registrationapp.JsonUtil;

import com.example.zeush.registrationapp.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zeush on 1/26/2019.
 */

public class JsonParsing {
    public static final String TAG_ERROR="errors";
    public static final String TAG_MESSAGE="message";
    public static final String TAG_USER="user";
    public static ArrayList<String> ParseResponseFromRegister(String JSON) throws JSONException {


        ArrayList<String>errors =new ArrayList<>();
        JSONObject main=new JSONObject(JSON);
        JSONArray errosJson=main.getJSONArray(TAG_ERROR);

        for(int i=0;i<errosJson.length();i++)
        {
            JSONObject object=errosJson.getJSONObject(i);errors.add(object.optString(TAG_MESSAGE));
        }

        return errors;
    }

    public static User ParseResponseFromLogin(String JSON) throws JSONException {
        User user;

        JSONObject main=new JSONObject(JSON);

        JSONObject userObject=main.getJSONObject(TAG_USER);
        user=new User(userObject.optString("name"),userObject.optString("email"),userObject.optString("phone"),userObject.optString("api_token"),null);


        return user;


    }
}
