package lk.gov.arogya.utils;


import android.content.Context;
import android.content.SharedPreferences;

import static lk.gov.arogya.utils.Constants.USER_ADDRESS1;
import static lk.gov.arogya.utils.Constants.USER_ADDRESS2;
import static lk.gov.arogya.utils.Constants.USER_ADDRESS3;
import static lk.gov.arogya.utils.Constants.USER_ADDRESS4;
import static lk.gov.arogya.utils.Constants.USER_DOB;
import static lk.gov.arogya.utils.Constants.USER_DS_DIVISION;
import static lk.gov.arogya.utils.Constants.USER_FULL_NAME;
import static lk.gov.arogya.utils.Constants.USER_GENDER;
import static lk.gov.arogya.utils.Constants.USER_GS_DIVISION;
import static lk.gov.arogya.utils.Constants.USER_MOBILE_NO;
import static lk.gov.arogya.utils.Constants.USER_NIC_PASSPORT_NO;
import static lk.gov.arogya.utils.Constants.USER_PASSWORD;
import static lk.gov.arogya.utils.Constants.USER_PROPERTIES;
import static lk.gov.arogya.utils.Constants.USER_VALUE_NULL;


public class SharedPreferencesHelper {

    public static void saveUserFullName(Context context, String userFullName) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_FULL_NAME, userFullName);
        editor.apply();
    }

    public static String getUserFullName(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_FULL_NAME, USER_VALUE_NULL);
    }

    public static void saveUserNicOrPassportNo(Context context, String userNicOrPassportNo) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_NIC_PASSPORT_NO, userNicOrPassportNo);
        editor.apply();
    }

    public static String getUserNicOrPassportNo(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_NIC_PASSPORT_NO, USER_VALUE_NULL);
    }

    public static void saveUserMobileNo(Context context, String userMobileNo) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_MOBILE_NO, userMobileNo);
        editor.apply();
    }

    public static String getUserMobileNo(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_MOBILE_NO, USER_VALUE_NULL);
    }

    public static void saveUserPassword(Context context, String userPassword) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_PASSWORD, userPassword);
        editor.apply();
    }

    public static String getUserPassword(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_PASSWORD, USER_VALUE_NULL);
    }


    public static void saveUserDsDivision(Context context, String userDsDivision) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_DS_DIVISION, userDsDivision);
        editor.apply();
    }

    public static String getUserDsDivision(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_DS_DIVISION, USER_VALUE_NULL);
    }

    public static void saveUserGsDivision(Context context, String userGsDivision) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_GS_DIVISION, userGsDivision);
        editor.apply();
    }

    public static String getUserGsDivision(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_GS_DIVISION, USER_VALUE_NULL);
    }

    public static void saveUserAddress1(Context context, String userAddress1) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_ADDRESS1, userAddress1);
        editor.apply();
    }

    public static String getUserAddress1(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_ADDRESS1, USER_VALUE_NULL);
    }

    public static void saveUserAddress2(Context context, String userAddress2) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_ADDRESS2, userAddress2);
        editor.apply();
    }

    public static String getUserAddress2(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_ADDRESS2, USER_VALUE_NULL);
    }

    public static void saveUserAddress3(Context context, String userAddress3) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_ADDRESS3, userAddress3);
        editor.apply();
    }

    public static String getUserAddress3(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_ADDRESS3, USER_VALUE_NULL);
    }

    public static void saveUserAddress4(Context context, String userAddress4) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_ADDRESS4, userAddress4);
        editor.apply();
    }

    public static String getUserAddress4(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_ADDRESS4, USER_VALUE_NULL);
    }

    public static void saveUserDob(Context context, String userDob) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_DOB, userDob);
        editor.apply();
    }

    public static String getUserDob(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_DOB, USER_VALUE_NULL);
    }

    public static void saveUserGender(Context context, String userGender) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_GENDER, userGender);
        editor.apply();
    }

    public static String getUserGender(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_PROPERTIES, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_GENDER, USER_VALUE_NULL);
    }


}
