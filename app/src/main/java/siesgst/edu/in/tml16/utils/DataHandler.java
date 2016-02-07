package siesgst.edu.in.tml16.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TooManyListenersException;

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
        for (int i = 0; i < array.length(); i++) {
            object = array.optJSONObject(i);
            String[] data = new String[12];
            data[0] = object.optString("eName");
            data[1] = object.optString("eDay");
            data[2] = object.optString("eVenue");
            data[3] = object.optString("eCategory");
            data[4] = object.optString("eSubCategory");
            data[5] = object.optString("eDetails");
            data[6] = object.optString("eHead1");
            data[7] = object.optString("ePhone1");
            data[8] = object.optString("eHead2");
            data[9] = object.optString("ePhone2");
            data[10] = object.optString("eCreated");
            data[11] = object.optString("eModified");

            new LocalDBHandler(context).insertEventData(data);
        }
    }

    public void pushFBData(JSONObject object) {
        JSONArray array;
        JSONObject fbObject;
        array = object.optJSONArray("data");
        for (int i = 0; i < array.length(); i++) {
            fbObject = array.optJSONObject(i);
            String[] data = new String[5];
                data[0] = fbObject.optString("message");
                data[1] = fbObject.optString("full_picture");
                data[2] = fbObject.optString("link");
            try {
                JSONObject likeObject = fbObject.optJSONObject("likes");
                JSONObject totalLikesObject = likeObject.optJSONObject("summary");
                data[3] = totalLikesObject.optString("total_count");
            } catch (NullPointerException e) {
                data[3] = "0";
            }
            try {
                JSONObject commentObject = fbObject.optJSONObject("comments");
                JSONObject totalCommentObject = commentObject.optJSONObject("summary");
                data[4] = totalCommentObject.optString("total_count");
            } catch (NullPointerException e) {
                data[4] = "0";
            }

            new LocalDBHandler(context).insertFBData(data);
        }
    }
}