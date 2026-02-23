package kh.edu.rupp.carrentapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check login AFTER super
        if (checkLoginStatus()) {
            return; // stop executing if redirected
        }

        setContentView(R.layout.activity_setup);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(view -> openLoginActivity());
        registerButton.setOnClickListener(view -> openRegisterActivity());
    }

    // Return true if redirected
    private boolean checkLoginStatus() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("IS_LOGGED_IN", false);

        if (isLoggedIn) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // VERY IMPORTANT
            return true;
        }

        return false;
    }

    private void openLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void openRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}