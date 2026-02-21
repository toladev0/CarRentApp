package kh.edu.rupp.carrentapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmailAddress;
    EditText editTextPassword;
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

        Button signInButton = findViewById(R.id.signInButton);
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink);
        createAccountLink = findViewById(R.id.createAccountLink);
        googleLoginIcon = findViewById(R.id.googleLoginIcon);
        facebookLoginIcon = findViewById(R.id.facebookLoginIcon);
        appleIdLoginIcon = findViewById(R.id.appleIdLoginIcon);

        editTextEmailAddress.requestFocus();

        signInButton.setOnClickListener(view -> {
            String username = editTextEmailAddress.getText().toString();
            String password = editTextPassword.getText().toString();

            if (!username.isEmpty() && !password.isEmpty())
                openMainActivity(username, password);
            else {
                editTextEmailAddress.setError("Email is required");
                editTextPassword.setError("Password is required");
                editTextEmailAddress.requestFocus();
            }
        });

        forgotPasswordLink.setOnClickListener(view -> openLink("https://www.google.com"));
        createAccountLink.setOnClickListener(view -> openRegisterActivity());
        googleLoginIcon.setOnClickListener(view -> openLink("https://www.google.com"));
        facebookLoginIcon.setOnClickListener(view -> openLink("https://www.facebook.com"));
        appleIdLoginIcon.setOnClickListener(view -> openLink("https://www.apple.com"));
    }

    private void openMainActivity(String username, String password) {

        if (username.equals("admin") && password.equals("admin123")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
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
