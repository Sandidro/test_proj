package ru.production.net;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.production.pojo.UserPojo;

public interface RestApi {

    @GET("/users")
    Observable<List<UserPojo>> userList(@Query("since") int since);
}
