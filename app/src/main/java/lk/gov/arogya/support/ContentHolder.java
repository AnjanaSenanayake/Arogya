package lk.gov.arogya.support;

import java.util.ArrayList;

import lk.gov.arogya.models.User;

public class ContentHolder {

    private static String UID;
    private static User user;
    private static ArrayList<User> userArrayList = new ArrayList<>();

    public static String getUID() {
        return UID;
    }

    public static void setUID(String UID) {
        ContentHolder.UID = UID;
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
