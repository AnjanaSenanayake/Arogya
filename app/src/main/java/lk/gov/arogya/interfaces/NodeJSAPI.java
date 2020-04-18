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
            @Field("nic") String nic,
            @Field("mobile") String mobile,
            @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Observable<String> login(
            @Field("nic") String nic,
            @Field("password") String password);
}
