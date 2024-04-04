package com.example.deanmoi.UI;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.deanmoi.Fragment.CheckinFragment;
import com.example.deanmoi.Fragment.HistoryFragment;
import com.example.deanmoi.R;
import com.google.android.material.tabs.TabLayout;


public class CheckinActivity extends AppCompatActivity {
    TabLayout tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        tab = findViewById(R.id.tab);
        tab.getTabAt(0);
        loadFragment(new CheckinFragment());
        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        loadFragment(new CheckinFragment());
                        break;
                    case 1:
                        loadFragment( new HistoryFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}