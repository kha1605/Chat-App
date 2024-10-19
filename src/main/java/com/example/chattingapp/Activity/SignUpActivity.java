package com.example.chattingapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chattingapp.databinding.ActivitySignUpBinding;
import com.example.chattingapp.utilities.Contants;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;



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
        binding.buttonSignup.setOnClickListener( v ->{
            if(isValidSignUpDetails()){
                signup();
            }
        });
        binding.layoutImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }
    private  void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }
    private void signup(){
            loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Contants.KEY_NAME, binding.inputName.getText().toString());
        user.put(Contants.KEY_EMAIL, binding.inputemail.getText().toString());
        user.put(Contants.KEY_PASSWORD, binding.inputpassword.getText().toString());
        user.put(Contants.KEY_IMAGE, encodedImage );
        database.collection(Contants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {

                })
                .addOnFailureListener(exception -> {

                });



    }

    private  String encodeImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth /bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private  final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if(result.getResultCode() ==RESULT_OK){
                    if(result.getData() !=null){
                        Uri imageuri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageuri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.imageProfile.setImageBitmap(bitmap);
                            binding.textAddImage.setVisibility(View.GONE);
                            encodedImage = encodeImage(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            }

    );
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
            
        } else {
            return  true ;
        }
    }
    private void loading(Boolean isLoading){
        if(isLoading){
            binding.buttonSignup.setVisibility(View.INVISIBLE);
            binding.progressbar.setVisibility(View.VISIBLE);

        }else {
            binding.progressbar.setVisibility(View.INVISIBLE);
            binding.buttonSignup.setVisibility(View.VISIBLE);
        }
    }
}