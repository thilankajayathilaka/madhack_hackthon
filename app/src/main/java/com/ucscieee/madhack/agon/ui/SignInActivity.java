package com.ucscieee.madhack.agon.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ucscieee.madhack.agon.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView linkSignUp = findViewById(R.id.linkSignUp);
        Button btnSignIn = findViewById(R.id.btnSignIn);

        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUpScreen();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailText = v.findViewById(R.id.textFieldSignInEmail);
                EditText passwordText = v.findViewById(R.id.textFieldSignInPassword);
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                if(email.equals("")){
                    Toast.makeText(getApplicationContext(),"Email is required", Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.equals("")){
                    Toast.makeText(getApplicationContext(),"Password is required", Toast.LENGTH_LONG).show();
                    return;
                }
                login(email, password);
                //goToMainScreen();
            }
        });
    }

    protected void login(String email, String password){

        String url = "https://agon.madhack.ucscieee.com/api/auth/login";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject userData = new JSONObject();

        try {
            userData.put("email", email);
            userData.put("password", password);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("response" + response);
                        try {
                            String status = response.getString("message");

                            if(status.equals("User logged in successfully")){
                                goToMainScreen();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Invalid email or password", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        error.printStackTrace();
                        System.out.println("response" + error.toString());

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("X-API-Key", "c4791105-90a3-491d-bde3-42d01ab5d287");
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    protected void goToSignUpScreen() {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    protected void goToMainScreen() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
