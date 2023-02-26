package com.ucscieee.madhack.agon.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ucscieee.madhack.agon.R;

import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Objects.requireNonNull(getSupportActionBar()).hide();

        findViewById(R.id.btnSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignInScreen();
            }
        });

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUpScreen();
            }
        });
    }

    private void goToSignUpScreen() {
        Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void goToSignInScreen() {
        Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
        startActivity(intent);
    }
}
