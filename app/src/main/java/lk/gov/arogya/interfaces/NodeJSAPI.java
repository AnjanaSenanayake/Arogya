package lk.gov.arogya.interfaces;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NodeJSAPI {

    @POST("register")
    @FormUrlEncoded
    Observable<String> register(
            @Field("name") String name,
            @Field("nicpp") String nicpp,
            @Field("primaryContact") String primaryContact,
            @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Observable<String> login(
            @Field("nicpp") String nicpp,
            @Field("password") String password);
}
