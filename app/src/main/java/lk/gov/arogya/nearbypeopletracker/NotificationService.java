package lk.gov.arogya.nearbypeopletracker;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationService {

    final static private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final static private String serverKey = "key=" + "AAAAZLoqySo:APA91bE5TlGqhyyiRWvyQIoPmdGUmEQx78rg-waMko5wJ__yoqnfEnPO1-SK9BoMpl9YaMP2FwxXWTf3zj8JQRMQMdEOIQFrgIdwJkY2xYazZKKpjlgJsfGmeS4cKqs4u_G37nUj1dnI";
    final static private String contentType = "application/json";
    private final static String TAG = "NotificationService";

    private static JSONObject getNotificationContent(String UIDHash, String status) {
        JSONObject notification = new JSONObject();
        JSONObject notificationBody = new JSONObject();
        try {
            notificationBody.put("UID_hash", UIDHash);
            notificationBody.put("status", status);
            notification.put("to", "/topics/" + UIDHash);
            notification.put("data", notificationBody);
            return notification;
        } catch (JSONException e) {
            Log.e(TAG, "Notification create  failed: " + e.getMessage());
            return null;
        }
    }

    public static void sendNotification(Context context, String topic, String message) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, getNotificationContent(topic, message),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Send notification success: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "Send notification failed");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        NotificationQueue.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
