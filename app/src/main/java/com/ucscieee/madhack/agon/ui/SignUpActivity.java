package com.ucscieee.madhack.agon.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ucscieee.madhack.agon.R;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.util.Map;
import java.util.HashMap;
import com.android.volley.AuthFailureError;
import java.util.UUID;



import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    // declare a request queue variable
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // initialize the request queue
        mQueue = Volley.newRequestQueue(this);

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText firstnameEditText = findViewById(R.id.first_name_edittext);
                EditText lastnameEditText = findViewById(R.id.last_name_edittext);
                EditText emailEditText = findViewById(R.id.email_edittext);
                EditText phoneEditText = findViewById(R.id.phone_edittext);
                EditText passwordEditText = findViewById(R.id.password_edittext);

                UUID uuid = UUID.randomUUID(); // generate a UUID
                String fuckyouid=uuid.toString();
                String first = firstnameEditText.getText().toString();
                String last = lastnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                boolean isValid = true;

                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid email format");
                    isValid = false;
                }

                if (phone.length() != 10) {
                    phoneEditText.setError("Phone number should have 10 digits");
                    isValid = false;
                }

                if (!isValidPassword(password)) {
                    passwordEditText.setError("Password should have at least 6 characters, including an uppercase letter, a special character and a number");
                    isValid = false;
                }

                if (isValid) {
                    // proceed with sign up
                    signUp(fuckyouid,first,last,email, phone, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Please fix the errors in the form", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // email validation function
    private static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    // password validation function
    private static boolean isValidPassword(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    private void signUp(String fuckyouid,String first,String last, String email, String phone, String password) {
        String url = "https://agon.madhack.ucscieee.com/api/auth/register";
        String accessKey = "c4791105-90a3-491d-bde3-42d01ab5d287";

        // create POST request body
        Map<String, String> params = new HashMap<>();

        params.put("userId",fuckyouid );
        params.put("firstName",first );
        params.put("lastName", last);
        params.put("email", email);
        params.put("password", password);
        params.put("contactNo", phone);
        params.put("avatarUrl", null);

        JSONObject jsonBody = new JSONObject(params);

        // create Volley request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // handle successful response
                        Toast.makeText(getApplicationContext(), "Sign up successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error response
                        String errorMsg = "Sign up failed. Please try again later.";
                        if (error.networkResponse != null) {
                            errorMsg = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        }
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                }) {
            // set API access key in request headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("X-API-Key", "c4791105-90a3-491d-bde3-42d01ab5d287");
                return headers;
            }
        };

        // add request to Volley request queue
        mQueue.add(request);
    }}

