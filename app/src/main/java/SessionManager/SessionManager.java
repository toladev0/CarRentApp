package SessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import kh.edu.rupp.carrentapp.LoginActivity;
import models.AuthResponse;
import models.RefreshRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.SupabaseApi;

public class SessionManager {

    private final Context context;
    private final SupabaseApi api;
    private final SharedPreferences prefs;

    public SessionManager(Context context, SupabaseApi api) {
        this.context = context;
        this.api = api;
        prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
    }

    public void saveTokens(String accessToken, String refreshToken) {
        prefs.edit()
                .putString("ACCESS_TOKEN", accessToken)
                .putString("REFRESH_TOKEN", refreshToken)
                .putBoolean("IS_LOGGED_IN", true)
                .apply();
    }

    public String getAccessToken() {
        return prefs.getString("ACCESS_TOKEN", null);
    }

    public String getRefreshToken() {
        return prefs.getString("REFRESH_TOKEN", null);
    }

    public void logout() {
        prefs.edit().clear().apply();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void refreshAccessToken() {

        String refreshToken = getRefreshToken();
        if (refreshToken == null) return;

        RefreshRequest request = new RefreshRequest(refreshToken);

        api.refreshToken(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    AuthResponse auth = response.body();
                    saveTokens(auth.access_token, auth.refresh_token);

                } else {
                    logout();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                logout();
            }
        });
    }
}