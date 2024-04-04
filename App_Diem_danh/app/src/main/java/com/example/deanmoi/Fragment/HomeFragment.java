package com.example.deanmoi.Fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deanmoi.Model.Notifications;
import com.example.deanmoi.Model.SinhVien;
import com.example.deanmoi.R;
import com.example.deanmoi.UI.CalendarActivity;
import com.example.deanmoi.UI.CheckinActivity;
import com.example.deanmoi.UI.Notification.NotificationActivity;
import com.example.deanmoi.UI.TestActivity;
import com.example.deanmoi.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null ) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private int MAX_NUMBER = 99;
    private int NOTIFICATION_NUMBER_COUNTER = 0;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore db;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("SinhVien").document(user.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        binding.tvName.setText(document.getString("name"));
                        String gender = document.getString("gender");
                        if (gender.equals("Nữ")) {
                            binding.ivAvatar.setImageResource(R.drawable.avatar_girl);
                        }
                        else {
                            binding.ivAvatar.setImageResource(R.drawable.avatar_boy);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayList<Notifications> notifications = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(new Date());
        Notifications n1 = new Notifications("Nhắc nhở:", "Nhớ đổi mật khẩu khi đăng nhập lần đầu.",
                date);
        Notifications n2 = new Notifications("Nhắc nhở:", "Bạn có lịch học vào lúc 9h30 môn Lập Trình Trên Thiết Bị Di Động.",
                date);
        Notifications n3 = new Notifications("GÓC CẢNH GIÁC:", "Các bạn sinh viên HUFLIT chú ý\nHiện nay nhà trường đã ghi nhận và xử lý nhiều trường hợp cá nhân, tổ chức ngoài trường giả làm sinh viên của trường để tiếp cận các bạn sinh viên mời mở thẻ ngân hàng, làm khảo sát, các đối tượng sẽ yêu cầu sinh viên cung cấp thông tin thẻ CCCD, thẻ Sinh viên và các thông tin khác liên quan, để công tác đảm bảo an ninh trong nhà trường được tốt, đề phòng sinh viên bị mất thông tin cá nhân nhằm mục đích vay nợ xấu hoặc mục đích xấu khác, phòng CT-CTSV đề nghị sinh viên thực hiện nghiêm túc.",
                date);
        notifications.add(n1);
        notifications.add(n2);
        notifications.add(n3);
        NOTIFICATION_NUMBER_COUNTER = notifications.size();
        if (NOTIFICATION_NUMBER_COUNTER == 0) {
            binding.badge.setVisibility(View.GONE);
        }
        else {
            if (NOTIFICATION_NUMBER_COUNTER > MAX_NUMBER) {
                binding.tvNotificationNumber.setText("99+");
            }
            else {
                binding.tvNotificationNumber.setText(String.valueOf(NOTIFICATION_NUMBER_COUNTER));
            }
        }

        binding.ivNotification.setOnClickListener(v -> startActivity(new Intent(getActivity(), NotificationActivity.class)));
        binding.cvCheckin.setOnClickListener(v -> startActivity(new Intent(getActivity(), CheckinActivity.class)));
        binding.cvSchedule.setOnClickListener(v -> startActivity(new Intent(getActivity(), CalendarActivity.class)));
        binding.cvTestSchedule.setOnClickListener(v -> startActivity(new Intent(getActivity(), TestActivity.class)));
        return binding.getRoot();
    }
}