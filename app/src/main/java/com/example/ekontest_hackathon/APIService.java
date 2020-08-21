package com.example.ekontest_hackathon;



import com.example.ekontest_hackathon.Notifications.MyResponse;
import com.example.ekontest_hackathon.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "content-type:application/json",
            "Authorization:key=AAAAPzuqfOQ:APA91bHnvh82-LmBgExE8x7q6QtZ9Dd-SpA7N8noBUwN9TMWfkqmkhuAbrMlU45Z_fO2wI97AUsM6DQJy42CLnTGTKyDUwn3D0N1AyxkIoZaMKa0MSCjz08cqTOv6vGRp1cD5E3B-doJ"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}

