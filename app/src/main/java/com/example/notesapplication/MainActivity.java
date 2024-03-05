package com.example.notesapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView noteView;
    ImageButton addNote;
    DBHelper DB;
    List<Note> GlobalList;
    NotesAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBCheck(String.valueOf(getApplicationContext().getDatabasePath("notes.db")));
        DB = DBHelper.getInstance(getApplicationContext());

        GlobalList = DB.GetAllNotes();

        noteView = findViewById(R.id.notesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        noteView.setLayoutManager(layoutManager);

        addNote = findViewById(R.id.addNoteImageButton);
        addNote.setOnClickListener(this::AddClick);

        noteAdapter = new NotesAdapter(getApplicationContext(), GlobalList);
        noteView.setAdapter(noteAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {

        GlobalList = DB.GetAllNotes();

        noteAdapter = new NotesAdapter(this, GlobalList);
        noteView.setAdapter(noteAdapter);
    }


    private void DBCheck(String path) {
        try {
            File file = new File(path);
        } catch (Exception e) {
        }
    }

    public void AddClick(View w){
        Intent i1 = new Intent(getApplicationContext(), AddNote.class);
        startActivity(i1);
    }
}