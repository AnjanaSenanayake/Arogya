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

public class LoginActivity extends Activity {

    private EditText edtNIC, edtPassword;
    private Button btnLogin;
    private TextView tvLinkToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtNIC = findViewById(R.id.edt_nic);
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
                String nic = edtNIC.getText().toString();
                String password = edtPassword.getText().toString();
                login(nic, password);
            }
        });
    }

    private void login(String nic, String password) {
        Retrofit retrofit = RetrofitClient.getInstance();
        NodeJSAPI nodeJSAPI = retrofit.create(NodeJSAPI.class);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(nodeJSAPI.login(nic, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        if (s.contains("encrypted_password")) {
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(SignUpActivity.class.getSimpleName(), throwable.getMessage(), throwable);
                    }
                }));
    }
}
