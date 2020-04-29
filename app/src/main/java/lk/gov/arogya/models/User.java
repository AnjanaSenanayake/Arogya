package lk.gov.arogya.models;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
    private String uid;
    private String pid;
    private String fullName;
    private String nicpp;
    private String primaryContact;
    private String dob;
    private String gender;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String district;
    private String DSDivision;
    private String GNDivision;
    private String secondaryContact1;
    private String secondaryContact2;
    private String emergencyContact;
    private String emergencyContactRelation;
    private String maritalStatus;
    private boolean isVerified;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNicpp() {
        return nicpp;
    }

    public void setNicpp(String nicpp) {
        this.nicpp = nicpp;
    }

    public String getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(String primaryContact) {
        this.primaryContact = primaryContact;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(final String district) {
        this.district = district;
    }

    public String getDSDivision() {
        return DSDivision;
    }

    public void setDSDivision(final String DSDivision) {
        this.DSDivision = DSDivision;
    }

    public String getGNDivision() {
        return GNDivision;
    }

    public void setGNDivision(final String GNDivision) {
        this.GNDivision = GNDivision;
    }

    public String getSecondaryContact1() {
        return secondaryContact1;
    }

    public void setSecondaryContact1(String secondaryContact1) {
        this.secondaryContact1 = secondaryContact1;
    }

    public String getSecondaryContact2() {
        return secondaryContact2;
    }

    public void setSecondaryContact2(String secondaryContact2) {
        this.secondaryContact2 = secondaryContact2;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactRelation() {
        return emergencyContactRelation;
    }

    public void setEmergencyContactRelation(String emergencyContactRelation) {
        this.emergencyContactRelation = emergencyContactRelation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public static User parseToUser(String payload) {
        User newUser = new User();
        try {
            JSONObject userJSONObject = new JSONObject(payload);
            newUser.setUid(optString(userJSONObject, "UID"));
            newUser.setPid(optString(userJSONObject, "PID"));
            newUser.setFullName(optString(userJSONObject, "Name"));
            newUser.setNicpp(optString(userJSONObject, "NICPP"));
            newUser.setPrimaryContact(optString(userJSONObject, "PrimaryContact"));
            newUser.setDob(optString(userJSONObject, "DOB"));
            newUser.setGender(optString(userJSONObject, "Gender"));
            newUser.setSecondaryContact1(optString(userJSONObject, "SecondaryContact1"));
            newUser.setSecondaryContact2(optString(userJSONObject, "SecondaryContact2"));
            newUser.setEmergencyContact(optString(userJSONObject, "EmergencyContact"));
            newUser.setEmergencyContactRelation(optString(userJSONObject, "EmergencyContactRelation"));
            newUser.setAddressLine1(optString(userJSONObject, "AddressLine1"));
            newUser.setAddressLine2(optString(userJSONObject, "AddressLine2"));
            newUser.setAddressLine3(optString(userJSONObject, "AddressLine3"));
            newUser.setAddressLine4(optString(userJSONObject, "AddressLine4"));
            newUser.setDistrict(optString(userJSONObject, "DistrictName"));
            newUser.setDSDivision(optString(userJSONObject, "DivisionalSecretariatName"));
            newUser.setGNDivision(optString(userJSONObject, "GNDivisionName"));
            newUser.setMaritalStatus(optString(userJSONObject, "MaritalStatus"));
            if (userJSONObject.getInt("IsVerified") == 1)
                newUser.setVerified(true);
            else
                newUser.setVerified(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newUser;
    }

    public static ArrayList<User> parseToUserList(String payload) {
        ArrayList<User> users = new ArrayList<>();
        try {
            JSONArray jArray = new JSONArray(payload);
            for (int i = 0; i < jArray.length(); i++) {
                User newUser = new User();
                JSONObject userJSONObject = jArray.getJSONObject(i);
                newUser.setUid(optString(userJSONObject, "UID"));
                newUser.setPid(optString(userJSONObject, "PID"));
                newUser.setFullName(optString(userJSONObject, "Name"));
                newUser.setNicpp(optString(userJSONObject, "NICPP"));
                newUser.setPrimaryContact(optString(userJSONObject, "PrimaryContact"));
                newUser.setDob(optString(userJSONObject, "DOB"));
                newUser.setGender(optString(userJSONObject, "Gender"));
                newUser.setSecondaryContact1(optString(userJSONObject, "SecondaryContact1"));
                newUser.setSecondaryContact2(optString(userJSONObject, "SecondaryContact2"));
                newUser.setEmergencyContact(optString(userJSONObject, "EmergencyContact"));
                newUser.setEmergencyContactRelation(optString(userJSONObject, "EmergencyContactRelation"));
                newUser.setAddressLine1(optString(userJSONObject, "AddressLine1"));
                newUser.setAddressLine2(optString(userJSONObject, "AddressLine2"));
                newUser.setAddressLine3(optString(userJSONObject, "AddressLine3"));
                newUser.setAddressLine4(optString(userJSONObject, "AddressLine4"));
                newUser.setDistrict(optString(userJSONObject, "DistrictName"));
                newUser.setDSDivision(optString(userJSONObject, "DivisionalSecretariatName"));
                newUser.setGNDivision(optString(userJSONObject, "GNDivisionName"));
                newUser.setMaritalStatus(optString(userJSONObject, "MaritalStatus"));
                if (userJSONObject.getInt("IsVerified") == 1)
                    newUser.setVerified(true);
                else
                    newUser.setVerified(false);
                users.add(newUser);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }

    private static String optString(JSONObject json, String key) {
        // http://code.google.com/p/android/issues/detail?id=13830
        if (json.isNull(key))
            return null;
        else
            return json.optString(key, null);
    }
}
