package siesgst.edu.in.tml16.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vishal on 5/1/16.
 */
public class DataHandler {

    Context context;
    JSONArray dataArray;

    public DataHandler(Context context) {
        this.context = context;
    }

    public void decodeAndPushJSON(JSONArray JSON) {
        this.dataArray = JSON;
        pushEvents(dataArray);
    }

    public void pushEvents(JSONArray array) {
        JSONObject object;

        try {
            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                String[] data = new String[10];
                data[0] = object.getString("eName");
                data[1] = object.getString("eDay");
                data[2] = object.getString("eTime");
                data[3] = object.getString("eCategory");
                data[4] = object.getString("eHead1");
                data[5] = object.getString("ePhone1");
                data[6] = object.getString("eHead2");
                data[7] = object.getString("ePhone2");

                new LocalDBHandler(context).insertEventData(data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}