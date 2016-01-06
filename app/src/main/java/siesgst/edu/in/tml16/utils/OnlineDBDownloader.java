package siesgst.edu.in.tml16.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private JSONObject object;
    final String link = "http://development.siesgst.ac.in/test.php";
    public OnlineDBDownloader() {

    }

    public void downloadData() {
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.connect();
            System.out.print((object = new JSONObject(convertStreamToString(conn.getInputStream()))).getJSONArray("events"));
            JSON = object.getJSONArray("events");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Reads an InputStream and converts it to a String.
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
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
