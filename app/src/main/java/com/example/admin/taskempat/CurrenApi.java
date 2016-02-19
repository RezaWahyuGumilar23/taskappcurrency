package com.example.admin.taskempat;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Admin on 17/02/2016.
 */
public interface CurrenApi {
    @POST("/data")
    Call<Incomee> postData(@Body Incomee data);
}
