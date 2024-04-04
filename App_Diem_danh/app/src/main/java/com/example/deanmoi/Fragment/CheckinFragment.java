package com.example.deanmoi.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.deanmoi.Model.LichSu;
import com.example.deanmoi.Model.MonHoc;
import com.example.deanmoi.R;
import com.example.deanmoi.databinding.FragmentCheckinBinding;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckinFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckinFragment newInstance(String param1, String param2) {
        CheckinFragment fragment = new CheckinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser user;
    ArrayList<MonHoc> monHocArrayList;
    boolean checkHistory ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCheckinBinding binding = FragmentCheckinBinding.inflate(getLayoutInflater());
        monHocArrayList = new ArrayList<>();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        ArrayList<LichSu> lichSuArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db.collection("SinhVien").document(user.getEmail()).collection("LichSu").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        lichSuArrayList.clear();
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            LichSu lichSu = new LichSu(snapshot.getString("date"), snapshot.getString("name"), snapshot.getString("room"), snapshot.getString("time"));
                            lichSuArrayList.add(lichSu);
                        }
                        for (LichSu lichSu : lichSuArrayList) {
                            String dateStartHistory = lichSu.getTime();
                            String dateStop = time;
                            String dateNow = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                            Date d3 = null;
                            Date d4 = null;
                            try {
                                d3 = format.parse(dateStartHistory);
                                d4 = format.parse(dateStop);
                            } catch (ParseException e) {
                            }
                            long diffHistory = d4.getTime() - d3.getTime();
                            long diffMinutes = diffHistory / (60 * 1000);
                            if (diffMinutes <45  && lichSu.getDate().equals(dateNow)) {
                                checkHistory = true;
                            }
                            else {
                                checkHistory = false;
                            }
                        }
                    }
                });
        if (!checkHistory) {
            db.collection("SinhVien").document(user.getEmail()).collection(day)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            monHocArrayList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MonHoc monHoc = new MonHoc(document.getId(), document.getString("name"), document.getString("teacher"), document.getString("room"));
                                monHocArrayList.add(monHoc);
                            }
                            boolean check = false;
                            for (MonHoc monHoc : monHocArrayList) {
                                String dateStart = monHoc.getTime();
                                String dateStop = time;
                                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                Date d1 = null;
                                Date d2 = null;
                                try {
                                    d1 = format.parse(dateStart);
                                    d2 = format.parse(dateStop);
                                } catch (ParseException e) {
                                }
                                long diff = d2.getTime() - d1.getTime();
                                long diffMinutes = diff / (60 * 1000);
                                if (diffMinutes < 5 && diffMinutes>0) {
                                    binding.checkin.setVisibility(View.VISIBLE);
                                    binding.tvSubName.setText(monHoc.getName());
                                    binding.tvSubTeacher.setText(monHoc.getTeacher());
                                    binding.tvSubRoom.setText(monHoc.getRoom());
                                    binding.none.setVisibility(View.GONE);
                                    CountDownTimer timer;
                                    timer = new CountDownTimer(5 * 60 * 1000 - diffMinutes * 1000 * 60, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            binding.tvTime.setText(String.valueOf(millisUntilFinished / 1000));
                                            binding.btnCheckin.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                                    String date = simpleDateFormat.format(new Date());
                                                    if (!checkHistory) {
                                                        LichSu lichSu = new LichSu(date, monHoc.getName(), monHoc.getRoom(), time);
                                                        HashMap<String, Object> map = new HashMap<>();
                                                        map.put("date", lichSu.getDate());
                                                        map.put("name", lichSu.getName());
                                                        map.put("room", lichSu.getRoom());
                                                        map.put("time", lichSu.getTime());
                                                        String id = UUID.randomUUID().toString();
                                                        db.collection("SinhVien").document(user.getEmail()).collection("LichSu").document(id)
                                                                .set(map)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            Toast.makeText(getActivity(), "Điểm danh thành công", Toast.LENGTH_SHORT).show();
                                                                            FragmentManager fm = getActivity().getSupportFragmentManager();
                                                                            FragmentTransaction ft = fm.beginTransaction();
                                                                            ft.replace(R.id.fragment, new HistoryFragment());
                                                                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                                                            ft.commit();
                                                                        }
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(getActivity(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    } else {
                                                        Toast.makeText(getActivity(), "Điểm danh thất bại", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                        @Override
                                        public void onFinish() {
                                            binding.checkin.setVisibility(View.GONE);
                                            binding.none.setVisibility(View.VISIBLE);
                                        }
                                    }.start();
                                    check = true;
                                }
                                if (check) break;
                            }
                            if (!check) {
                                binding.checkin.setVisibility(View.GONE);
                                binding.none.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
        else {
            binding.checkin.setVisibility(View.GONE);
            binding.none.setVisibility(View.GONE);
        }
        return binding.getRoot();
    }
}