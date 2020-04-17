package lk.gov.arogya.support;

import android.util.Log;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lk.gov.arogya.activity.LoginActivity;
import lk.gov.arogya.activity.SignUpActivity;
import lk.gov.arogya.interfaces.NodeJSAPI;
import retrofit2.Retrofit;

public class RestAPI {

    private static Retrofit retrofit = RetrofitClient.getInstance();
    private static NodeJSAPI nodeJSAPI = retrofit.create(NodeJSAPI.class);
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();

    public interface OnSuccessListener<T, V> {

        void onSuccess(T response);

        void onFailure(V err);
    }

    public static void register(String fullName, String nicpp, String primaryContact, String password,
            OnSuccessListener<String, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting register method");
        compositeDisposable.add(nodeJSAPI.register(fullName, nicpp, primaryContact, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.e(RestAPI.class.getSimpleName(), s);
                        listener.onSuccess(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending register method");
    }

    public static void login(String nicpp, String password, OnSuccessListener<String, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting login method");
        compositeDisposable.add(nodeJSAPI.login(nicpp, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(LoginActivity.class.getSimpleName(), "Payload: " + s);
                        listener.onSuccess(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(SignUpActivity.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending login method");
    }
}
