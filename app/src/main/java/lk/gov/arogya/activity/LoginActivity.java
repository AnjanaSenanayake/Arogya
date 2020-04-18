package lk.gov.arogya.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

public class LoginActivity extends Activity {

    private EditText edtNICPP, edtPassword;
    private Button btnLogin;
    private TextView tvLinkToRegister;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLinearLayout = findViewById(R.id.linear_layout_login);
        edtNICPP = findViewById(R.id.edt_nicpp);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        tvLinkToRegister = findViewById(R.id.tv_link_to_register);
        tvLinkToRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                String nic = edtNICPP.getText().toString();
                String password = edtPassword.getText().toString();

//                checking for app functionality without server, will remove later
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));


                if (nic.matches("") && password.matches("")) {
                    Toast.makeText(LoginActivity.this, "Please fill NIC/Passport number and Password fields",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (nic.matches("")) {
                    Toast.makeText(LoginActivity.this, "Please fill NIC/Passport number field", Toast.LENGTH_SHORT)
                            .show();
                    return;
                } else if (password.matches("")) {
                    Toast.makeText(LoginActivity.this, "Please fill Password field", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    login(nic, password);
                }


            }
        });
    }

    private void login(String nicpp, String password) {
        RestAPI.login(nicpp, password, new OnSuccessListener<String, Throwable>() {
            @Override
            public void onSuccess(String response) {
                response = response.replace("\"", "");
                if (response.equals(Messages.LOGIN_SUCCESS.getMessage())) {
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                    //temp code for checking AskUserInformationActivity
                    startActivity(new Intent(LoginActivity.this, AskUserInformationActivity.class));
                } else if (response.equals(Messages.USER_DOES_NOT_EXISTS.getMessage())) {
                    showSnackBarMessage(Messages.USER_DOES_NOT_EXISTS.getMessage());
                } else if (response.equals(Messages.INCORRECT_PASSWORD.getMessage())) {
                    showSnackBarMessage(Messages.INCORRECT_PASSWORD.getMessage());
                } else if (response.equals(Messages.USER_DOES_NOT_HAVE_LOGIN_ACCOUNT.getMessage())) {
                    showSnackBarMessage(Messages.USER_DOES_NOT_HAVE_LOGIN_ACCOUNT.getMessage());
                } else {
                    showSnackBarMessage(Messages.LOGIN_FAILED.getMessage());
                }
            }

            @Override
            public void onFailure(final Throwable err) {
            }
        });
    }

    private void showSnackBarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(mLinearLayout, message, Snackbar.LENGTH_LONG)
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
}
