package com.example.deanmoi.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.deanmoi.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassActivity extends AppCompatActivity {
    EditText CurrentPass , PassChange , PrePass ;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String userId ;
    MaterialButton SuccessBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        CurrentPass = findViewById(R.id.CurrentPass);
        PassChange = findViewById(R.id.PassChange);
        PrePass = findViewById(R.id.PrePass);
        SuccessBtn = findViewById(R.id.SucessBtn);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        user = mAuth.getCurrentUser();
        SuccessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });
        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> startActivity(new Intent(ChangePassActivity.this, LoginActivity.class)));
    }

    private void changePass() {
        String current_pass = CurrentPass.getText().toString();
        String pass_change = PassChange.getText().toString();
        String pre_pass = PrePass.getText().toString();
        boolean check = (current_pass.isEmpty() ||pass_change.isEmpty()||pre_pass.isEmpty() || pass_change.length()<6);
        if(current_pass.isEmpty()) {
            CurrentPass.setError("Không được bỏ trống");
        }
        else if (pass_change.isEmpty()) {
            PassChange.setError("Không được bỏ trống");
        } else if (pre_pass.isEmpty()) {
            PrePass.setError("Không được bỏ trống");
        }
        else if (pass_change.length()<6) {
            PassChange.setError("Nhập mật khẩu trên hoặc bằng 6 ký tự");
        }
        else if (!check) {
            AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),current_pass);
            user.reauthenticate(authCredential)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if(pass_change.equals(pre_pass)){
                                user.updatePassword(pass_change)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(ChangePassActivity.this, "thành công!!!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ChangePassActivity.this,LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }else
                                PrePass.setError("mật khẩu không giống nhau");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            CurrentPass.setError("Mật khẩu hiện tại không đúng");
                        }
                    });
        }
    }


}