package com.example.deanmoi.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.View;

import com.example.deanmoi.databinding.ActivitySplashScreenBinding;

import java.util.logging.LogRecord;

public class SplashScreenActivity extends AppCompatActivity {
    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    binding.view2.setVisibility(View.GONE);
                    binding.view1.setVisibility(View.GONE);
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                finally {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}