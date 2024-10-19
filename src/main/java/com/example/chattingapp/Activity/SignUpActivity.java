package com.example.chattingapp.Activity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chattingapp.R;
import com.example.chattingapp.databinding.ActivitySignInBinding;
import com.example.chattingapp.databinding.ActivitySignUpBinding;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
    private void setListeners(){
        binding.textSignIn.setOnClickListener(v -> onBackPressed());
    }
    private  void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }
    private void signup(){

    }
    private Boolean isValidSignUpDetails(){
        if(encodedImage == null){
            showToast("Select profile Image");
            return false;
        } else if (binding.inputName.getText().toString(). trim().isEmpty()) {

            showToast("Enter name");
            return  false;
            
        } else if (binding.inputemail.getText().toString().trim().isEmpty()) {
            showToast("Enter email");
            return false  ;
            
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputemail.getText().toString()).matches()) {
            showToast("Enter valid email");
            return false ;

            
        } else if (binding.inputpassword.getText().toString().trim().isEmpty()) {
            showToast("Enter Password");
            return false ;

        } else if (binding.inputconfirmPassword.getText().toString().trim().isEmpty()) {
            showToast("confirm your password");
            return  false ;
            
        } else if (!binding.inputpassword.getText().toString().equals(binding.inputconfirmPassword.getText().toString())) {
            showToast("password & confirm password must be same");
            return  false ;
            
        }
    }
}