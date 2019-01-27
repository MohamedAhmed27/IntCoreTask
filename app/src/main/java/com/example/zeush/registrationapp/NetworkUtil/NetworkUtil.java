package com.example.zeush.registrationapp.NetworkUtil;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by zeush on 1/26/2019.
 */

public class NetworkUtil {

    private static final String BASE_INTCORE_URL =" https://internship-api-v0.7.intcore.net";


    public static URL buildUrlForChangePassword() {
        Uri builtUri = Uri.parse(BASE_INTCORE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("user")
                .appendPath("auth")
                .appendPath("update-password")
                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("yaw", "Built URI " + url);

        return url;
    }

    public static URL buildUrlForUpdate() {
        Uri builtUri = Uri.parse(BASE_INTCORE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("user")
                .appendPath("auth")
                .appendPath("update-profile")

                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("yaw", "Built URI " + url);

        return url;
    }
    public static URL buildUrlForLogin() {
        Uri builtUri = Uri.parse(BASE_INTCORE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("user")
                .appendPath("auth")
                .appendPath("signin")
                .build();


        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("yaw", "Built URI " + url);

        return url;
    }




    public static URL buildUrlForRegister() {
        Uri builtUri = Uri.parse(BASE_INTCORE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("user")
                .appendPath("auth")
                .appendPath("signup")

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("yaw", "Built URI " + url);

        return url;
    }




    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
