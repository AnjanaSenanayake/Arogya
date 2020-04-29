package lk.gov.arogya.api;

import android.util.Log;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import lk.gov.arogya.models.CurfewPassRequest;
import lk.gov.arogya.models.Messages;
import lk.gov.arogya.models.User;
import lk.gov.arogya.support.ContentHolder;
import lk.gov.arogya.support.ParserUtils;
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
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        listener.onSuccess(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending login method");
    }

    public static void getUserByUID(String uid, OnSuccessListener<User, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting getUserByUID method");
        compositeDisposable.add(nodeJSAPI.getUserByUID(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        User user = User.parseToUser(s);
                        ContentHolder.setUser(user);
                        listener.onSuccess(user);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending getUserByUID method");
    }

    public static void updateUser(User updatedUser, OnSuccessListener<Boolean, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting updateUser method");
        compositeDisposable.add(nodeJSAPI.updateUser(ContentHolder.getUser().getUid(), updatedUser.getFullName(), updatedUser.getPrimaryContact(),
                updatedUser.getAddressLine1(), updatedUser.getAddressLine2(), updatedUser.getAddressLine3(), updatedUser.getAddressLine4(),
                updatedUser.getDSDivision(), updatedUser.getGNDivision(),
                updatedUser.getEmergencyContact(), updatedUser.getEmergencyContactRelation(),
                updatedUser.getSecondaryContact1(), updatedUser.getSecondaryContact2(),
                updatedUser.getDob(), updatedUser.getGender(), updatedUser.getMaritalStatus())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        if (s.equals(Messages.UPDATE_SUCCESS.getMessage()))
                            listener.onSuccess(true);
                        else
                            listener.onSuccess(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending updateUser method");
    }

    public static void createUser(User newUser, OnSuccessListener<User, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting getUserByUID method");
        compositeDisposable.add(nodeJSAPI.createUser(ContentHolder.getUser().getUid(), newUser.getFullName(), newUser.getNicpp(), newUser.getPrimaryContact(),
                newUser.getAddressLine1(), newUser.getAddressLine2(), newUser.getAddressLine3(), newUser.getAddressLine4(),
                newUser.getEmergencyContact(), newUser.getEmergencyContactRelation(),
                newUser.getSecondaryContact1(), newUser.getSecondaryContact2(),
                newUser.getDob(), newUser.getGender(), newUser.getMaritalStatus())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        listener.onSuccess(User.parseToUser(s));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending getUserByUID method");
    }

    public static void getAllChildUsers(String pid, OnSuccessListener<ArrayList<User>, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting getAllChildUsers method");
        compositeDisposable.add(nodeJSAPI.getAllChildUsers(pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        listener.onSuccess(User.parseToUserList(s));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending getAllChildUsers method");
    }

    public static void getAllEpidemics(OnSuccessListener<HashMap<Integer, String>, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting getAllEpidemics method");
        compositeDisposable.add(nodeJSAPI.getAllEpidemics()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        listener.onSuccess(
                                (HashMap<Integer, String>) ParserUtils.parseToMap(s, "EpidemicID", "EpidemicName"));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending getAllEpidemics method");
    }

    public static void createEpidemicAlert(String UID, int infectionID, String indentifiedDate, OnSuccessListener<Boolean, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting createEpidemicAlert method");
        compositeDisposable.add(nodeJSAPI.createEpidemicAlert(UID, infectionID, indentifiedDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        listener.onSuccess(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending createEpidemicAlert method");
    }

    public static void getDSByDistrict(String districtID, OnSuccessListener<ArrayList<String>, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting getDSByDistrict method");
        compositeDisposable.add(nodeJSAPI.getDSByDistrictName(districtID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        listener.onSuccess(ParserUtils.parseToStringList(s, "DivisionalSecretariatName"));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending getDSByDistrict method");
    }

    public static void getGNByDivision(String DSID, OnSuccessListener<ArrayList<String>, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting getGNByDivision method");
        compositeDisposable.add(nodeJSAPI.getGNByDivisionName(DSID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        listener.onSuccess(ParserUtils.parseToStringList(s, "GNDivisionName"));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending getGNByDivision method");
    }

    public static void requestCurfewPass(CurfewPassRequest curfewPassRequest,
            OnSuccessListener<Boolean, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting requestCurfewPass method");
        compositeDisposable.add(nodeJSAPI.requestCurfewPass(
                curfewPassRequest.getRequestedFor(),
                ContentHolder.getUID(),
                curfewPassRequest.getReasonOfRequest(),
                curfewPassRequest.getWhereTo(),
                curfewPassRequest.getValidDateFrom(),
                curfewPassRequest.getValidDateTo())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        listener.onSuccess(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending requestCurfewPass method");
    }

    public static void getAllCurfewPasses(String requestedBy,
            OnSuccessListener<ArrayList<CurfewPassRequest>, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting getAllCurfewPasses method");
        compositeDisposable.add(nodeJSAPI.getAllCurfewPassesUnderUser(
                requestedBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        listener.onSuccess(ParserUtils.parseToCurfewPassList(s));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending getAllCurfewPasses method");
    }

    public static void cancelRequestedCurfewPass(int curfewPassRequestID,
            OnSuccessListener<Boolean, Throwable> listener) {
        Log.d(RestAPI.class.getSimpleName(), "Starting cancelRequestedCurfewPass method");
        compositeDisposable.add(nodeJSAPI.cancelRequestedCurfewPass(
                curfewPassRequestID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        Log.d(RestAPI.class.getSimpleName(), "Payload: " + s);
                        listener.onSuccess(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(RestAPI.class.getSimpleName(), throwable.getMessage(), throwable);
                        listener.onFailure(throwable);
                    }
                }));
        Log.d(RestAPI.class.getSimpleName(), "Ending cancelRequestedCurfewPass method");
    }
}
