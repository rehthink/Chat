package com.example.chat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationRequest {
    @Headers({"Content-Type:application/json","Authorization:key=AlzaSyDgdB0xfsam8ClrJHMr2VZbrdjvaYE68Zg\t\n"})
    @POST("send")
    Call<NotificationResponce> sent(@Body NotificationReq req);

}
