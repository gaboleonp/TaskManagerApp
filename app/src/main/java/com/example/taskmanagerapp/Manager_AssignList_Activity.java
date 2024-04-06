package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Manager_AssignList_Activity extends AppCompatActivity {
	TextView titleemail, titleusername;
	Button signout, deleteprofile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_assign_list);

		titleemail = findViewById(R.id.showemailtext);
		titleusername = findViewById(R.id.showusernametext);
		signout = findViewById(R.id.signoutbutton);
		deleteprofile = findViewById(R.id.deleteprofilebutton);
		showDataUser();
		signout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(Manager_AssignList_Activity.this, "Signout success !", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(Manager_AssignList_Activity.this, LogInActivity.class);
				startActivity(intent);
				finish();
			}
		});
		deleteprofile.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
                String usernameMain = titleusername.getText().toString().trim();
				DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
				Query checkUserData = reference.orderByChild("username").equalTo(usernameMain);
				checkUserData.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                           dataSnapshot.child(usernameMain).getRef().removeValue();
							Toast.makeText(Manager_AssignList_Activity.this, "Delete profile success !", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(Manager_AssignList_Activity.this, SignUpActivity.class);
							startActivity(intent);
							finish();}
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
				});
            }
		});
	}

	public void showDataUser(){
		Intent intent = getIntent();
		titleusername.setText(intent.getStringExtra("username"));
		titleemail.setText("Email: "+intent.getStringExtra("email"));

	}
}