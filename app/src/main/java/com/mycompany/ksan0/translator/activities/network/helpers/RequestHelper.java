package com.mycompany.ksan0.translator.activities.network.helpers;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHelper {
    public static String makeEmptyGetRequest(String url) {
        HttpURLConnection connection = null;

        try {
            String response = "";
            URL requestURL = new URL(url);
            connection = (HttpURLConnection) requestURL.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            byte buffer[] = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                response += new String(buffer, 0, read);
            }

            return response;
        } catch (Exception e) {
            return null;
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
