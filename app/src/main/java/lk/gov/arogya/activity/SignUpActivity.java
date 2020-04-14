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

public class SignUpActivity extends AppCompatActivity {

    private EditText edtFullName, edtNIC, edtMobileNumber;
    private EditText edtDivSecretariat, edtGramaDivCode;
    private Button btnRegister;
    private TextView tvLinkToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtFullName = findViewById(R.id.edt_full_name);
        edtNIC = findViewById(R.id.edt_nic);
        edtMobileNumber = findViewById(R.id.edt_phone_number);
        edtDivSecretariat = findViewById(R.id.edt_divisional_secretariat);
        edtGramaDivCode = findViewById(R.id.edt_grama_division);
        tvLinkToLogin = findViewById(R.id.tv_link_to_login);
        tvLinkToLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}
