package kh.edu.rupp.carrentapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SetupActivity extends AppCompatActivity {

    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkLoginStatus()) {
            finish();
            return;
        }

        setContentView(R.layout.activity_setup);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(view -> openLoginActivity());
        registerButton.setOnClickListener(view -> openRegisterActivity());
    }

    private boolean checkLoginStatus() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("IS_LOGGED_IN", false);

        if (isLoggedIn) {
            openMainActivity();
            return true;
        }
        return false;
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void openLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void openRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}