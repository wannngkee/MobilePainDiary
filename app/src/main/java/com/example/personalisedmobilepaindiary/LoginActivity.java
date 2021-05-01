package com.example.personalisedmobilepaindiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.personalisedmobilepaindiary.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    String email;
    String pwd;
    boolean isEmailValid, isPasswordValid;
    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("email")) {
            email = intent.getStringExtra("email");
            binding.email.setText(email);
        }

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValidation();
                if (isValid){
                auth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Login successfully",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("loginEmail", email);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getApplicationContext(),
                                                    "Login failed: " + task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                }

            }
        });
    }

    public boolean setValidation() {
        email = binding.email.getText().toString();
        pwd = binding.password.getText().toString();

        // Check for a valid email address.
        if (email.isEmpty() && email != null) {
            binding.emailError.setError("Please enter an Email");
            isEmailValid = false;
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailError.setError("Invalid email address");
            isEmailValid = false;
            isValid = false;
        } else {
            isEmailValid = true;
            binding.emailError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if ( pwd.isEmpty() && pwd != null) {
            binding.passError.setError("Please enter the password");
            isPasswordValid = false;
            isValid = false;
        } else if (pwd.length() < 6) {
            binding.passError.setError("Password should be longer than six characters");
            isPasswordValid = false;
            isValid = false;
        } else {
            isPasswordValid = true;
            binding.passError.setErrorEnabled(false);
        }

        if (isEmailValid && isPasswordValid) {
            isValid = true;
        }
        return isValid;
    }
}



