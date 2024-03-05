package com.example.notesapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AddNote extends AppCompatActivity {

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_layout);

        DB = DBHelper.getInstance(this);

        String[] categories = DB.GetCategories().toArray(new String[0]);
        String[] priorities = DB.GetPriorities().toArray(new String[0]);

        EditText noteTitleET = findViewById(R.id.noteTitleEditText);
        EditText noteTextET = findViewById(R.id.noteTextEditText);
        TextInputLayout category = findViewById(R.id.category);
        TextInputLayout priority = findViewById(R.id.priority);

        Button save = findViewById(R.id.confirmAdd);
        Button cancel = findViewById(R.id.cancelAdd);

        if (category.getEditText() instanceof MaterialAutoCompleteTextView) {
            ((MaterialAutoCompleteTextView) category.getEditText()).setSimpleItems(categories);
        }

        if (priority.getEditText() instanceof MaterialAutoCompleteTextView) {
            ((MaterialAutoCompleteTextView) priority.getEditText()).setSimpleItems(priorities);
        }

        View.OnClickListener saver = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String noteTitle = noteTitleET.getText().toString();
                String noteText = noteTextET.getText().toString();
                String noteCategory = category.getEditText().toString();
                String notePriority = priority.getEditText().toString();

                if (!noteTitle.isEmpty() && !noteText.isEmpty() && !noteCategory.isEmpty() && !notePriority.isEmpty()) {

                    //Note note = new Note(0, noteTitle, noteText, noteCategory, notePriority);

                }
            }
        };
        save.setOnClickListener(saver);

        View.OnClickListener canceler = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        cancel.setOnClickListener(canceler);
    }

}
