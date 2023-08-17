package com.example.sunnily;

import static com.example.sunnily.LoginActivity.newuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryActivity extends AppCompatActivity {
    private ImageView btnHome, btnTask, btnProfil;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter; // Create Object of the Adapter class
    private DatabaseReference mbase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

// Create a instance of the database and get
        // its reference
        mbase = FirebaseDatabase.getInstance().getReference("user").child(newuser).child("history");

        recyclerView = findViewById(R.id.recyclerview);
        btnHome = findViewById(R.id.btnHome4);
        btnTask = findViewById(R.id.btnTask4);
        btnProfil = findViewById(R.id.btnProfil4);
        // To display the Recycler view linearly

//        recyclerView.setLayoutManager(
//                new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<History> options
                = new FirebaseRecyclerOptions.Builder<History>()
                .setQuery(mbase, History.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new HistoryAdapter(options);
        // Connecting Adapter class with the Recycler view*/

        recyclerView.setAdapter(adapter);

        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistoryActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HistoryActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

}