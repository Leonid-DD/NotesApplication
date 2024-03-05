package com.example.notesapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView noteView;
    ImageButton addNote;
    DBHelper DB;
    List<Note> GlobalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBCheck(String.valueOf(getApplicationContext().getDatabasePath("notes.db")));
        DB = DBHelper.getInstance(getApplicationContext());

        noteView = findViewById(R.id.notesRecyclerView);
        addNote = findViewById(R.id.addNoteImageButton);

        GlobalList = DB.GetAllNotes();

        NotesAdapter noteAdapter = new NotesAdapter(getApplicationContext(), GlobalList);
        noteView.setAdapter(noteAdapter);
    }

    private void DBCheck(String path) {
        try {
            File file = new File(path);
        } catch (Exception e) {
        }
    }
}