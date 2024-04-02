package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
	private EditText emailtext, passwordtext;
	private TextView ifsignuptext;
	private Button loginbtn;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		emailtext = findViewById(R.id.LogInEmailText);
		passwordtext = findViewById(R.id.LogInPasswordText);
		loginbtn = findViewById(R.id.LoginButton);
		ifsignuptext = findViewById(R.id.CreateAccountTextView);
		mAuth = FirebaseAuth.getInstance();

		ifsignuptext.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
				Intent intent = new Intent(LogInActivity.this,SignUpActivity.class );
				startActivity(intent);
            }
		});
		loginbtn.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
              loginUser();
            }
		});

	}
	protected void onStart() {
		super.onStart();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!= null) {
                    Intent intent = new Intent(LogInActivity.this, Manager_AssignList_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
	}
	public void loginUser(){
		String email = emailtext.getText().toString().trim();
        String password = passwordtext.getText().toString().trim();
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"All the fields must be filled", Toast.LENGTH_SHORT).show();
			return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
					Toast.makeText(LogInActivity.this,"Login successful !", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(LogInActivity.this, Manager_AssignList_Activity.class);
                    startActivity(intent);
                    finish();
                }
				else {
                    Toast.makeText(LogInActivity.this,"Login Failed. Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

	}
}