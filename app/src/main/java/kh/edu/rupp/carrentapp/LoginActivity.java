package kh.edu.rupp.carrentapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import models.AuthResponse;
import models.LoginRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.ApiKey;
import services.SupabaseApi;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmailAddress;
    EditText editTextPassword;
    Button signInButton;
    TextView forgotPasswordLink;
    TextView createAccountLink;
    ImageView googleLoginIcon;
    ImageView facebookLoginIcon;
    ImageView appleIdLoginIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        signInButton = findViewById(R.id.signInButton);
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink);
        createAccountLink = findViewById(R.id.createAccountLink);
        googleLoginIcon = findViewById(R.id.googleLoginIcon);
        facebookLoginIcon = findViewById(R.id.facebookLoginIcon);
        appleIdLoginIcon = findViewById(R.id.appleIdLoginIcon);

        editTextEmailAddress.requestFocus();

        signInButton.setOnClickListener(view -> {
            String email = editTextEmailAddress.getText().toString();
            String password = editTextPassword.getText().toString();

            if (email.isEmpty()) {
                editTextEmailAddress.setError("Email is required");
                editTextEmailAddress.requestFocus();
            }
            else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmailAddress.setError("Invalid email address");
                editTextEmailAddress.requestFocus();
            }
            else if (password.isEmpty()) {
                editTextPassword.setError("Password is required");
                editTextPassword.requestFocus();
            }
            else if (password.length() < 8) {
                editTextPassword.setError("Password must be at least 8 characters");
                editTextPassword.requestFocus();
            }
            else {
                login(email, password);
            }
        });

        forgotPasswordLink.setOnClickListener(view -> openForgotPasswordActivity());
        createAccountLink.setOnClickListener(view -> openRegisterActivity());
        googleLoginIcon.setOnClickListener(view -> openLink("https://www.google.com"));
        facebookLoginIcon.setOnClickListener(view -> openLink("https://www.facebook.com"));
        appleIdLoginIcon.setOnClickListener(view -> openLink("https://www.apple.com"));
    }

    private void login(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiKey.PROJECT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SupabaseApi api = retrofit.create(SupabaseApi.class);

        LoginRequest request = new LoginRequest(email, password);

        api.login(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse auth = response.body();

                    String accessToken = auth.access_token;
                    String refreshToken = auth.refresh_token;
                    String name = auth.user.name;

                    SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("FULL_NAME", name);
                    editor.putString("ACCESS_TOKEN", accessToken);
                    editor.putString("REFRESH_TOKEN", refreshToken);
                    editor.putBoolean("IS_LOGGED_IN", true);

                    editor.apply();

                    openMainActivity();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Invalid email or password",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void openForgotPasswordActivity() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void openLink(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
