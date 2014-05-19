package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends Activity {
	private static final String TAG = "ToDoActivity";

	private ArrayList<String> toDoItems;
	private ArrayAdapter<String> toDoAdapter;
	private ListView lvItems;
	private EditText etNewItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		lvItems = (ListView) findViewById(R.id.lvItems);
		readItems();
		toDoAdapter = new ArrayAdapter<String>(getBaseContext(),
				android.R.layout.simple_list_item_1, toDoItems);
		lvItems.setAdapter(toDoAdapter);
		setupListViewListener();
	}

	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			// abstract metnod that must be implemented for instantiation of
			// OnItemLongClickListener type.
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int position, long id) {
				toDoItems.remove(position);
				// Changed base data, but not stuff in ListView
				toDoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
		});

		lvItems.setOnItemClickListener(new OnItemClickListener() {
			// abstract metnod that must be implemented for instantiation of
			// OnItemLongClickListener type.
			@Override
			public void onItemClick(AdapterView<?> adapter, View item,
					int position, long id) {
				Intent i = new Intent(getBaseContext(), EditItemActivity.class);
				// put "extras" into the bundle for access in the second
				// activity
				i.putExtra("position", position);
				i.putExtra("edit_text", toDoItems.get(position));
				// Start Edit activity, but want to get editted text back.
				startActivityForResult(i, REQUEST_CODE);
			}
		});

	}

	static final int REQUEST_CODE = 5874;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			// Extract name value from result extras
			String newText = i.getExtras().getString("edited_text");
			// I probably could have cached the original position before calling
			// the Edit activity
			// and just used it when i got back here.
			int newPos = i.getIntExtra("original_position", 0);
			// Replace is a set method to a specific row.
			toDoItems.set(newPos, newText);
			toDoAdapter.notifyDataSetChanged();
			writeItems();
		}
	}

	public void onAddItem(View v) {
		String itemText = etNewItem.getText().toString();
		toDoAdapter.add(itemText);
		// We've added text to list; no longer need in text input field.
		etNewItem.setText("");
		writeItems();
	}

	private void readItems() {
		File filesDir = getFilesDir();
		File toDoFile = new File(filesDir, "todo.txt");
		try {
			toDoItems = new ArrayList<String>(FileUtils.readLines(toDoFile));
		} catch (IOException e) {
			toDoItems = new ArrayList<String>();
		}
	}

	private void writeItems() {
		File filesDir = getFilesDir();
		File toDoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(toDoFile, toDoItems);
		} catch (IOException e) {
			e.printStackTrace();
			;
		}
	}
}
