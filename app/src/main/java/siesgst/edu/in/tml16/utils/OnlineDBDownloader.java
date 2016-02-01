package siesgst.edu.in.tml16.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by vishal on 30/12/15.
 */
public class OnlineDBDownloader {

    private JSONArray JSON;
    final String link = "http://tml.siesgst.ac.in/includes/resources.php";
    final String regLink = "http://tml.siesgst.ac.in/validate/validate.php";

    Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public OnlineDBDownloader(Context context) {
        this.context = context;
    }

    public void downloadData() {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.connect();
            JSONObject object = new JSONObject(convertStreamToString(conn.getInputStream()));
            JSON = object.getJSONArray("events");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }

    }

    public void submitRegData(String fullName, String emailID, String phone, String year, String branch, String college, String division, String rollNO, String event) {
        String parameters = "uName=" + fullName + "&" + "uEmail=" + emailID + "&" + "uPhone=" + phone + "&" + "uYear=" + year + "&" + "uBranch=" + branch + "&" + "uCollege=" + college + "&" + "uDivision=" + division + "&" + "uRoll=" + rollNO + "&" + "uEvent=" + event;
        byte[] postData = parameters.getBytes(Charset.forName("UTF-8"));
        int postDataLength = postData.length;
        HttpURLConnection conn = null;

        sharedPreferences = context.getSharedPreferences("TML", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        try {
            URL url = new URL(regLink);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.connect();
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(parameters);
            writer.flush();
            editor.remove("reg_status");
            editor.putString("reg_status", convertStreamToString(conn.getInputStream()));
            editor.apply();
            writer.close();
        } catch (SocketException e) {
            editor.remove("reg_status");
            editor.putString("reg_status", "Unable to connect fetch data; Try again...");
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
            editor.remove("reg_status");
            editor.putString("reg_status", "Some error occurred; Try again... ");
            editor.apply();
        } finally {
            if (conn != null)
                conn.disconnect();
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