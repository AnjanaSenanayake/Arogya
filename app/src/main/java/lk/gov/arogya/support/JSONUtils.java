package lk.gov.arogya.support;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lk.gov.arogya.models.User;

public class JSONUtils {

    public static ArrayList<String> parseToStringList(String payload) {
        ArrayList<String> uids = new ArrayList<>();
        try {
            JSONArray jArray = new JSONArray(payload);
            for (int i = 0; i < jArray.length(); i++) {
                User newUser = new User();
                JSONObject uidJSONObject = jArray.getJSONObject(i);
                uids.add(uidJSONObject.getString("UID"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return uids;
    }

    public static JSONObject parseToJSON(String payload) {
        try {
            return new JSONObject(payload);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
