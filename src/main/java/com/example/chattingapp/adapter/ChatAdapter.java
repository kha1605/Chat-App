package com.example.chattingapp.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattingapp.databinding.ItemContainerReceviedMesageBinding;
import com.example.chattingapp.databinding.ItemContainerSentMessageBinding;
import com.example.chattingapp.databinding.ItemContainerUserBinding;
import com.example.chattingapp.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> chatmessage ;
    private final Bitmap receiverProfileImage ;
    private final String senderID;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED =2;

    public ChatAdapter(List<ChatMessage> chatmessage, Bitmap receiverProfileImage, String senderID) {
        this.chatmessage = chatmessage;
        this.receiverProfileImage = receiverProfileImage;
        this.senderID = senderID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType ==VIEW_TYPE_SENT){
            return new SentMessageViewHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }else{
            return new ReceiviedMessageViewHoler(
                    ItemContainerReceviedMesageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT){
            ((SentMessageViewHolder) holder) .setData(chatmessage.get(position));
        }else{
            ((ReceiviedMessageViewHoler) holder).setData(chatmessage.get(position), receiverProfileImage);
        }

    }

    @Override
    public int getItemCount() {
        return chatmessage.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatmessage.get(position).senderID.equals(senderID)){
            return VIEW_TYPE_RECEIVED;
        }else{
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerSentMessageBinding binding;

        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding){
            super(itemContainerSentMessageBinding.getRoot());
            binding = itemContainerSentMessageBinding;

        }
        void setData(ChatMessage chatMessage){
            binding.textmessage.setText(chatMessage.message);
            binding.textdatetime.setText(chatMessage.datetime);

        }
    }

    static class ReceiviedMessageViewHoler extends RecyclerView.ViewHolder{
        private final ItemContainerReceviedMesageBinding binding;

        ReceiviedMessageViewHoler(ItemContainerReceviedMesageBinding itemContainerReceviedMesageBinding){
            super((itemContainerReceviedMesageBinding.getRoot()));
            binding = itemContainerReceviedMesageBinding;
        }
        void setData(ChatMessage chatMessage, Bitmap receiverProfileImage){
            binding.textmessage.setText(chatMessage.message);
            binding.textdatetime.setText(chatMessage.datetime);
            binding.imageProfile.setImageBitmap(receiverProfileImage);
        }
    }
}
