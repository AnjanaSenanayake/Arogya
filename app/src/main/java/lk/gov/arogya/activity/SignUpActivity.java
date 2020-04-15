package lk.gov.arogya.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import lk.gov.arogya.R;
import lk.gov.arogya.utils.SharedPreferencesHelper;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtFullName, edtNIC, edtMobileNumber;
    private EditText edtPassword;
    private Button btnRegister;
    private TextView tvLinkToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnRegister = findViewById(R.id.btn_register);

        edtFullName = findViewById(R.id.edt_full_name);
        edtNIC = findViewById(R.id.edt_nic);
        edtMobileNumber = findViewById(R.id.edt_phone_number);
        edtPassword = findViewById(R.id.edt_password);
        tvLinkToLogin = findViewById(R.id.tv_link_to_login);
        tvLinkToLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        //temp code for checking AskUserInformationActivity
        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                SharedPreferencesHelper.saveUserNicOrPassportNo(SignUpActivity.this, edtNIC.getText().toString());
                SharedPreferencesHelper.saveUserFullName(SignUpActivity.this, edtFullName.getText().toString());
                SharedPreferencesHelper.saveUserMobileNo(SignUpActivity.this, edtMobileNumber.getText().toString());
                SharedPreferencesHelper.saveUserPassword(SignUpActivity.this, edtPassword.getText().toString());


                startActivity(new Intent(SignUpActivity.this, AskUserInformationActivity.class));
            }
        });
    }
}
