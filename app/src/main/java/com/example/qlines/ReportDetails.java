package com.example.qlines;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReportDetails extends AppCompatActivity {
    private String position;
    private int loc;
    private TextView desc;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);
        final Intent myintent = getIntent();
        position = myintent.getStringExtra("key");
        loc = myintent.getIntExtra("index", 0);

        desc = findViewById(R.id.desc);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("locations/loc0" + (loc + 1) + "/reports/" + position + "/rep_desc");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                desc.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
