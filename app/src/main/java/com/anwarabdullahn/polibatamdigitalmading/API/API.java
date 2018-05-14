package com.anwarabdullahn.polibatamdigitalmading.API;

import android.util.Log;

import com.anwarabdullahn.polibatamdigitalmading.Model.Mahasiswa;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anwarabdullahn on 1/23/18.
 */

public class API {
//    development   10.0.2.2:8000/api/v1/
//    production    http://digitalmading.xyz/api/v1/

    private static final String ROOT_URL = "http://digitalmading.xyz/api/v1/";
    private static final String TOKEN = "TOKEN";
    private static final String USER = "USER";
    private static ProjectService SERVICE;


    private static Converter<ResponseBody, APIError> ERROR_CONVERTER;

    public static ProjectService service() {
        if (SERVICE == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(25, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            String token = Hawk.get(TOKEN, "");
                            Request original = chain.request();
                            Request request = original.newBuilder()
                                    .addHeader("Cache-Control", "no-cache")
                                    .addHeader("Authorization", token)
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ERROR_CONVERTER = retrofit.responseBodyConverter(APIError.class, new Annotation[0]);

            SERVICE = retrofit.create(ProjectService.class);
        }

        return SERVICE;

    }

    static Converter<ResponseBody, APIError> getErrorConverter() {
        return ERROR_CONVERTER;
    }

    public static Mahasiswa currentUser() {
        return Hawk.get(USER);
    }

    public static void setCurrentUser(Mahasiswa user) {
        Hawk.put(USER, user);
    }
    public static void removeCurrentUser() {
        Hawk.delete(USER);
    }
    public static void setToken(String token) {
        Hawk.put(TOKEN, token);
    }

    public static void removeToken() {
        Hawk.delete(TOKEN);
    }

    public static boolean isLoggedIn() {
        String token = Hawk.get(TOKEN);
        Log.e("token", token + "");
        return token != null;
    }

    public static void logOut() {
        setCurrentUser(null);
        setToken(null);
        removeCurrentUser();
        removeToken();
    }
}
