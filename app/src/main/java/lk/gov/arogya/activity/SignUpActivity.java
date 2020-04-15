package lk.gov.arogya.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lk.gov.arogya.R;
import lk.gov.arogya.interfaces.NodeJSAPI;
import lk.gov.arogya.support.RetrofitClient;
import retrofit2.Retrofit;

public class SignUpActivity extends Activity {

    private EditText edtFullName, edtNIC, edtMobileNumber;
    private EditText edtPassword;
    private Button btnRegister;
    private TextView tvLinkToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtFullName = findViewById(R.id.edt_full_name);
        edtNIC = findViewById(R.id.edt_nic);
        edtMobileNumber = findViewById(R.id.edt_phone_number);
        edtPassword = findViewById(R.id.edt_password);
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
                String name = edtFullName.getText().toString();
                String nic = edtNIC.getText().toString();
                String mobile = edtMobileNumber.getText().toString();
                String password = edtPassword.getText().toString();
                register(name, nic, mobile, password);
            }
        });
    }

    private void register(String name, String nic, String mobile, String password) {
        Retrofit retrofit = RetrofitClient.getInstance();
        NodeJSAPI nodeJSAPI = retrofit.create(NodeJSAPI.class);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(nodeJSAPI.register(name, nic, mobile, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Toast.makeText(SignUpActivity.this, "Register Success", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(SignUpActivity.class.getSimpleName(), throwable.getMessage(), throwable);
                    }
                }));
    }
}
