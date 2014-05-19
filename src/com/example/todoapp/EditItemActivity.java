package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends Activity {
	private static final String TAG = "EditItemActivity";

	private EditText stEditText;
	private int originalPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		stEditText = (EditText) findViewById(R.id.editText1);

		String originalText = getIntent().getStringExtra("edit_text");
		originalPos = getIntent().getIntExtra("position", 0);

		stEditText.setText(originalText, TextView.BufferType.EDITABLE);
		stEditText.setSelection(originalText.length());
		stEditText.requestFocus();
	}

	public void onSubmit(View v) {
		Intent i = new Intent();
		// Pass relevant data back as a result
		i.putExtra("edited_text", stEditText.getText().toString());
		i.putExtra("original_position", originalPos);
		// Activity finished ok, return the data
		setResult(RESULT_OK, i); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent
	}
}
