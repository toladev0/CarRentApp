package kh.edu.rupp.carrentapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import models.AuthResponse;
import models.RegisterRequest;
import models.UserInsertRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.ApiKey;
import services.SupabaseApi;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextFullName;
    EditText editTextPhoneNumber;
    EditText editTextEmailAddress;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    Button signUpButton;
    TextView alreadyHaveAccountLink;
    ImageView googleRegisterIcon;
    ImageView facebookRegisterIcon;
    ImageView appleIdRegisterIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        signUpButton = findViewById(R.id.signUpButton);
        alreadyHaveAccountLink = findViewById(R.id.alreadyHaveAccountLink);
        googleRegisterIcon = findViewById(R.id.googleRegisterIcon);
        facebookRegisterIcon = findViewById(R.id.facebookRegisterIcon);
        appleIdRegisterIcon = findViewById(R.id.appleIdRegisterIcon);

        editTextFullName.requestFocus();

        signUpButton.setOnClickListener(view -> {
            String fullName = editTextFullName.getText().toString();
            String phoneNumber = editTextPhoneNumber.getText().toString();
            String email = editTextEmailAddress.getText().toString();
            String password = editTextPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();

            if (editTextFullName.getText().toString().isEmpty()) {
                editTextFullName.setError("Full name is required");
                editTextFullName.requestFocus();
            }
            else if (editTextPhoneNumber.getText().toString().isEmpty()) {
                editTextPhoneNumber.setError("Phone number is required");
                editTextPhoneNumber.requestFocus();
            }
            else if(email.isEmpty()) {
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
            else if (confirmPassword.isEmpty()) {
                editTextConfirmPassword.setError("Confirm password is required");
                editTextConfirmPassword.requestFocus();
            }
            else if (confirmPassword.length() < 8) {
                editTextPassword.setError("Password must be at least 8 characters");
                editTextPassword.requestFocus();
            }
            else if (!password.equals(confirmPassword)) {
                editTextPassword.setError("Password does not match");
                editTextConfirmPassword.setError("Password does not match");
            }
            else {
                registerUser(fullName, phoneNumber, email, password);
            }
        });

        alreadyHaveAccountLink.setOnClickListener(view -> openLoginActivity());
        googleRegisterIcon.setOnClickListener(view -> openLink("https://www.google.com"));
        facebookRegisterIcon.setOnClickListener(view -> openLink("https://www.facebook.com"));
        appleIdRegisterIcon.setOnClickListener(view -> openLink("https://www.apple.com"));
    }

    private void registerUser(String fullName, String phoneNumber, String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiKey.PROJECT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SupabaseApi api = retrofit.create(SupabaseApi.class);

        RegisterRequest request = new RegisterRequest(fullName, phoneNumber, email, password);

        api.registerUser(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String userId = response.body().user.id;
                    String accessToken = response.body().access_token;

                    UserInsertRequest newUser =
                            new UserInsertRequest(
                                    userId,
                                    email,
                                    fullName,
                                    phoneNumber,
                                    "user"
                            );

                    api.insertUser("Bearer " + accessToken, newUser)
                            .enqueue(new Callback<>() {

                                @Override
                                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this,
                                                "Registration Successful!",
                                                Toast.LENGTH_LONG).show();
                                        openLoginActivity();
                                    } else {
                                        Toast.makeText(RegisterActivity.this,
                                                "Insert into users failed",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                    Toast.makeText(RegisterActivity.this,
                                            "Database Error: " + t.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this,
                            "Signup failed",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                Toast.makeText(RegisterActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void openLink(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
