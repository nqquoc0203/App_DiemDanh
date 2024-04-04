package com.example.deanmoi.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deanmoi.HistoryAdapter;
import com.example.deanmoi.Model.LichSu;
import com.example.deanmoi.R;
import com.example.deanmoi.databinding.FragmentHistoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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

    HistoryAdapter historyAdapter;
    FirebaseFirestore fb;
    FirebaseAuth auth;
    FirebaseUser user;
    ArrayList<LichSu> lichSuArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHistoryBinding binding = FragmentHistoryBinding.inflate(getLayoutInflater());
        fb = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        lichSuArrayList = new ArrayList<>();
        showData();
        historyAdapter = new HistoryAdapter(lichSuArrayList);
        binding.rvHistory.setAdapter(historyAdapter);
        binding.rvHistory.setHasFixedSize(true);
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        return binding.getRoot();
    }

    public void showData() {
        fb.collection("SinhVien").document(user.getEmail()).collection("LichSu").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        lichSuArrayList.clear();
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            LichSu lichSu = new LichSu(snapshot.getString("date"), snapshot.getString("name"), snapshot.getString("room"), snapshot.getString("time"));
                            lichSuArrayList.add(lichSu);
                        }
                        historyAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}