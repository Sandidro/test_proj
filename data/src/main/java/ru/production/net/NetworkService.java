package ru.production.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService mInstance;
    private static final String BASE_URL = "https://api.github.com/";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public RestApi getGithubApi() {
        return mRetrofit.create(RestApi.class);
    }

}
