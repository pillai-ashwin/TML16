package siesgst.edu.in.tml16.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vishal on 30/12/15.
 */
public class OnlineDBDownloader {

    private JSONArray JSON;
    final String link = "http://api.androidhive.info/contacts/";

    public OnlineDBDownloader() {

    }

    public void downloadData() throws IOException, JSONException {
        InputStream is = null;

        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.connect();
        int response = conn.getResponseCode();
        Log.d("TML", "The response is: " + response);
        JSON = new JSONArray(convertStreamToString(conn.getInputStream()));
        if (is != null) {
            is.close();
        }
    }

    // Reads an InputStream and converts it to a String.
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public JSONArray getJSON() {
        return JSON;
    }
}
