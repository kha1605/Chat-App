package com.example.chattingapp.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattingapp.R;
import com.example.chattingapp.databinding.ItemContainerUserBinding;
import com.example.chattingapp.listeners.UserListener;
import com.example.chattingapp.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private final List<User> users;
    private  final UserListener userListener;

    public UsersAdapter(List<User> users, UserListener userListener) {
        this.users = users;
        this.userListener = userListener;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        ItemContainerUserBinding binding;

        UserViewHolder(ItemContainerUserBinding itemContainerUserBinding) {
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;
        }

        void setUserData(User user) {
            binding.textName.setText(user.name);
            binding.textmail.setText(user.email);
            Bitmap userImage = getUserImage(user.image);
            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
            if (userImage != null) {
                binding.imageProfile.setImageBitmap(userImage);
            } else {
                binding.imageProfile.setImageResource(R.drawable.background_image); // Hình ảnh mặc định
            }

        }
        }

        private Bitmap getUserImage(String encodedImage) {
            if (encodedImage == null || encodedImage.isEmpty()) {
                Log.e("UsersAdapter", "Encoded image is null or empty");
                return null; // Hoặc trả về hình ảnh mặc định
            }

            try {
                // Giải mã chuỗi Base64 thành mảng byte
                byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
                // Chuyển đổi mảng byte thành Bitmap
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            } catch (IllegalArgumentException e) {
                Log.e("UsersAdapter", "Error decoding image", e);
                return null; // Hoặc trả về hình ảnh mặc định
            }
        }

}