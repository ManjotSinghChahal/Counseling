package com.tenserflow.therapist.Webservice;


import com.google.gson.JsonObject;
import com.tenserflow.therapist.model.Booking.BookingListing;
import com.tenserflow.therapist.model.Booking_history.Booking_History;
import com.tenserflow.therapist.model.Detail.DetailListing;
import com.tenserflow.therapist.model.GetPersonalMedical.GetPersonalMedicalDetail;
import com.tenserflow.therapist.model.HomeListing.HomeListing;
import com.tenserflow.therapist.model.MedicalDetail.MedicalDetail;
import com.tenserflow.therapist.model.PersonalDetail.PersonalDetail;
import com.tenserflow.therapist.model.PersonalMedicalDetail.PersonalMedical;
import com.tenserflow.therapist.model.Timeslot.GetTimeslot;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Admin on 12/13/2018.
 */

public interface Service {


    @FormUrlEncoded
    @POST("register")
    Call<JsonObject> register(@Header("auth") String header, @Field("first_name") String first_name, @Field("last_name") String last_name, @Field("email") String email, @Field("password") String password, @Field("phone_no") String phone_no, @Field("address") String address, @Field("dob") String dob,@Field("country_code") String country_code,@Field("flag") String flag);

    @FormUrlEncoded
    @POST("login")
    Call<JsonObject> login(@Header("auth") String header, @Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("forgotPassword")
    Call<JsonObject> forgot(@Header("auth") String header, @Field("email") String email);

    @FormUrlEncoded
    @POST("SocialLogin")
    Call<JsonObject> social_login(@Header("auth") String header, @Field("social_id") String login_type, @Field("login_type") String social_id, @Field("first_name") String first_name,@Field("last_name") String name, @Field("email") String email,@Field("address") String address,@Field("dob") String dob,@Field("password") String password,@Field("phone_no") String phone_no,@Field("user_image") String user_image,@Field("country_code") String country_code,@Field("flag") String flag,@Field("image_type") String  image_type);

    @FormUrlEncoded
    @POST("changePassword")
    Call<JsonObject> change_password(@Header("token") String header, @Field("old_password") String old_password,@Field("new_password") String new_password);


    @Multipart
    @POST("editProfile")
    Call<JsonObject> editProfile(@Header("token") String token, @Part("first_name") RequestBody first_name, @Part("last_name") RequestBody last_name, @Part("email") RequestBody email, @Part("phone_no") RequestBody phone_no, @Part("address") RequestBody address, @Part("dob") RequestBody dob, @Part("user_image") String pic,@Part("country_code") RequestBody country_code,@Part("flag") RequestBody flag,@Part("image_type") RequestBody image_type);

    @Multipart
    @POST("editProfile")
    Call<JsonObject> editProfile1(@Header("token") String token, @Part("first_name") RequestBody first_name, @Part("last_name") RequestBody last_name, @Part("email") RequestBody email, @Part("phone_no") RequestBody phone_no, @Part("address") RequestBody address, @Part("dob") RequestBody dob, @Part MultipartBody.Part image1,@Part("country_code") RequestBody country_code,@Part("flag") RequestBody flag,@Part("image_type") RequestBody image_type);


    @POST("getProfile")
    Call<JsonObject> getProfile(@Header("token") String token);

    @POST("delete_profile_pic")
    Call<JsonObject> delete_profile_img(@Header("token") String token);

    @POST("homelisting")
    Call<HomeListing> homelisting(@Header("token") String token);



    @FormUrlEncoded
    @POST("PersonalDetail")
    Call<PersonalDetail> personalDetail(@Header("token") String token, @Field("sub_cat") String subCat_id, @Field("therapy_id") String thearpy_id, @Field("name") String name_per, @Field("address") String address_per, @Field("city") String city_per, @Field("state") String state_per, @Field("dob") String date_per, @Field("age") String age_per, @Field("email") String email_per, @Field("cell_phone") String cellPhone, @Field("home_phone")String homePhone,@Field("country_code_C")String country_code_C,@Field("country_code_H")String country_code_H,@Field("country_name")String country_name);


    @FormUrlEncoded
    @POST("MedicalDetail")
    Call<MedicalDetail> medicalDetail(@Header("token") String token, @Field("sub_cat") String subCat_id, @Field("therapy_id") String thearpy_id, @Field("contact_number") String contact_number, @Field("father_name") String father_name, @Field("Father_Mental_health_issue") String Father_Mental_health_issue, @Field("mother_name") String mother_name, @Field("Mother_Mental_health_issue") String Mother_Mental_health_issue, @Field("diagonosis") String diagonosis, @Field("issues_message") String issues_message, @Field("theraphy_goal") String theraphy_goal, @Field("any_medical")String any_medical,@Field("country_code")String country_code);

    @FormUrlEncoded
    @POST("detail_listing")
    Call<DetailListing> detailListing(@Header("token") String token,@Field("category_id") String category_id,@Field("therapy_id") String therapy_id);

    @Multipart
    @POST("BookNow")
   // Call<JsonObject> bookingListing(@Header("token") String token,@Part("sub_cat") RequestBody sub_cat, @Part("therapy_id") RequestBody therapy_id,@Part("therapy_type") RequestBody therapy_type,@Part("session_package") RequestBody session_package,@Part("datetime") RequestBody datetime,@Part MultipartBody.Part[] image);
    Call<JsonObject> bookingListing(@Header("token") String token,@Part("sub_cat") RequestBody sub_cat, @Part("therapy_id") RequestBody therapy_id,@Part("therapy_type") RequestBody therapy_type,@Part("session_package") RequestBody session_package,@Part("timeslot_id") RequestBody datetime,@Part MultipartBody.Part ImagesParts_ins,@Part MultipartBody.Part ImagesParts_lic);

    @FormUrlEncoded
    @POST("SavedDetails")
    Call<JsonObject> detailPersonalMediacal(@Header("token") String token, @Field("therapy_id") String therapy_id);

    @POST("BookingHistory")
    Call<Booking_History> bookingHistory(@Header("token") String token);

    @FormUrlEncoded
    @POST("CancelBooking")
    Call<JsonObject> bookingCancel(@Header("token") String token,@Field("booking_id") String bookId);

    @FormUrlEncoded
    @POST("Rating")
    Call<JsonObject> rating(@Header("token") String token,@Field("booking_id") String bookingId,@Field("booking_id") String bookingId1,@Field("rating") String ratingVal,@Field("description") String review_coment);

    @FormUrlEncoded
    @POST("bookingslot")
    Call<GetTimeslot> timeslot(@Header("token") String token, @Field("therapy_id") String therapy_id/*, @Field("subcategory_id") String subcategory_id*/, @Field("date") String date);

    @FormUrlEncoded
    @POST("payment")
    Call<JsonObject> payment(@Header("token") String token,@Field("booking_id") String bookId,@Field("transaction_id") String transaction_id,@Field("amount") String amount,@Field("date") String date);


    @POST("getpolicy1")
    Call<JsonObject> policy_url();

}
