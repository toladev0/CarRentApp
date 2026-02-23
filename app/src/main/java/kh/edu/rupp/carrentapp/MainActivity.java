package kh.edu.rupp.carrentapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import SessionManager.SessionManager;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.ApiKey;
import services.SupabaseApi;

public class MainActivity extends AppCompatActivity {

    Button logoutButton;
    TextView helloTextView;
    SessionManager sessionManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutButton = findViewById(R.id.logoutButton);
        helloTextView = findViewById(R.id.helloTextView);

        initSessionManager();
        showUserName();
        setLogoutButton();
    }

    @SuppressLint("SetTextI18n")
    private void showUserName(){
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String name = prefs.getString("FULL_NAME", "User");
        helloTextView.setText("Hello, " + name + "!");
    }

    private void initSessionManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiKey.PROJECT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SupabaseApi api = retrofit.create(SupabaseApi.class);
        sessionManager = new SessionManager(this);
    }

    private void setLogoutButton(){
        logoutButton.setOnClickListener(view -> sessionManager.logout());
    }
}