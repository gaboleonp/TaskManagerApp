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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {
	// private FirebaseAuth mAuth;

	private EditText emailtext, passwordtext, confirmpasswordtext;
	private TextView ifsignuptext;
	private Button loginbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		emailtext = findViewById(R.id.LogInEmailText);
		passwordtext = findViewById(R.id.LogInPasswordText);
		confirmpasswordtext = findViewById(R.id.LogInConfirmPasswordText);
		loginbtn = findViewById(R.id.LoginButton);
		ifsignuptext = findViewById(R.id.CreateAccountTextView);

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
				checkUser();
            }
		});
	}

	public void checkUser(){

		String emailLogin = emailtext.getText().toString().trim().toLowerCase();
		String passwordLogin = passwordtext.getText().toString().trim().toLowerCase();
		String confirmpasswordLogin = confirmpasswordtext.getText().toString().trim().toLowerCase();

		DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
		Query checkUserData = reference.orderByChild("email").equalTo(emailLogin);
		checkUserData.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()){
					for (DataSnapshot userSnapShot : dataSnapshot.getChildren()){

						String passfromDB = userSnapShot.child("password").getValue(String.class);
						String rolefromDB = userSnapShot.child("role").getValue(String.class);

						assert passfromDB != null;
						if(passfromDB.equals(passwordLogin)){

							String usernameDB = userSnapShot.child("username").getValue(String.class);
							String emailDB = userSnapShot.child("email").getValue(String.class);
							String passwordDB = userSnapShot.child("password").getValue(String.class);
							String confirmpasswordDB = userSnapShot.child("confirmPassword").getValue(String.class);

							if (passwordLogin.equals(confirmpasswordLogin)) {
								if("manager".equals(rolefromDB)) {
									Intent mintent = new Intent(LogInActivity.this, Manager_AssignList_Activity.class);
									mintent.putExtra("username", usernameDB);
									mintent.putExtra("email", emailDB);
									mintent.putExtra("password", passwordDB);
									mintent.putExtra("confirmPassword", confirmpasswordDB);
									startActivity(mintent);
								} else if("employee".equals(rolefromDB)) {
									Intent eintent = new Intent(LogInActivity.this, User_TaskList_Activity.class);
									eintent.putExtra("username", usernameDB);
									eintent.putExtra("email", emailDB);
									eintent.putExtra("password", passwordDB);
									eintent.putExtra("confirmPassword", confirmpasswordDB);
									startActivity(eintent);
								}
								Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
								finish();
							} else {
								Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
							}
						} else {
							Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_LONG).show();
						}
					}
				} else {
					Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
				}
			}
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
			}
		});
	}
}