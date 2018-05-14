package com.anwarabdullahn.polibatamdigitalmading.API;

import com.anwarabdullahn.polibatamdigitalmading.Model.ListBanner;
import com.anwarabdullahn.polibatamdigitalmading.Model.ListBerita;
import com.anwarabdullahn.polibatamdigitalmading.Model.ListEvent;
import com.anwarabdullahn.polibatamdigitalmading.Model.ListKategori;
import com.anwarabdullahn.polibatamdigitalmading.Model.Login;
import com.anwarabdullahn.polibatamdigitalmading.Model.Profile;
import com.anwarabdullahn.polibatamdigitalmading.Request.LoginForm;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by anwarabdullahn on 1/23/18.
 */

public interface ProjectService {

    @POST("mhs/auth/login")
    Call<Login> login(@Body LoginForm body);

    @POST("mhs/auth/register")
    Call<APIResponse> setRegister(@Body RequestBody body);

    @PUT("mhs/auth/logout")
    Call<APIResponse> logout();

    @POST("mhs/auth/password")
    Call<APIResponse> setPassword(@Body RequestBody body);

    @POST("mhs/forget")
    Call<APIResponse> forgot(@Body RequestBody body);

    @GET("mhs/profile")
    Call<Profile> profile();

    @POST("mhs/profile/picture")
    Call<Profile> profileImage(@Body RequestBody body);

    @POST("mhs/profile/update")
    Call<Profile> profileUpdate(@Body RequestBody body);

    @GET("mhs/banner")
    Call<ListBanner> bannerList();

    @GET("mhs/announcement/category")
    Call<ListKategori> getKategori();

    @GET("mhs/announcement/category/{categoryId}")
    Call<ListBerita> getKategoriBy(@Path("categoryId") String categoryId);

    @GET("mhs/event")
    Call<ListEvent> getEventby(@Query("date") String date);

    @GET("mhs/event")
    Call<ListEvent> getEvent();

    @GET("mhs/announcement")
    Call<ListBerita> getBerita();



}
