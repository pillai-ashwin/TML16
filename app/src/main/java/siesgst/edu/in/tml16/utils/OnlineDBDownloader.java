package siesgst.edu.in.tml16.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vishal on 30/12/15.
 */
public class OnlineDBDownloader {

    private JSONArray JSON;
    final String link = "";

    public OnlineDBDownloader() {

    }

    public void downloadData() throws IOException, JSONException {
        InputStream is = null;

        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("TML", "The response is: " + response);
            is = conn.getInputStream();

            JSON = new JSONArray(readIt(is));
        } finally {
            if (is != null) {
                is.close();
            }

        }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[50000];
        reader.read(buffer);
        return new String(buffer);
    }

    public JSONArray getJSON() {
        return JSON;
    }
}
