package com.example.deanmoi.UI.Notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.deanmoi.Fragment.HomeFragment;
import com.example.deanmoi.Model.LichSu;
import com.example.deanmoi.Model.Notifications;
import com.example.deanmoi.R;
import com.example.deanmoi.UI.MainActivity;
import com.example.deanmoi.UI.Notification.NotificationAdapter;
import com.example.deanmoi.databinding.ActivityNotificationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {
    ActivityNotificationBinding binding;
    NotificationAdapter notificationAdapter;
    ArrayList<Notifications> notifications;
    FirebaseFirestore fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        notifications = new ArrayList<>();
        fb = FirebaseFirestore.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(new Date());
        showData();
        notificationAdapter = new NotificationAdapter(notifications);
        binding.rvNoti.setAdapter(notificationAdapter);
        binding.rvNoti.setHasFixedSize(true);
        binding.rvNoti.setLayoutManager(new LinearLayoutManager(this));
        binding.ivBack.setOnClickListener(v -> finish());
    }
    public void showData() {
        fb.collection("ThongBao").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        notifications.clear();
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            Notifications notifications1 = new Notifications(snapshot.getString("title"), snapshot.getString("desc"), snapshot.getString("date"));
                            notifications.add(notifications1);
                        }
                        notificationAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NotificationActivity.this, "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}