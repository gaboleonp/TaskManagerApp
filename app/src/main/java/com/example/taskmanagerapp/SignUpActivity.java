package com.example.taskmanagerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

	TextView textView, ifaccountlogin;
	CheckBox checkBox;
	private EditText usernametxt, emailtxt, passwordtxt;
	private Button signupbtn;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		checkBox = findViewById(R.id.checkBox);
        SpannableString spannableString = new SpannableString("I agree to the Terms of Services and Privacy Policy.");
		spannableString.setSpan(new ForegroundColorSpan(Color.WHITE),0,14,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new ForegroundColorSpan(Color.WHITE),32,36,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new ForegroundColorSpan(Color.rgb(255,193,7)),15,32,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new ForegroundColorSpan(Color.rgb(255,193,7)),37,51,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		checkBox.setText(spannableString);

		ifaccountlogin = findViewById(R.id.alreadyaccountclick);
		SpannableString spannableString1 = new SpannableString("Have an Account? Log In");
		spannableString1.setSpan(new ForegroundColorSpan(Color.WHITE),0,17,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString1.setSpan(new ForegroundColorSpan(Color.rgb(255,193,7)),17,spannableString1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ifaccountlogin.setText(spannableString1);

		usernametxt = findViewById(R.id.SignUpUsernameText);
		emailtxt = findViewById(R.id.SignUpEmailText);
		passwordtxt = findViewById(R.id.SignUpPasswordText);
		signupbtn = findViewById(R.id.signupbutton);
		mAuth = FirebaseAuth.getInstance();

		ifaccountlogin.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LogInActivity.class );
				startActivity(intent);
            }
		});
		signupbtn.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
                registerUser();
            }
		});
	}
private void registerUser(){
	String username = usernametxt.getText().toString().trim();
	String email = emailtxt.getText().toString().trim();
	String password = passwordtxt.getText().toString().trim();

	if (username.isEmpty() || email.isEmpty() || password.isEmpty()){
		Toast.makeText(this, "All fields must be filled out !", Toast.LENGTH_SHORT).show();
		return;
		}
	if (password.length()<8){
		passwordtxt.setError("Password should be at least 8 characters");
		passwordtxt.requestFocus();
		return;
	}
	if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
		emailtxt.setError("Please enter a valid Email address");
		emailtxt.requestFocus();
		return;
	}
	mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
		@Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                Toast.makeText(SignUpActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this,Manager_AssignList_Activity.class );
				intent.putExtra("username",username);
                startActivity(intent);
            }
            else {
                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
	});

}
}
