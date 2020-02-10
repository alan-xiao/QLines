package com.example.qlines;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputActivity extends AppCompatActivity {
    EditText desc;
    Button save;
    DatabaseReference ref;
    String pos;
    Report report;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Intent myintent = getIntent();
        int position = myintent.getIntExtra("index", 0);
        String pos = "0" + (position + 1);
        desc = findViewById(R.id.desc);
        save = findViewById(R.id.writeButton);
        ref = FirebaseDatabase.getInstance().getReference("locations/loc" + pos + "/reports");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report = new Report(desc.getText().toString().trim(), fAuth.getCurrentUser().getUid());
                ref.push().setValue(report);
                Toast.makeText(InputActivity.this, "Data Inserted Successfully",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
