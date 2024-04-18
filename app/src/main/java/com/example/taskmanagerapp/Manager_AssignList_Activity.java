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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Manager_AssignList_Activity extends AppCompatActivity {
	TextView titleemail, titleusername, taskusername, taskname, taskdescription, taskdeadline;
	Button signout, btnassigntask, btnviewtask;
	DatabaseReference usersReference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_assign_list);

		titleemail = findViewById(R.id.showemailtext);
		titleusername = findViewById(R.id.showusernametext);
		signout = findViewById(R.id.signoutbutton);

		taskusername = findViewById(R.id.taskuserassignedtxt);
		taskname = findViewById(R.id.tasknametxt);
		taskdescription = findViewById(R.id.taskdescriptiontxt);
		taskdeadline = findViewById(R.id.taskdeadlinetxt);

		btnassigntask = findViewById(R.id.assigntaskbutton);
		btnviewtask = findViewById(R.id.viewtaskbutton);

		// Initialize Firebase database reference to 'users' node
		usersReference = FirebaseDatabase.getInstance().getReference("users");

		btnassigntask.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				InsertData();
			}
		});
		btnviewtask.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Manager_AssignList_Activity.this, TaskListActivity.class));
				finish();
			}
		});

		signout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(Manager_AssignList_Activity.this, "Signout success !", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(Manager_AssignList_Activity.this, LogInActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	private void InsertData() {
		final String tasksusername = taskusername.getText().toString().trim();
		final String tasksname = taskname.getText().toString().trim();
		final String tasksdescription = taskdescription.getText().toString().trim();
		final String tasksdeadline = taskdeadline.getText().toString().trim();

		// Check if the provided username exists under the 'users' node
		usersReference.child(tasksusername).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
			@Override
			public void onComplete(@NonNull com.google.android.gms.tasks.Task<DataSnapshot> task) {
				if (task.isSuccessful()) {
					DataSnapshot snapshot = task.getResult();
					if (snapshot.exists()) {
						// Username exists, proceed with adding the task
						addTask(tasksusername, tasksname, tasksdescription, tasksdeadline);
					} else {
						// Username does not exist, show error message
						Toast.makeText(Manager_AssignList_Activity.this, "User does not exist", Toast.LENGTH_SHORT).show();
					}
				} else {
					// Error occurred while checking username existence, show error message
					Toast.makeText(Manager_AssignList_Activity.this, "Error checking username existence", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void addTask(String tasksusername, String tasksname, String tasksdescription, String tasksdeadline) {
		// Generate unique task ID
		String taskId = usersReference.child(tasksusername).child("tasks").push().getKey();

		Task task = new Task(tasksusername, tasksname, tasksdescription, tasksdeadline);

		// Add the task under the specified user's node
		usersReference.child(tasksusername).child("tasks").child(taskId).setValue(task)
				.addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> t) {
						if (t.isSuccessful()) {
							// Task added successfully, show success message
							Toast.makeText(Manager_AssignList_Activity.this, "Task assigned successfully", Toast.LENGTH_SHORT).show();
						} else {
							// Failed to add task, show error message
							Toast.makeText(Manager_AssignList_Activity.this, "Failed to assign task", Toast.LENGTH_SHORT).show();
						}
					}
				});
	}
}
