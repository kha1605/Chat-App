package com.example.chattingapp.Activity;

import static android.app.ProgressDialog.show;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chattingapp.R;
import com.example.chattingapp.databinding.ActivitySignInBinding;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Objects;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySignInBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setListeners();
    }
    private void setListeners(){
        binding.textCreatenewaccount.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class)));
        binding.buttonSignin.setOnClickListener(v -> addDataToFirestore());
    }
    private void addDataToFirestore(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object > data = new HashMap<>();
        data.put("fistname","Kha");
        data.put("lastname","Minha");


        database.collection("users")
                .add(data)
                .addOnSuccessListener(documentReference ->{
                    Toast.makeText(getApplicationContext(), "Data Inserted" , Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(getApplicationContext(),exception.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
}