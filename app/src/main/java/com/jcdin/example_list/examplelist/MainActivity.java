package com.jcdin.example_list.examplelist;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Note> notesList = new ArrayList<Note>();
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listview = (ListView) findViewById(R.id.noteListView);

        getNotes();
        notesAdapter = new NotesAdapter(this, R.layout.listitemlayout, notesList);
        listview.setAdapter(notesAdapter);

        attachEvent();
    }

    private void attachEvent() {
        final TextInputEditText searchInput = (TextInputEditText) findViewById(R.id.searchInput);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < before) {
                    notesAdapter.resetData();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                notesAdapter.getFilter().filter(s.toString());
            }
        });
    }

    private void getNotes() {
        // provide a dummy data
        notesList = new ArrayList<Note>();

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2"};
        for (int i = 0; i < values.length; ++i) {
            notesList.add(new Note(values[i], values[i]));
        }
    }
}
