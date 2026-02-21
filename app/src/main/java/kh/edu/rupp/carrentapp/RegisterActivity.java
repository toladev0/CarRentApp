package kh.edu.rupp.carrentapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

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

        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        signUpButton = findViewById(R.id.signUpButton);
        alreadyHaveAccountLink = findViewById(R.id.alreadyHaveAccountLink);
        googleRegisterIcon = findViewById(R.id.googleRegisterIcon);
        facebookRegisterIcon = findViewById(R.id.facebookRegisterIcon);
        appleIdRegisterIcon = findViewById(R.id.appleIdRegisterIcon);

        editTextEmailAddress.requestFocus();

        signUpButton.setOnClickListener(view -> {
            String email = editTextEmailAddress.getText().toString();
            String password = editTextPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();

            if(email.isEmpty()) {
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
                Toast.makeText(this, "Email: " + email + "\nPassword: " + password, Toast.LENGTH_SHORT).show();
                openMainActivity();
            }
        });

        alreadyHaveAccountLink.setOnClickListener(view -> openLoginActivity());
        googleRegisterIcon.setOnClickListener(view -> openLink("https://www.google.com"));
        facebookRegisterIcon.setOnClickListener(view -> openLink("https://www.facebook.com"));
        appleIdRegisterIcon.setOnClickListener(view -> openLink("https://www.apple.com"));
    }

    private void openMainActivity() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openLink(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
