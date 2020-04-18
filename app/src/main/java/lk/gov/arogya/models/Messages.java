package lk.gov.arogya.models;

public enum Messages {
    ERROR_WHILE_REGISTERING_USER("Some error occurred while creating the User"),
    ERROR_RETRV_USER_WITH_NICPP("Error retrieving user with nicpp"),
    USER_ALREADY_EXISTS("User already exists"),
    REGISTER_SUCCESS("Register success"),
    REGISTER_FAILED("Register failed"),
    USER_DOES_NOT_EXISTS("User does not exists"),
    USER_DOES_NOT_HAVE_LOGIN_ACCOUNT("User does not have a login account"),
    ERROR_RETRV_PROFILE_WITH_UID("Error retrieving profile with uid: "),
    LOGIN_SUCCESS("Login success"),
    LOGIN_FAILED("Login failed"),
    INCORRECT_PASSWORD("Incorrect password");

    public String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
