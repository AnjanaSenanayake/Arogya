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

    private static final String TAG = "SignUpActivity";
    private EditText edtFullName, edtNICPP, edtPrimaryContact;
    private EditText edtPassword, editConfirmPassword;
    private Button btnRegister;
    private TextView tvLinkToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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
                String name = edtFullName.getText().toString();
                String nic = edtNICPP.getText().toString();
                String mobile = edtPrimaryContact.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = editConfirmPassword.getText().toString();
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

    private void register(String name, String nic, String mobile, String password) {
        Log.d(TAG, "register: ON the Register Method");
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
