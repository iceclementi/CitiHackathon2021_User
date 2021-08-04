package com.example.savelah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputPassword;
    private TextView loginError;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        loginError = findViewById(R.id.loginError);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> login());
        inputPassword.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                login();
                return true;
            } else {
                return false;
            }
        });
    }

    private void login() {
        if (isValidCredentials()) {
            loginError.setVisibility(View.GONE);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            loginError.setVisibility(View.VISIBLE);
        }
    }

    private boolean isValidCredentials() {
        return inputUsername.getText().toString().trim().equals(Constant.USERNAME)
                && inputPassword.getText().toString().trim().equals(Constant.PASSWORD);
    }
}