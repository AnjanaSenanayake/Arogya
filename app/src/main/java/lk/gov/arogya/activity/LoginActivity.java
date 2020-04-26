package lk.gov.arogya.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import lk.gov.arogya.R;
import lk.gov.arogya.activity.parent.FormValidationActivity;
import lk.gov.arogya.models.Messages;
import lk.gov.arogya.support.ContentHolder;
import lk.gov.arogya.support.JSONUtils;
import lk.gov.arogya.support.RestAPI;
import lk.gov.arogya.support.RestAPI.OnSuccessListener;

public class LoginActivity extends FormValidationActivity {

    private TextInputEditText edtNICPP, edtPassword;
    private TextInputLayout edtNICPPTxtInputLayout, edtPasswordTxtInputLayout;
    private Button btnLogin;
    private TextView tvLinkToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initInstances();
        tvLinkToRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {

                clearAllErrorMessages();
                boolean allEdtTxtBoxFilled = checkForEmptyEditBoxAndShowAlert();

                if (allEdtTxtBoxFilled) {
                    String nic = edtNICPP.getText().toString();
                    String password = edtPassword.getText().toString();

                    disableAllButtons();
                    showProgressBar();
                    login(nic, password);
                }
            }
        });
    }

    @Override
    public void initInstances() {
        edtNICPP = findViewById(R.id.edt_nicpp);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        tvLinkToRegister = findViewById(R.id.tv_link_to_register);
        edtNICPPTxtInputLayout = findViewById(R.id.edt_nicpp_txt);
        edtPasswordTxtInputLayout = findViewById(R.id.edt_password_txt);
    }


    private void login(String nicpp, String password) {
        //temp code for checking AskUserInformationActivity
        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
        RestAPI.login(nicpp, password, new OnSuccessListener<String, Throwable>() {
            @Override
            public void onSuccess(String response) {
                hideProgressBar();
                response = response.replace("\"", "");
                if (response.contains("UID")) {
                    Toast.makeText(LoginActivity.this, R.string.msg_login_success, Toast.LENGTH_LONG).show();
                    try {
                        ContentHolder.setUID(JSONUtils.parseToJSON(response).getString("UID"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    return;
                } else if (response.equals(Messages.USER_DOES_NOT_EXISTS.getMessage())) {
                    showSnackBarMessage(Messages.USER_DOES_NOT_EXISTS.getMessage());
                } else if (response.equals(Messages.INCORRECT_PASSWORD.getMessage())) {
                    showSnackBarMessage(Messages.INCORRECT_PASSWORD.getMessage());
                } else if (response.equals(Messages.USER_DOES_NOT_HAVE_LOGIN_ACCOUNT.getMessage())) {
                    showSnackBarMessage(Messages.USER_DOES_NOT_HAVE_LOGIN_ACCOUNT.getMessage());
                } else {
                    showSnackBarMessage(Messages.LOGIN_FAILED.getMessage());
                }
                enableAllButtons();
            }

            @Override
            public void onFailure(final Throwable err) {
                hideProgressBar();
                enableAllButtons();
            }
        });
    }

    private void showSnackBarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorView, message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtNICPP.setText("");
                        edtPassword.setText("");
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    protected void disableOrEnableButtons(boolean disable) {
        btnLogin.setEnabled(disable);
        tvLinkToRegister.setEnabled(disable);
        edtNICPPTxtInputLayout.setEnabled(disable);
        edtPasswordTxtInputLayout.setEnabled(disable);
    }

    @Override
    protected boolean checkForEmptyEditBoxAndShowAlert() {
        boolean returnVal = false;

        if (isEditTextEmpty(edtNICPP)) {
            edtNICPPTxtInputLayout.setError("Please fill " + getString(R.string.edt_nic_number));
        } else if (isEditTextEmpty(edtPassword)) {
            edtPasswordTxtInputLayout.setError("Please fill " + getString(R.string.tv_password));
        } else {
            returnVal = true;
        }
        return returnVal;
    }


    @Override
    protected void clearAllErrorMessages() {
        edtNICPPTxtInputLayout.setError(null);
        edtPasswordTxtInputLayout.setError(null);
    }
}
