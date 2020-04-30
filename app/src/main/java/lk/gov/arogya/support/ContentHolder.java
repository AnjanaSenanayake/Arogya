package lk.gov.arogya.support;

import java.util.ArrayList;
import lk.gov.arogya.R;
import lk.gov.arogya.models.User;

public class ContentHolder {

    private static String UID;
    private static User user;
    private static ArrayList<User> userArrayList = new ArrayList<>();

    public static String getUID() {
        if (UID == null) {
            UID = PreferenceUtil.getSharedPreferenceString(R.string.id_uid);
        }
        return UID;
    }

    public static void setUID(String UID) {
        ContentHolder.UID = UID;
        PreferenceUtil.setSharedPreferenceString(R.string.id_uid, UID);
    }

    public static ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public static void setUserArrayList(ArrayList<User> userArrayList) {
        ContentHolder.userArrayList = userArrayList;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        ContentHolder.user = user;
    }
}
