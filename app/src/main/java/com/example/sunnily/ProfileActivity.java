package com.example.sunnily;

import static com.example.sunnily.LoginActivity.newuser;
import static com.example.sunnily.LoginActivity.usernm;
import static com.example.sunnily.LoginActivity.nama;
import static com.example.sunnily.LoginActivity.mail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private ImageView btnHome, btnTask, btnProfil;

    private TextView txtNama, txtUname, txtEmail;
    private Button blogout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        btnHome = findViewById(R.id.btnHome3);
        btnTask = findViewById(R.id.btnTask3);
        btnProfil = findViewById(R.id.btnProfil3);
        blogout = findViewById(R.id.btnLogout);
        txtNama = findViewById(R.id.txtNama);
        txtUname = findViewById(R.id.txtUname);
        txtEmail = findViewById(R.id.txtEmail);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("user").child(newuser);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Nama Harus String
                usernm = snapshot.child("username").getValue(String.class);
                mail = snapshot.child("email").getValue(String.class);
                nama = snapshot.child("name").getValue(String.class);

                //Deklarasi profil
                if (nama != null) {
                    txtNama.setText(nama);
                }
                if (usernm != null) {
                    txtUname.setText(usernm);
                }
                if (mail != null) {
                    txtEmail.setText(mail);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        blogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAccount();
            }
        });

    }

    //logout account
    private void logoutAccount() {
        mAuth.signOut();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);

        Toast.makeText(getApplicationContext(), "Log out berhasil", Toast.LENGTH_LONG).show();

    }
}