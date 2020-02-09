package com.example.qlines;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private ArrayList<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.locations);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("locations");
        list = new ArrayList<>();
        locations = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.location_info, R.id.locationInfo, list);
        ref.addChildEventListener(new ChildEventListener() {
            private void refresh(DataSnapshot dataSnapshot) {
                int i = 0;
                ArrayList<String> values = new ArrayList<>(3);
                ArrayList<Report> reports = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (i == 3) {
                        String str = ds.getValue().toString();
                        String[] arr = str.split("=\\{");
                        System.out.println("Arr length: " + arr.length);
                        for (int k = 0; k < arr.length - 1; k++) {
                            reports.add(new Report());
                            if (k != arr.length - 2) {
                                int lastComma = arr[k + 1].lastIndexOf(',');
                                int secondLastComma = arr[k + 1].substring(0, lastComma).lastIndexOf(',');
                                int thirdLastComma = arr[k + 1].substring(0, secondLastComma).lastIndexOf(',');
                                reports.get(k).setTime(arr[k + 1].substring(secondLastComma + 12, lastComma - 1));
                                reports.get(k).setUser_id(arr[k + 1].substring(thirdLastComma + 10, secondLastComma));
                                reports.get(k).setRep_desc(arr[k + 1].substring(9, thirdLastComma));


                            } else {
                                int lastComma = arr[k + 1].lastIndexOf(',');
                                int secondLastComma = arr[k + 1].substring(0, lastComma).lastIndexOf(',');
                                reports.get(k).setTime(arr[k + 1].substring(lastComma + 12, arr[k + 1].length() - 2));
                                reports.get(k).setUser_id(arr[k + 1].substring(secondLastComma + 10, lastComma));
                                reports.get(k).setRep_desc(arr[k + 1].substring(9, secondLastComma));
                            }
                        }

                    } else {
                        values.add(ds.getValue().toString());
                    }
                    i++;
                }
                locations.add(new Location(values.get(1), values.get(0), values.get(2), reports));
                System.out.println("Size of Report for " + values.get(1) + " : " + reports.size());
                list.add(locations.get(locations.size() - 1).getName());
                listView.setAdapter(adapter);
            }

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println("onChildAdded Entered");
                this.refresh(dataSnapshot);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent = new Intent(view.getContext(), ReportsActivity.class);
                myintent.putExtra("location", locations.get(position));
                startActivity(myintent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut(); // logs out
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
