package com.example.sunnily;

import static com.example.sunnily.LoginActivity.nama;
import static com.example.sunnily.SignupActivity.name;
import static com.example.sunnily.LoginActivity.newuser;
import static com.example.sunnily.LoginActivity.formattedDate;
import static com.example.sunnily.LoginActivity.usernm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageView btnHome, btnTask, btnProfil;
    private ProgressBar barHum0, barHum1, barHum2, barUv;
    private Button btnHum0, btnHum1, btnHum2, btnUv, btnSimpan;
    private Integer CurrentProgress = 0;
    private TextView tvNama, tvHum0, tvHum1, tvHum2, tvUv0, valHum0, valHum1, valHum2, valUv0;
    private long epoch;

    protected static Float hum0, hum1, hum2, uv0, numHum0, numHum1, numHum2, numUv0;
    protected static String resHum0, resHum1, resHum2, resUv0;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNama = findViewById(R.id.TVnama);
       /* barHum0 = findViewById(R.id.progressBarPipiKanan);
        barHum1 = findViewById(R.id.progressBarPipiKiri);
        barHum2 = findViewById(R.id.progressBarJidat);
        barUv = findViewById(R.id.progressBarUV); */
        tvHum0 = findViewById(R.id.viewPipiKanan);
        tvHum1 = findViewById(R.id.viewPipiKiri);
        tvHum2 = findViewById(R.id.viewJidat);
        tvUv0 = findViewById(R.id.viewUv);
        valHum0 = findViewById(R.id.ResultPipiKanan);
        valHum1 = findViewById(R.id.ResultPipiKiri);
        valHum2 = findViewById(R.id.ResultJidat);
        valUv0 = findViewById(R.id.ResultUv);
        btnHum0 = findViewById(R.id.buttonPipiKanan);
        btnHum1 = findViewById(R.id.buttonPipiKiri);
        btnHum2 = findViewById(R.id.buttonJidat);
        btnUv = findViewById(R.id.buttonUV);
        btnSimpan = findViewById(R.id.buttonSimpan);
        btnHome = findViewById(R.id.btnHome2);
        btnTask = findViewById(R.id.btnTask2);
        btnProfil = findViewById(R.id.btnProfil2);

        epoch = System.currentTimeMillis();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        formattedDate = df.format(c);

        reader();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef1 = database.getReference("user").child(newuser).child("lastLogin");
        myRef1.setValue(formattedDate);

        DatabaseReference myRef = database.getReference("user").child(newuser);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nama = snapshot.child("name").getValue(String.class);
                if (nama != null) {
                    tvNama.setText(nama);
                }

                btnHum0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* barHum1.setProgress(hum1);
                        barHum1.setMax(1000); */
                        scanHum0();
                    }
                });
                btnHum1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* barHum1.setProgress(hum1);
                        barHum1.setMax(1000); */
                        scanHum1();
                    }
                });

                btnHum2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*barHum2.setProgress(hum2);
                        barHum2.setMax(1000);*/
                        scanHum2();
                    }
                });

                btnUv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /* barUv.setProgress(uv0);
                        barUv.setMax(1000); */
                        scanUv0();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSimpanData();
                Toast.makeText(MainActivity.this,
                        "Data Tersimpan", Toast.LENGTH_LONG).show();
            }

        });

    }


    //Scan Humidity Pipi Kanan
    private void scanHum0() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user").child(newuser).child("humid0");
        myRef.setValue(hum0);
        tvHum0.setText(String.valueOf(hum0));

        myRef = database.getReference("user").child(newuser).child("resHum0");
        //Category Humidity
        if (hum0 <= 1) {
            myRef.setValue("Buruk");
            valHum0.setText("Buruk");
        } else if (hum0 <= 3) {
            myRef.setValue("Cukup");
            valHum0.setText("Cukup");
        } else {
            myRef.setValue("Baik");
            valHum0.setText("Baik");
        }
    }

    //Scan Humidity Pipi Kiri
    private void scanHum1() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user").child(newuser).child("humid1");
        myRef.setValue(hum1);
        tvHum1.setText(String.valueOf(hum1));

        myRef = database.getReference("user").child(newuser).child("resHum1");
        //Category Humidity
        if (hum1 <= 1) {
            myRef.setValue("Buruk");
            valHum1.setText("Buruk");
        } else if (hum1 <= 3) {
            myRef.setValue("Cukup");
            valHum1.setText("Cukup");
        } else {
            myRef.setValue("Baik");
            valHum1.setText("Baik");
        }
    }

    //Scan Humidity Jida
    private void scanHum2() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user").child(newuser).child("humid2");
        myRef.setValue(hum2);
        tvHum2.setText(String.valueOf(hum2));

        myRef = database.getReference("user").child(newuser).child("resHum2");
        //Category Humidity
        if (hum2 <= 1) {
            myRef.setValue("Buruk");
            valHum2.setText("Buruk");
        } else if (hum2 <= 3) {
            myRef.setValue("Cukup");
            valHum2.setText("Cukup");
        } else {
            myRef.setValue("Baik");
            valHum2.setText("Baik");
        }
    }

    //Scan Intensitas UV
    private void scanUv0() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user").child(newuser).child("uv0");
        myRef.setValue(uv0);
        tvUv0.setText(String.valueOf(uv0));

        myRef = database.getReference("user").child(newuser).child("resUv0");
        //Category Humidity
        if (uv0 <= 1) {
            myRef.setValue("Buruk");
            valUv0.setText("Buruk");
        } else if (uv0 <= 3) {
            myRef.setValue("Cukup");
            valUv0.setText("Cukup");
        } else {
            myRef.setValue("Baik");
            valUv0.setText("Baik");
        }
    }

    private void reader(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("transaction");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hum0 = snapshot.child("humid").getValue(Float.class);
                hum1 = snapshot.child("humid").getValue(Float.class);
                hum2 = snapshot.child("humid").getValue(Float.class);
                uv0 = snapshot.child("uv").getValue(Float.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference myRef1 = database.getReference("user").child(newuser);
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resHum0 = snapshot.child("resHum0").getValue(String.class);
                resHum1 = snapshot.child("resHum1").getValue(String.class);
                resHum2 = snapshot.child("resHum2").getValue(String.class);
                resUv0 = snapshot.child("resUv0").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getSimpanData() {
        reader();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;

        myRef = database.getReference("user").child(newuser).child("history").child("" + epoch).child("humid0");
        myRef.setValue(resHum0);
        myRef = database.getReference("user").child(newuser).child("history").child("" + epoch).child("humid1");
        myRef.setValue(resHum1);
        myRef = database.getReference("user").child(newuser).child("history").child("" + epoch).child("humid2");
        myRef.setValue(resHum2);
        myRef = database.getReference("user").child(newuser).child("history").child("" + epoch).child("uv0");
        myRef.setValue(resUv0);
        myRef = database.getReference("user").child(newuser).child("history").child("" + epoch).child("tanggal");
        myRef.setValue(formattedDate);
    }
}