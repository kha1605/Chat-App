package com.example.chattingapp.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chattingapp.R;
import com.example.chattingapp.databinding.ActivityChatBinding;
import com.example.chattingapp.models.User;
import com.example.chattingapp.utilities.Contants;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private User recevierUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadRecevierDetails();
        setListeners();

    }
    private void   loadRecevierDetails(){
        recevierUser =(User) getIntent().getSerializableExtra(Contants.KEY_USER);
        binding.textName.setText(recevierUser.name);

    }
    private void setListeners(){
        binding.imageback.setOnClickListener(v ->onBackPressed() );
    }
}