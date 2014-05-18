package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Build;

public class ToDoActivity extends Activity {
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
			// abstract metnod that must be implemented for instantiation of OnItemLongClickListener type.
			@Override
			public boolean onItemLongClick (AdapterView<?> adapter, View item, int position, long id) {
				toDoItems.remove(position);
				// Changed base data, but not stuff in ListView
				toDoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
		});
		
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
			e.printStackTrace();;
		}
	}
}
