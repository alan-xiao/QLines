package com.example.qlines;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        ArrayAdapter adapter;
        Intent myintent = getIntent();
        Location location = (Location)myintent.getSerializableExtra("location");

        ArrayList<String> list = new ArrayList<>();
        for(Report rep : location.getReports()) {
            list.add(rep.getTime());
        }

        System.out.println("Created reports");

        adapter = new ArrayAdapter<>(this, R.layout.report_info, R.id.reportInfo, list);
        ListView listView = findViewById(R.id.reports);
        listView.setAdapter(adapter);




    }
}
