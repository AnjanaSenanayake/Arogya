package lk.gov.arogya.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnectionDetector {

    public class ConnectionDetector {

        private Context context_this;

        public ConnectionDetector(Context context) {
            this.context_this = context;
        }

        public boolean IsInternetConnected() {
            ConnectivityManager connectivityManager = (ConnectivityManager) context_this
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();
                if (infos != null) {
                    for (int i = 0; i < infos.length; i++) {
                        if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
}
