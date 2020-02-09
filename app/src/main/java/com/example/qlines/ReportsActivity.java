package com.example.qlines;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {
    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Intent myintent = getIntent();
        int position = myintent.getIntExtra("index", 0);
        String pos = "0" + (position + 1);
        listView = findViewById(R.id.reports);

        database = FirebaseDatabase.getInstance();
        System.out.println("locations/loc" + pos + "/reports");
        ref = database.getReference("locations/loc" + pos + "/reports");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.report_info, R.id.reportInfo, list);

        ref.orderByChild("timestamp").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Report report = dataSnapshot.getValue(Report.class);
                System.out.println(dataSnapshot.getValue().toString());
                System.out.println(report.toString());
                list.add(report.getTimestamp());
                listView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
