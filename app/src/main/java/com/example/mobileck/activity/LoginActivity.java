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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etxt_username;
    EditText etxt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Change to Register
        findViewById(R.id.tvRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // Send request to Login
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Find field
                etxt_username = findViewById(R.id.etxt_username);
                etxt_password = findViewById(R.id.etxt_password);

                // Check empty
                if(etxt_username.getText().toString().isEmpty() || etxt_password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please input all values!!!", Toast.LENGTH_SHORT).show();
                } else {
                    // POST to Server
                    ApiService.apiService.sendLogin(new User(0, etxt_username.getText().toString(), etxt_password.getText().toString(), null, null))
                            .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
//                            Toast.makeText(LoginActivity.this, "Success to call Api", Toast.LENGTH_LONG).show();

                            // Check Exist Account
                            if(response.errorBody() == null) {
                                User getUser = response.body();

                                if(getUser != null) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("user", getUser);

                                    // Change Page
                                    startActivity(intent);
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong Password or Account doesn't exist!!!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Fail to call Api", Toast.LENGTH_LONG).show();
                            Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}