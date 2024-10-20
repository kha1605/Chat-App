package com.example.chattingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chattingapp.adapter.UsersAdapter;
import com.example.chattingapp.databinding.ActivityUserBinding;
import com.example.chattingapp.listeners.UserListener;
import com.example.chattingapp.models.User;
import com.example.chattingapp.utilities.Contants;
import com.example.chattingapp.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements UserListener {
    private ActivityUserBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();
    }
    private void  setListeners(){
        binding.imageback.setOnClickListener(v-> onBackPressed());
    }
    private void getUsers() {
        loading(true); // Bật thanh tiến trình khi bắt đầu lấy dữ liệu
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Contants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    loading(false); // Tắt thanh tiến trình khi dữ liệu được lấy về
                    String currentUserID = preferenceManager.getString(Contants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<User> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            if (currentUserID.equals(queryDocumentSnapshot.getId())) {
                                continue; // Bỏ qua người dùng hiện tại
                            }
                            User user = new User();
                            user.name = queryDocumentSnapshot.getString(Contants.KEY_NAME);
                            user.email = queryDocumentSnapshot.getString(Contants.KEY_EMAIL);
                            user.image = queryDocumentSnapshot.getString(Contants.KEY_IMAGE);
                            user.token = queryDocumentSnapshot.getString(Contants.KEY_FCM_TOKEN);
                            users.add(user);
                        }
                        if (users.size() > 0) {
                            UsersAdapter usersAdapter = new UsersAdapter(users,this);
                            binding.userRecylerview.setAdapter(usersAdapter);
                            binding.userRecylerview.setVisibility(View.VISIBLE);
                            binding.textErrormessage.setVisibility(View.GONE); // Ẩn thông báo lỗi nếu có dữ liệu
                        } else {
                            showErrormessage();
                        }
                    } else {
                        showErrormessage();
                    }
                });
    }

    private void showErrormessage(){
        binding.textErrormessage.setText(String.format("%s","No user available"));
        binding.textErrormessage.setVisibility(View.VISIBLE);
    }
    private void loading(Boolean isloading){
        if(isloading){
            binding.progressbar.setVisibility(View.VISIBLE);

        }else {
            binding.progressbar.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Contants.KEY_USER, user);
        startActivity(intent);
        finish();
    }
}