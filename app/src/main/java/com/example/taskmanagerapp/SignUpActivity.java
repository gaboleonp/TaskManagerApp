package com.example.taskmanagerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.CheckBox;
import android.widget.TextView;
import android.text.Spannable;

public class SignUpActivity extends AppCompatActivity {

	TextView textView;
	CheckBox checkBox;
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

		textView = findViewById(R.id.textViewAlreadyAccount);
		SpannableString spannableString1 = new SpannableString("Have an Account? Sign In");
		spannableString1.setSpan(new ForegroundColorSpan(Color.WHITE),0,17,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString1.setSpan(new ForegroundColorSpan(Color.rgb(255,193,7)),17,spannableString1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(spannableString1);

	}



}