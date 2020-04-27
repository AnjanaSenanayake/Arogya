package lk.gov.arogya.signup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import lk.gov.arogya.R;
import lk.gov.arogya.login.LoginActivity;
import lk.gov.arogya.models.Messages;
import lk.gov.arogya.api.RestAPI;
import lk.gov.arogya.api.RestAPI.OnSuccessListener;
import lk.gov.arogya.parent.FormValidationActivity;
import lk.gov.arogya.support.PreferenceUtil;

public class SignUpActivity extends FormValidationActivity {

    private static final String TAG = "SignUpActivity";
    private TextInputEditText edtFullName, edtNICPP, edtPrimaryContact;
    private TextInputEditText edtPassword, editConfirmPassword;
    private TextInputLayout edtFullNameTxtInputLayout, edtNICPPTxtInputLayout, edtPrimaryContactTxtInputLayout;
    private TextInputLayout edtPasswordTxtInputLayout, edtPasswordConformTxtInputLayout;
    private Button btnRegister;
    private TextView tvLinkToLogin;
    private String name;
    private String nic;
    private String mobile;
    private String password;
    private String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initInstances();
        setUpOnClickListener();
    }

    @Override
    protected void initInstances() {
        edtFullName = findViewById(R.id.edt_full_name);
        edtNICPP = findViewById(R.id.edt_nicpp);
        edtPrimaryContact = findViewById(R.id.edt_primary_contact);
        edtPassword = findViewById(R.id.edt_password);
        editConfirmPassword = findViewById(R.id.edt_confirm_password);
        edtFullNameTxtInputLayout = findViewById(R.id.edt_full_name_txt);
        edtNICPPTxtInputLayout = findViewById(R.id.edt_nicpp_txt);
        edtPrimaryContactTxtInputLayout = findViewById(R.id.edt_primary_contact_txt);
        edtPasswordTxtInputLayout = findViewById(R.id.edt_password_txt);
        edtPasswordConformTxtInputLayout = findViewById(R.id.edt_confirm_password_txt);
        btnRegister = findViewById(R.id.btn_register);
        tvLinkToLogin = findViewById(R.id.tv_link_to_login);
    }

    private void setUpOnClickListener() {
        tvLinkToLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                clearAllErrorMessages();
                boolean allEdtTxtBoxFilled = checkForEmptyEditBoxAndShowAlert();

                if (allEdtTxtBoxFilled) {
                    name = edtFullName.getText().toString();
                    nic = edtNICPP.getText().toString();
                    mobile = edtPrimaryContact.getText().toString();
                    password = edtPassword.getText().toString();
                    confirmPassword = editConfirmPassword.getText().toString();

                    if (!password.equals(confirmPassword)) {
                        edtPasswordTxtInputLayout.setError("Password and confirm password does not match");
                        edtPasswordConformTxtInputLayout.setError("Password and confirm password does not match");
                    } else {
                        disableAllButtons();
                        showProgressBar();
                        register(name, nic, mobile, password);
                    }
                }
            }
        });
    }

    private void register(String name, String nicpp, String primaryContact, String password) {
        RestAPI.register(name, nicpp, primaryContact, password, new OnSuccessListener<String, Throwable>() {
            @Override
            public void onSuccess(String response) {
                hideProgressBar();
                response = response.replace("\"", "");
                if (response.equals(Messages.REGISTER_SUCCESS.getMessage())) {
                    Toast.makeText(SignUpActivity.this, getResources().getString(R.string.msg_register_success),
                            Toast.LENGTH_LONG).show();
                    saveUserInfoInDevice();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                    return;
                } else if (response.equals(Messages.USER_ALREADY_EXISTS.getMessage())) {
                    showSnackBarMessage(getResources().getString(R.string.msg_user_already_exists));
                } else {
                    showSnackBarMessage(Messages.REGISTER_FAILED.getMessage());
                }
                enableAllButtons();
            }

            @Override
            public void onFailure(final Throwable err) {
                hideProgressBar();
                enableAllButtons();
                showSnackBarMessage(getResources().getString(R.string.msg_register_failed));
            }
        });
    }

    private void showSnackBarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorView, message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clearAllErrorMessages();
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
        PreferenceUtil.setSharedPreferenceString(R.string.edt_full_name, name);
        PreferenceUtil.setSharedPreferenceString(R.string.edt_nic_number, nic);
        PreferenceUtil.setSharedPreferenceString(R.string.edt_mobile_number, mobile);
        PreferenceUtil.setSharedPreferenceString(R.string.tv_password, password);
    }

    @Override
    protected boolean checkForEmptyEditBoxAndShowAlert() {
        boolean returnVal = false;

        if (isEditTextEmpty(edtFullName)) {
            edtFullNameTxtInputLayout.setError("Please fill " + getString(R.string.edt_full_name));
        } else if (isEditTextEmpty(edtNICPP)) {
            edtNICPPTxtInputLayout.setError("Please fill " + getString(R.string.edt_nic_number));
        } else if (isEditTextEmpty(edtPrimaryContact)) {
            edtPrimaryContactTxtInputLayout.setError("Please fill " + getString(R.string.edt_mobile_number));
        } else if (isEditTextEmpty(edtPassword)) {
            edtPasswordTxtInputLayout.setError("Please fill " + getString(R.string.tv_password));
        } else if (isEditTextEmpty(editConfirmPassword)) {
            edtPasswordConformTxtInputLayout.setError("Please fill " + getString(R.string.tv_confirm_password));
        } else {
            returnVal = true;
        }
        return returnVal;
    }


    @Override
    protected void disableOrEnableButtons(boolean disable) {
        btnRegister.setEnabled(disable);
        tvLinkToLogin.setEnabled(disable);
        edtFullNameTxtInputLayout.setEnabled(disable);
        edtNICPPTxtInputLayout.setEnabled(disable);
        edtPrimaryContactTxtInputLayout.setEnabled(disable);
        edtPasswordTxtInputLayout.setEnabled(disable);
        edtPasswordConformTxtInputLayout.setEnabled(disable);
    }

    @Override
    protected void clearAllErrorMessages() {
        edtFullNameTxtInputLayout.setError(null);
        edtNICPPTxtInputLayout.setError(null);
        edtPrimaryContactTxtInputLayout.setError(null);
        edtPasswordTxtInputLayout.setError(null);
        edtPasswordConformTxtInputLayout.setError(null);
    }


}
