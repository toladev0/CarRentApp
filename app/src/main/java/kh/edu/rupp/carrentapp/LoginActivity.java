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
                openMainActivity(email, password);
            }
        });

        forgotPasswordLink.setOnClickListener(view -> openLink("https://www.google.com"));
        createAccountLink.setOnClickListener(view -> openRegisterActivity());
        googleLoginIcon.setOnClickListener(view -> openLink("https://www.google.com"));
        facebookLoginIcon.setOnClickListener(view -> openLink("https://www.facebook.com"));
        appleIdLoginIcon.setOnClickListener(view -> openLink("https://www.apple.com"));
    }

    private void openMainActivity(String email, String password) {

        if (email.equals("admin@example.com") && password.equals("admin123")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
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
