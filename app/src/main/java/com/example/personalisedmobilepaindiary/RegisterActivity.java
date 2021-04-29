package com.example.personalisedmobilepaindiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalisedmobilepaindiary.databinding.ActivityLoginBinding;
import com.example.personalisedmobilepaindiary.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;
    String email;
    String pwd;
    String confirmPwd;
    boolean isEmailValid, isPasswordValid, isConfirmPasswordValid;
    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValidation();
                if (isValid) {
                    auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        intent.putExtra("email", email);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "Register failed: " + task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public Boolean setValidation() {
        email = binding.email.getText().toString();
        pwd = binding.password.getText().toString();
        confirmPwd = binding.confirmPassword.getText().toString();
        // Check for a valid email address.
        if (email.isEmpty()) {
            binding.emailError.setError("Please enter the Email");
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
        if (pwd.isEmpty()) {
            binding.passError.setError("Please enter the password");
            isPasswordValid = false;
            isValid = false;
        } else if (pwd.length() < 6) {
            binding.passError.setError("Password should be longer than six characters");
            isPasswordValid = false;
            isValid = false;
        } else if (!confirmPwd.equals(pwd)) {
            binding.confirmPassError.setError("Please make sure your passwords match");
            isPasswordValid = false;
            isValid = false;
        } else {
            isPasswordValid = true;
            binding.passError.setErrorEnabled(false);
        }

        // Check to confirm password.
        if (pwd.isEmpty()) {
            binding.confirmPassError.setError("Please enter the password");
            isPasswordValid = false;
            isValid = false;
        } else if (pwd.length() < 6) {
            binding.confirmPassError.setError("Password should be longer than six characters");
            isPasswordValid = false;
            isValid = false;
        } else if (!confirmPwd.equals(pwd)) {
            binding.confirmPassError.setError("Please make sure your passwords match");
            isPasswordValid = false;
            isValid = false;
        } else {
            binding.passError.setErrorEnabled(false);
            isConfirmPasswordValid = true;
        }

        if (isEmailValid && isPasswordValid && isConfirmPasswordValid) {
            isValid = true;
        }
        return isValid;
    }
};

