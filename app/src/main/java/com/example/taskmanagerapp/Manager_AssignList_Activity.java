package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Manager_AssignList_Activity extends AppCompatActivity {
	TextView email, username;
	Button signout, deleteprofile;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_assign_list);
		email = findViewById(R.id.showemailtext);
		username = findViewById(R.id.showusernametext);
		signout = findViewById(R.id.signoutbutton);
		deleteprofile = findViewById(R.id.deleteprofilebutton);
		mAuth = FirebaseAuth.getInstance();

		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		FirebaseUser currentUser = mAuth.getCurrentUser();

		String emailuser = "";
		if (currentUser!= null) {
            emailuser = currentUser.getEmail();
        }
		email.setText("Email: "+ emailuser);
		username.setText("Username: "+ name);

		signout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FirebaseAuth.getInstance().signOut();
				Toast.makeText(Manager_AssignList_Activity.this, "Signout success !", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(Manager_AssignList_Activity.this, LogInActivity.class);
				startActivity(intent);
				finish();
			}
		});

		deleteprofile.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
				assert currentUser != null;
				currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if (task.isSuccessful()) {
                            Toast.makeText(Manager_AssignList_Activity.this, "Delete success !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Manager_AssignList_Activity.this, SignUpActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Manager_AssignList_Activity.this, "Delete failed !", Toast.LENGTH_SHORT).show();
                        }
					}
				});
            }
		});


	}
}