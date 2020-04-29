package lk.gov.arogya.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lk.gov.arogya.models.Constants.Approval;
import lk.gov.arogya.models.CurfewPassRequest;
import lk.gov.arogya.models.EpidemicAlert;
import lk.gov.arogya.models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParserUtils {

    public static ArrayList<String> parseToStringList(String payload, String key) {
        ArrayList<String> list = new ArrayList<>();
        try {
            JSONArray jArray = new JSONArray(payload);
            for (int i = 0; i < jArray.length(); i++) {
                User newUser = new User();
                JSONObject uidJSONObject = jArray.getJSONObject(i);
                list.add(uidJSONObject.getString(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
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
        HashMap<Integer, String> map = new HashMap<>();
        try {
            JSONArray jArray = new JSONArray(payload);
            JSONObject jsonObject;
            for (int i = 0; i < jArray.length(); i++) {
                jsonObject = jArray.getJSONObject(i);
                map.put(jsonObject.getInt(key), jsonObject.getString(value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
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

    public static ArrayList<CurfewPassRequest> parseToCurfewPassList(String payload) {
        ArrayList<CurfewPassRequest> list = new ArrayList<>();
        CurfewPassRequest curfewPassRequest;
        try {
            JSONArray jArray = new JSONArray(payload);
            for (int i = 0; i < jArray.length(); i++) {
                curfewPassRequest = new CurfewPassRequest();
                JSONObject jsonObject = jArray.getJSONObject(i);
                curfewPassRequest.setRequestID(jsonObject.getInt("RequestID"));
                curfewPassRequest.setRequestedFor(jsonObject.getString("Name"));
                curfewPassRequest.setRequestedBy(jsonObject.getString("RequestedBy"));
                curfewPassRequest.setReasonOfRequest(jsonObject.getString("Reason"));
                curfewPassRequest.setWhereTo(jsonObject.getString("WhereTo"));
                curfewPassRequest.setValidDateFrom(jsonObject.getString("ValidFrom"));
                curfewPassRequest.setValidDateTo(jsonObject.getString("ValidTo"));
                curfewPassRequest.setInspectedBy(jsonObject.getString("InspectedBy"));
                curfewPassRequest.setInspectedOn(jsonObject.getString("InspectedOn"));
                curfewPassRequest.setStatus(Approval.valueOf(jsonObject.getString("Status")));
                list.add(curfewPassRequest);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
