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

    //deklarasi variabel
    //private untuk variabel yang hanya bisa di buka dalam file yang sama
    private ImageView btnHome, btnTask, btnProfil;
    private Button btnHum0, btnHum1, btnHum2, btnUv, btnSimpan,btnHis;
    private TextView tvNama, tvHum0, tvHum1, tvHum2, tvUv0, valHum0, valHum1, valHum2, valUv0;
    private long epoch;

    protected static Float finHum0, finHum1, finHum2, finUv0;
    protected static Float hum0, hum1, hum2, uv0;
    protected static String resHum0, resHum1, resHum2, resUv0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNama = findViewById(R.id.TVnama);
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
        btnHis = findViewById(R.id.buttonHistory);
        btnHome = findViewById(R.id.btnHome2);
        btnTask = findViewById(R.id.btnTask2);
        btnProfil = findViewById(R.id.btnProfil2);

        //tampilkan format tanggal dan waktu
        epoch = System.currentTimeMillis();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        formattedDate = df.format(c);

        reader();

        //koneksi ke firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //pembuatan variable yang terhubung dengan firebase
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

        //button Home
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        //button Profile
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        btnHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });

        //btnSimpan
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
        //note: 7 kondisi
        if (hum0 <= 3) {
            myRef.setValue("Kering");
            valHum0.setText("Kering");
        } else if (hum0 <= 5) {
            myRef.setValue("Normal");
            valHum0.setText("Normal");
        } else {
            myRef.setValue("Lembab");
            valHum0.setText("Lembab");
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
        if (hum1 <= 3) {
            myRef.setValue("Kering");
            valHum1.setText("Kering");
        } else if (hum1 <= 5) {
            myRef.setValue("Normal");
            valHum1.setText("Normal");
        } else {
            myRef.setValue("Lembab");
            valHum1.setText("Lembab");
        }
    }

    //Scan Humidity Jidat
    private void scanHum2() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user").child(newuser).child("humid2");
        myRef.setValue(hum2);
        tvHum2.setText(String.valueOf(hum2));

        myRef = database.getReference("user").child(newuser).child("resHum2");
        //Category Humidity
        if (hum2 <= 3) {
            myRef.setValue("Kering");
            valHum2.setText("Kering");
        } else if (hum2 <= 5) {
            myRef.setValue("Normal");
            valHum2.setText("Normal");
        } else {
            myRef.setValue("Lembab");
            valHum2.setText("Lembab");
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
        if (uv0 >= 3) {
            myRef.setValue("Buruk");
            valUv0.setText("Buruk");
        } else if (uv0 >= 0.6) {
            myRef.setValue("Cukup");
            valUv0.setText("Cukup");
        } else if (uv0 < 0.6) {
            myRef.setValue("Baik");
            valUv0.setText("Baik");
        }
    }

    //fungsi reader untuk membaca nilai dari transaction firbase kedalam variabel android
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
                finHum0 = snapshot.child("humid0").getValue(Float.class);
                finHum1 = snapshot.child("humid1").getValue(Float.class);
                finHum2 = snapshot.child("humid2").getValue(Float.class);
                finUv0 = snapshot.child("uv0").getValue(Float.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //fungsi simpan data di histroy firebase
    private void getSimpanData() {
        reader();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;

        myRef = database.getReference("user").child(newuser).child("history").child("" + epoch).child("humid0");
        myRef.setValue(finHum0 + " - " + resHum0);
        myRef = database.getReference("user").child(newuser).child("history").child("" + epoch).child("humid1");
        myRef.setValue(finHum1 + " - " + resHum1);
        myRef = database.getReference("user").child(newuser).child("history").child("" + epoch).child("humid2");
        myRef.setValue(finHum2 + " - " + resHum2);
        myRef = database.getReference("user").child(newuser).child("history").child("" + epoch).child("uv0");
        myRef.setValue(finUv0 + " - " + resUv0);
        myRef = database.getReference("user").child(newuser).child("history").child("" + epoch).child("tanggal");
        myRef.setValue(formattedDate);
    }
}