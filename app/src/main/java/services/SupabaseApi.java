package services;

import models.AuthResponse;
import models.LoginRequest;
import models.RefreshRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SupabaseApi {

    @Headers({
            "Content-Type: application/json",
            "apikey: " + ApiKey.API_KEY
    })
    @POST("auth/v1/token?grant_type=password")
    Call<AuthResponse> login(@Body LoginRequest request);
    @POST("auth/v1/token?grant_type=refresh_token")
    Call<AuthResponse> refreshToken(@Body RefreshRequest request);
}