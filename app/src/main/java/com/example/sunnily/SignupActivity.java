package com.example.sunnily;

import static com.example.sunnily.LoginActivity.usernm;
import static com.example.sunnily.LoginActivity.mail;
import static com.example.sunnily.LoginActivity.nama;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText txtNama, txtUname, txtEmail, txtPassword, txtConfirmPassword;
    private Button bsignup;
    private TextView txtLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        txtNama = findViewById(R.id.txtNamaSignup);
        txtUname = findViewById(R.id.txtUnameSignup);
        txtEmail = findViewById(R.id.txtEmailSignup);
        txtPassword = findViewById(R.id.txtPasswordSignup);
        txtConfirmPassword = findViewById(R.id.txtKonfirmPassword);
        bsignup = findViewById(R.id.btnSignup);
        txtLogin = findViewById(R.id.txtToLogin);

        bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void registerNewUser(){

        String email, password, name, handphone;
        email = txtEmail.getText().toString();
        password = txtPassword.getText().toString();
        name = txtNama.getText().toString();

        mail = email;
        nama = name;
        usernm = txtUname.getText().toString();
        String newuser = email.substring(0,5);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user").child(newuser).child("name");
        myRef.setValue(name);

        myRef = database.getReference("user").child(newuser).child("email");
        myRef.setValue(email);

        myRef = database.getReference("user").child(newuser).child("password");
        myRef.setValue(password);

        myRef= database.getReference("user").child(newuser).child("username");
        myRef.setValue(usernm);

        myRef = database.getReference("user").child(newuser).child("humid0");
        myRef.setValue(0);

        myRef = database.getReference("user").child(newuser).child("humid1");
        myRef.setValue(0);

        myRef = database.getReference("user").child(newuser).child("humid2");
        myRef.setValue(0);

        myRef = database.getReference("user").child(newuser).child("uv0");
        myRef.setValue(0);


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter youremail!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }


        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();


                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(SignupActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();

                        }
                    }
                });
    }
}