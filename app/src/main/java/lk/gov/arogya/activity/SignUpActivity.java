package lk.gov.arogya.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import lk.gov.arogya.R;
import lk.gov.arogya.models.Messages;
import lk.gov.arogya.support.RestAPI;
import lk.gov.arogya.support.RestAPI.OnSuccessListener;
import lk.gov.arogya.utils.SharedPreferencesHelper;

public class SignUpActivity extends Activity {

    private static final String TAG = "SignUpActivity";
    private String name;
    private String nic;
    private String mobile;
    private String password;
    private EditText edtFullName, edtNICPP, edtPrimaryContact;
    private EditText edtPassword, editConfirmPassword;
    private Button btnRegister;
    private TextView tvLinkToLogin;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mLinearLayout = findViewById(R.id.linear_layout_sign_up);
        edtFullName = findViewById(R.id.edt_full_name);
        edtNICPP = findViewById(R.id.edt_nicpp);
        edtPrimaryContact = findViewById(R.id.edt_primary_contact);
        edtPassword = findViewById(R.id.edt_password);
        editConfirmPassword = findViewById(R.id.edt_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLinkToLogin = findViewById(R.id.tv_link_to_login);
        tvLinkToLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                name = edtFullName.getText().toString();
                nic = edtNICPP.getText().toString();
                mobile = edtPrimaryContact.getText().toString();
                password = edtPassword.getText().toString();
                String confirmPassword = editConfirmPassword.getText().toString();

//                saveUserInfoInDevice();
//                checking for app functionality without server will remove later
//                startActivity(new Intent(SignUpActivity.this, AskUserInformationActivity.class));

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Password and confirm password does not match",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (name.matches("") || nic.matches("") || mobile.matches("") || password.matches("")) {
                    Toast.makeText(SignUpActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    register(name, nic, mobile, password);
                }
            }
        });
    }

    private void register(String name, String nicpp, String primaryContact, String password) {
        RestAPI.register(name, nicpp, primaryContact, password, new OnSuccessListener<String, Throwable>() {
            @Override
            public void onSuccess(String response) {
                response = response.replace("\"", "");
                if (response.equals(Messages.REGISTER_SUCCESS.getMessage())) {
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.msg_register_success), Toast.LENGTH_LONG).show();
                    saveUserInfoInDevice();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                } else if (response.equals(Messages.USER_ALREADY_EXISTS.getMessage())) {
                    showSnackBarMessage(getResources().getString(R.string.msg_user_already_exists));
                } else {
                    showSnackBarMessage(Messages.REGISTER_FAILED.getMessage());
                }
            }

            @Override
            public void onFailure(final Throwable err) {
                showSnackBarMessage(getResources().getString(R.string.msg_register_failed));
            }
        });
    }

    private void showSnackBarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(mLinearLayout, message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtFullName.setText("");
                        edtNICPP.setText("");
                        edtPrimaryContact.setText("");
                        edtPassword.setText("");
                        editConfirmPassword.setText("");
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    private void saveUserInfoInDevice() {
        SharedPreferencesHelper.saveUserFullName(this, name);
        SharedPreferencesHelper.saveUserMobileNo(this, mobile);
        SharedPreferencesHelper.saveUserNicOrPassportNo(this, nic);
        SharedPreferencesHelper.saveUserPassword(this, password);
    }
}
