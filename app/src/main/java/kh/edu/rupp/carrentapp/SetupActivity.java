package kh.edu.rupp.carrentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SetupActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Intent intent = new Intent(this, LoginActivity.class);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            startActivity(intent);
            finish();
        });
        }
    }
