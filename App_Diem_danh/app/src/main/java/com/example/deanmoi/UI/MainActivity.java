package com.example.deanmoi.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;


import com.example.deanmoi.Fragment.HomeFragment;
import com.example.deanmoi.Fragment.ProfileFragment;
import com.example.deanmoi.Fragment.SettingFragment;
import com.example.deanmoi.R;
import com.example.deanmoi.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Integer selectedTab = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadFragment(new HomeFragment());
        binding.homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedTab != 1) {
                    binding.tvProfile.setVisibility(View.GONE);
                    binding.tvSetting.setVisibility(View.GONE);

                    binding.imgProfile.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.white));
                    binding.imgSetting.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.white));

                    binding.profileLayout.setBackgroundResource(R.color.black);
                    binding.settingLayout.setBackgroundResource(R.color.black);

                    binding.tvHome.setVisibility(View.VISIBLE);
                    binding.imgHome.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.black));
                    binding.homeLayout.setBackgroundResource(R.drawable.round_back);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,0);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    binding.homeLayout.startAnimation(scaleAnimation);

                    selectedTab = 1;

                    loadFragment(new HomeFragment());
                }
            }
        });
        binding.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedTab != 2) {
                    binding.tvHome.setVisibility(View.GONE);
                    binding.tvSetting.setVisibility(View.GONE);

                    binding.imgHome.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.white));
                    binding.imgSetting.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.white));

                    binding.homeLayout.setBackgroundResource(R.color.black);
                    binding.settingLayout.setBackgroundResource(R.color.black);

                    binding.tvProfile.setVisibility(View.VISIBLE);
                    binding.imgProfile.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.black));
                    binding.profileLayout.setBackgroundResource(R.drawable.round_back);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,0);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    binding.profileLayout.startAnimation(scaleAnimation);

                    selectedTab = 2;

                    loadFragment(new ProfileFragment());
                }
            }
        });
        binding.settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedTab != 3) {
                    binding.tvProfile.setVisibility(View.GONE);
                    binding.tvHome.setVisibility(View.GONE);

                    binding.imgProfile.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.white));
                    binding.imgHome.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.white));

                    binding.profileLayout.setBackgroundResource(R.color.black);
                    binding.homeLayout.setBackgroundResource(R.color.black);

                    binding.tvSetting.setVisibility(View.VISIBLE);
                    binding.imgSetting.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.black));
                    binding.settingLayout.setBackgroundResource(R.drawable.round_back);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,0);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    binding.settingLayout.startAnimation(scaleAnimation);

                    selectedTab = 3;

                    loadFragment(new SettingFragment());
                }
            }
        });
    }
    public void loadFragment (Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
}