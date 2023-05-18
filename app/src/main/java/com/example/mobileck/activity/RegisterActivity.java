package com.example.mobileck.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileck.R;
import com.example.mobileck.api.ApiService;
import com.example.mobileck.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.Buffer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText etxt_username;
    EditText etxt_password;
    EditText etxt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Change to Login
        findViewById(R.id.tvLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        // Send request to Register
        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Find field
                etxt_username = findViewById(R.id.etxt_username);
                etxt_password = findViewById(R.id.etxt_password);
                etxt_email = findViewById(R.id.etxt_email);

                // Check empty
                if(etxt_username.getText().toString().isEmpty() || etxt_password.getText().toString().isEmpty() || etxt_email.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please input all values!!!", Toast.LENGTH_SHORT).show();
                } else {
                    // POST to Server
                    ApiService.apiService.sendRegister(new User(0, etxt_username.getText().toString(), etxt_password.getText().toString(), etxt_email.getText().toString(), null))
                            .enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    // Check Exist Account
                                    if(response.errorBody() == null) {
                                        User getUser = response.body();

                                        if(getUser != null) {
                                            // Change Page
                                            Toast.makeText(RegisterActivity.this, "Success register", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        }

                                    } else {
                                        String errorString = null;

                                        // Get Error Value from Server and Toast
                                        try {
                                            errorString = response.errorBody().string();
                                            JSONObject jsonObject = new JSONObject(errorString);
                                            String errorMessage = jsonObject.getString("message");
                                            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();

                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(RegisterActivity.this, "Fail to call Api", Toast.LENGTH_LONG).show();
                                    Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }
}