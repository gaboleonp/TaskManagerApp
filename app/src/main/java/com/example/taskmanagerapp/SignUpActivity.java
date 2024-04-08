package com.example.taskmanagerapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

	TextView textView, ifaccountlogin;
	CheckBox checkBox;
	FirebaseDatabase database;
	DatabaseReference reference;
	private EditText usernametxt, roletxt, emailtxt, passwordtxt;
	private Button signupbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		checkBox = findViewById(R.id.checkBox);
		SpannableString spannableString = new SpannableString("I agree to the Terms of Services and Privacy Policy.");
		spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 32, 36, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new ForegroundColorSpan(Color.rgb(255, 193, 7)), 15, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new ForegroundColorSpan(Color.rgb(255, 193, 7)), 37, 51, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		checkBox.setText(spannableString);

		ifaccountlogin = findViewById(R.id.alreadyaccountclick);
		SpannableString spannableString1 = new SpannableString("Have an Account? Log In");
		spannableString1.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString1.setSpan(new ForegroundColorSpan(Color.rgb(255, 193, 7)), 17, spannableString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ifaccountlogin.setText(spannableString1);

		usernametxt = findViewById(R.id.SignUpUsernameText);
		roletxt = findViewById(R.id.SignUpRoleText);
		emailtxt = findViewById(R.id.SignUpEmailText);
		passwordtxt = findViewById(R.id.SignUpPasswordText);
		signupbtn = findViewById(R.id.signupbutton);


		ifaccountlogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
				startActivity(intent);
			}
		});
		signupbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				database = FirebaseDatabase.getInstance();
				reference = database.getReference("users");
				String username = usernametxt.getText().toString().trim().toLowerCase();
				String role = roletxt.getText().toString().trim().toLowerCase();
				String email = emailtxt.getText().toString().trim().toLowerCase();
				String password = passwordtxt.getText().toString().trim().toLowerCase();

				if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty() ) {
					Toast.makeText(SignUpActivity.this, "All fields must be filled out !", Toast.LENGTH_SHORT).show();
					return;
				} else {
					Model model = new Model(username,role, email, password);
					reference.child(username).setValue(model);
					Toast.makeText(SignUpActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

					if ("manager".equals(role)) {
						Intent managerIntent = new Intent(SignUpActivity.this, Manager_AssignList_Activity.class);
						managerIntent.putExtra("username", username);
						managerIntent.putExtra("role", role);
						managerIntent.putExtra("email", email);
						managerIntent.putExtra("password", password);
						startActivity(managerIntent);
					} else if ("employee".equals(role)) {
						Intent employeeIntent = new Intent(SignUpActivity.this, User_TaskList_Activity.class);
						employeeIntent.putExtra("username", username);
						employeeIntent.putExtra("role", role);
						employeeIntent.putExtra("email", email);
						employeeIntent.putExtra("password", password);
						startActivity(employeeIntent);
					}

					finish();
				}

			}
		});
	}
}