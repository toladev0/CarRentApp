package kh.edu.rupp.carrentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = new Intent(this, MainActivity.class);

        Button signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(view -> {
            startActivity(intent);
            finish();
        });
    }
}
