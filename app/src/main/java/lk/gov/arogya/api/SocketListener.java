package lk.gov.arogya.api;

import com.github.nkzawa.emitter.Emitter.Listener;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import java.net.URISyntaxException;
import lk.gov.arogya.support.ContentHolder;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketListener {

    private static Socket socket;

    public SocketListener() {
    }

    public static void instantiate() {
        try {
            socket = IO.socket(RetrofitClient.SERVER_URL);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public interface OnChangeListener<T> {

        void onSuccess(T response);
    }

    public static void userVerificationListener(OnChangeListener<Boolean> listener) {
        socket.on("verifyUser", new Listener() {
            @Override
            public void call(final Object... args) {
                try {
                    JSONObject obj = (JSONObject) args[0];
                    String uid = obj.getString("uid");
                    if (uid.equals(ContentHolder.getUID())) {
                        boolean value = obj.getInt("isVerified") == 1;
                        listener.onSuccess(value);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void curfewPassApprovalListener(OnChangeListener<String> listener) {
        socket.on("passApproval", new Listener() {
            @Override
            public void call(final Object... args) {
                try {
                    JSONObject obj = (JSONObject) args[0];
                    listener.onSuccess(obj.getString("status"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
