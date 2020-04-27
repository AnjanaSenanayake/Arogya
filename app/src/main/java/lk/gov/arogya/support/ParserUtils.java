package lk.gov.arogya.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lk.gov.arogya.models.EpidemicAlert;
import lk.gov.arogya.models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParserUtils {

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

    public static Map<Integer, String> parseToMap(String payload, String key, String value) {
        HashMap<Integer, String> infectionsMap = new HashMap<>();
        try {
            JSONArray jArray = new JSONArray(payload);
            JSONObject jsonObject;
            for (int i = 0; i < jArray.length(); i++) {
                jsonObject = jArray.getJSONObject(i);
                infectionsMap.put(jsonObject.getInt(key), jsonObject.getString(value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infectionsMap;
    }

    public static ArrayList<EpidemicAlert> parseToEpidemicAlertList(String alertList, String contactsList) {
        ArrayList<EpidemicAlert> epidemicAlerts = new ArrayList<>();
        EpidemicAlert epidemicAlert;

        String[] alertLines = alertList.split("\n");
        String[] contactLines = contactsList.split("\n");
        for (String alert : alertLines) {
            String[] alertTokens = alert.split("---");
            for (String contact : contactLines) {
                if (contact.contains(alertTokens[0])) {
                    String[] contactTokens = contact.split("---");
                    epidemicAlert = new EpidemicAlert();
                    epidemicAlert.setUIDHash(contactTokens[0]);
                    epidemicAlert.setEpidemic(alertTokens[1]);
                    epidemicAlert.setLatitude(contactTokens[1]);
                    epidemicAlert.setLongitude(contactTokens[2]);
                    epidemicAlert.setContactDate(contactTokens[3]);
                    epidemicAlerts.add(epidemicAlert);
                }
            }
        }
        return epidemicAlerts;
    }
}
