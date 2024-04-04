package com.example.deanmoi.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.deanmoi.ScanBarCodeActivity;
import com.example.deanmoi.Model.SinhVien;
import com.example.deanmoi.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        SplashScreen.installSplashScreen(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnConfirm.setOnClickListener(v -> SignIn());
        binding.barcode.setOnClickListener(v -> startActivity(new Intent(this, ScanBarCodeActivity.class)));
        String idBarcode = getIntent().getStringExtra("id");
        binding.edtID.setText(idBarcode);
    }

    private void SignIn() {
        String email = binding.edtID.getText().toString().trim();
        String pass = binding.edtPass.getText().toString().trim();
        
        binding.layoutId.setErrorEnabled(false);
        binding.layoutPass.setErrorEnabled(false);
        if (email.isEmpty()) {
            binding.layoutId.setError("Vui lòng nhập email");
        }
        if (pass.isEmpty()) {
            binding.layoutPass.setError("Vui lòng nhập mật khẩu");
        }
        else {
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                binding.layoutPass.setError("Sai email hoặc mật khẩu");
                            }
                        }
                    });
        }
    }
}