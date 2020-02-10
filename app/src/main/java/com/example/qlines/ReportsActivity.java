package com.example.qlines;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReportsActivity extends AppCompatActivity {
    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ArrayList<String> list;
    private ArrayList<Report> reports;
    private ArrayList<String> keys;
    private ArrayAdapter<String> adapter;
    private int locationPosition;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TimeUnit timeUnit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        final Intent myintent1 = getIntent();
        locationPosition = myintent1.getIntExtra("index", 0);
        String pos = "0" + (locationPosition + 1);
        listView = findViewById(R.id.reports);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("locations/loc" + pos + "/reports");
        Date now = new Date();
        list = new ArrayList<>();
        reports = new ArrayList<>();
        keys = new ArrayList<>();
        timeUnit = TimeUnit.MINUTES;
        adapter = new ArrayAdapter<>(this, R.layout.report_info, R.id.reportInfo, list);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reports.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Report report = ds.getValue(Report.class);
                    if (!report.notShow()) {
                        reports.add(report);
                        keys.add(ds.getKey());
                    }
                }
                Collections.reverse(reports);
                Collections.reverse(keys);
                System.out.println(keys);
                updateList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent2 = new Intent(view.getContext(), InputActivity.class);
                myintent2.putExtra("index", locationPosition);
                startActivity(myintent2);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent3 = new Intent(view.getContext(), ReportDetails.class);
                myintent3.putExtra("key", keys.get(position));
                myintent3.putExtra("index", locationPosition);
                startActivity(myintent3);
            }
        });

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void updateList() {
        list.clear();
        for (Report r : reports) {
            list.add(r.getTimeAgoString());
        }
        listView.setAdapter(adapter);
    }
}
