package com.example.deanmoi.UI;

import static com.example.deanmoi.UI.CalendarUtils.daysInMonthArray;
import static com.example.deanmoi.UI.CalendarUtils.monthYearFromDate;
import static com.example.deanmoi.UI.CalendarUtils.selectedDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deanmoi.Model.MonHoc;
import com.example.deanmoi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    private TextView TimeEventCell;
    private TextView HomNay;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    EventAdapter eventAdapter;
    ArrayList<MonHoc> dailyEvents;
    private LinearLayout linearLayout;

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
        setEventAdpater();
    }

    private void showData() {
        LocalDate localDate = LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(),selectedDate.getDayOfMonth());
        db.collection("SinhVien").document(firebaseUser.getEmail())
                .collection(String.valueOf(DayOfWeek.from(localDate).name().substring(0,1).toUpperCase() + DayOfWeek.from(localDate).name().substring(1).toLowerCase()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() { // sự kiện get data thành công
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        dailyEvents.clear();
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            MonHoc model = new MonHoc(
                                    documentSnapshot.getId(),documentSnapshot.getString("name"),documentSnapshot.getString("teacher"),documentSnapshot.getString("room"));
                            dailyEvents.add(model);
                        }
                        eventAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() { // Lấy data không thành công
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CalendarActivity.this, "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
        TimeEventCell = findViewById(R.id.TimeEventCell);
        HomNay = findViewById(R.id.HomNay);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        linearLayout =findViewById(R.id.llShow);
    }
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date )
    {
        if(date != null)
        {
            selectedDate = date;
            setMonthView();
            setEventAdpater();
            showData();
        }
    }

    private void setEventAdpater()
    {
        LocalDate localDate = LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(),selectedDate.getDayOfMonth());
        dailyEvents = new ArrayList<>();
        eventAdapter = new EventAdapter(dailyEvents);
        eventListView.setAdapter(eventAdapter);
        TimeEventCell.setText(DayOfWeek.from(localDate).name().substring(0, 1).toUpperCase() + DayOfWeek.from(localDate).name().substring(1).toLowerCase());
        HomNay.setText("Hôm Nay");
        showData();
    }



}