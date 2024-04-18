package com.example.taskmanagerapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class User_TaskList_Activity extends AppCompatActivity {
	ListView taskList;
	DatabaseReference usersReference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_task_list);

		taskList = findViewById(R.id.taskuserListView);
		usersReference = FirebaseDatabase.getInstance().getReference("users");

		fetchUsersAndTasks();
	}

	private void fetchUsersAndTasks() {
		final List<String> taskListData = new ArrayList<>();

		String loggedInUsername = getIntent().getStringExtra("username");

		usersReference.child(loggedInUsername).child("tasks").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				Log.d("User_TaskList_Activity", "onDataChange() method called");

				taskListData.clear();
				Log.d("User_TaskList_Activity", "Clearing taskListData");

				if (dataSnapshot.exists()) {
					Log.d("User_TaskList_Activity", "DataSnapshot exists: true");

					for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
						Log.d("User_TaskList_Activity", "Processing task: " + taskSnapshot.getKey());

						String taskId = taskSnapshot.getKey();
						String taskName = taskSnapshot.child("taskName").getValue(String.class);
						String taskDescription = taskSnapshot.child("taskDescription").getValue(String.class);
						String taskDeadline = taskSnapshot.child("taskDeadline").getValue(String.class);

						if (taskName != null && taskDescription != null && taskDeadline != null) {
							String formattedTask = "Task Name: " + taskName + "\n";
							formattedTask += "Description: " + taskDescription + "\n";
							formattedTask += "Deadline: " + taskDeadline;

							Log.d("User_TaskList_Activity", "Formatted Task: " + formattedTask);

							taskListData.add(formattedTask);
						}
					}
				} else {
					Log.d("User_TaskList_Activity", "DataSnapshot exists: false");
				}

				ArrayAdapter<String> adapter = new ArrayAdapter<>(User_TaskList_Activity.this, android.R.layout.simple_list_item_1, taskListData);
				taskList.setAdapter(adapter);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				Log.e("User_TaskList_Activity", "DatabaseError: " + databaseError.getMessage());
			}
		});
	}
}
