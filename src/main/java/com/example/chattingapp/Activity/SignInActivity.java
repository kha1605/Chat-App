package com.example.chattingapp.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chattingapp.databinding.ActivitySignInBinding;
import java.util.HashMap;

import com.example.chattingapp.utilities.Contants;
import com.example.chattingapp.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.Constants;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private com.example.chattingapp.utilities.PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBoolean(Contants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        binding =ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }
    private void setListeners(){
        binding.textCreatenewaccount.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class)));
        binding.buttonSignin.setOnClickListener(v -> {
            if(isValidSignInDetails()){
                signIn();
            }
        });
    }
    private void signIn(){
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Contants.KEY_COLLECTION_USERS)
                .whereEqualTo(Contants.KEY_EMAIL, binding.inputemail.getText().toString())
                .whereEqualTo(Contants.KEY_PASSWORD, binding.password.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null  && task.getResult().getDocuments().size() >0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Contants.KEY_IS_SIGNED_IN, true);
                        preferenceManager.putString(Contants.KEY_USER_ID, documentSnapshot.getId());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        loading(false);
                        showToast("Unable to sign in");
                    }
                });
    }
    private  void loading(Boolean isLoading){
        if(isLoading){
            binding.buttonSignin.setVisibility(View.INVISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);
        } else {
            binding.buttonSignin.setVisibility(View.VISIBLE);
            binding.progressbar.setVisibility(View.INVISIBLE);
        }
    }
    private  void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private Boolean isValidSignInDetails(){
        if(binding.inputemail.getText().toString().trim().isEmpty()){
            showToast("Enter Email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputemail.getText().toString()).matches()) {
            showToast("Enter valid Email");
            return  false;
            
        } else if (binding.password.getText().toString().trim().isEmpty()) {
            showToast("Enter password");
            return false;
            
        }else{
            return  true;
        }
    }

}