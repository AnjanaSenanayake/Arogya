package lk.gov.arogya;

import android.content.Context;
import android.util.Log;

public class Application extends android.app.Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Application.mContext = this;
    }

    public static Context getAppContext() {
        return Application.mContext;
    }

    @Override
    public void onTrimMemory(int level) {
        Log.i("Application", "onTrimMemory");
        super.onTrimMemory(level);
    }
}
