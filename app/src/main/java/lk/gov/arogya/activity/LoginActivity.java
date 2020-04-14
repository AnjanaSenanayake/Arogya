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

public class LoginActivity extends AppCompatActivity {

    private EditText edtNIC, edtPasscode;
    private Button btnLogin;
    private TextView tvLinkToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtNIC = findViewById(R.id.edt_nic);
        edtPasscode = findViewById(R.id.edt_pass_code);
        btnLogin = findViewById(R.id.btn_login);
        tvLinkToRegister = findViewById(R.id.tv_link_to_register);
        tvLinkToRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
}
