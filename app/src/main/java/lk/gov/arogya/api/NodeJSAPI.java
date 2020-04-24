package lk.gov.arogya.api;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @POST("createUser")
    @FormUrlEncoded
    Observable<String> createUser(
            @Field("pid") String pid,
            @Field("name") String name,
            @Field("nicpp") String nicpp,
            @Field("primaryContact") String primaryContact,
            @Field("addressLine1") String addressLine1,
            @Field("addressLine2") String addressLine2,
            @Field("addressLine3") String addressLine3,
            @Field("addressLine4") String addressLine4,
            @Field("emergencyContact") String emergencyContact,
            @Field("emergencyContactRelation") String emergencyContactRelation,
            @Field("secondaryContact1") String secondaryContact1,
            @Field("secondaryContact2") String secondaryContact2,
            @Field("dob") String dob,
            @Field("gender") String gender,
            @Field("maritalStatus") String maritalStatus);

    @POST("updateUser")
    @FormUrlEncoded
    Observable<String> updateUser(
            @Field("uid") String uid,
            @Field("name") String name,
            @Field("primaryContact") String primaryContact,
            @Field("addressLine1") String addressLine1,
            @Field("addressLine2") String addressLine2,
            @Field("addressLine3") String addressLine3,
            @Field("addressLine4") String addressLine4,
            @Field("DSDivision") String DSDivision,
            @Field("GNDivision") String GNDivision,
            @Field("emergencyContact") String emergencyContact,
            @Field("emergencyContactRelation") String emergencyContactRelation,
            @Field("secondaryContact1") String secondaryContact1,
            @Field("secondaryContact2") String secondaryContact2,
            @Field("dob") String dob,
            @Field("gender") String gender,
            @Field("maritalStatus") String maritalStatus);

    @POST("getUserByUID")
    @FormUrlEncoded
    Observable<String> getUserByUID(
            @Field("uid") String uid);

    @POST("getAllChildUsers")
    @FormUrlEncoded
    Observable<String> getAllChildUsers(
            @Field("pid") String pid);
}
