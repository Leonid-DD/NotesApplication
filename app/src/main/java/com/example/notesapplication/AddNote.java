package com.example.notesapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNote extends AppCompatActivity {

    DBHelper DB;
    Map<String, Integer> categoryMap = new HashMap<>();
    Map<String, Integer> priorityMap = new HashMap<>();
    int categoryID;
    int priorityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_layout);

        DB = DBHelper.getInstance(this);

        categoryMap = DB.populateCategory(categoryMap);
        priorityMap = DB.populatePriority(priorityMap);

        String[] categories = DB.GetCategories().toArray(new String[0]);
        String[] priorities = DB.GetPriorities().toArray(new String[0]);

        EditText noteTitleET = findViewById(R.id.noteTitleEditText);
        EditText noteTextET = findViewById(R.id.noteTextEditText);

        TextInputLayout category = findViewById(R.id.category);
        TextInputLayout priority = findViewById(R.id.priority);
        AutoCompleteTextView categoryACTV = (AutoCompleteTextView) category.getEditText();
        AutoCompleteTextView priorityACTV = (AutoCompleteTextView) priority.getEditText();

        assert categoryACTV != null;
        categoryACTV.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories));
        assert priorityACTV != null;
        priorityACTV.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, priorities));

        Button save = findViewById(R.id.confirmAdd);
        Button cancel = findViewById(R.id.cancelAdd);

        if (category.getEditText() instanceof MaterialAutoCompleteTextView) {
            ((MaterialAutoCompleteTextView) category.getEditText()).setSimpleItems(categories);
        }

        if (priority.getEditText() instanceof MaterialAutoCompleteTextView) {
            ((MaterialAutoCompleteTextView) priority.getEditText()).setSimpleItems(priorities);
        }

        categoryACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String catString = categories[position];
                categoryID = categoryMap.get(catString);

            }
        });

        priorityACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String priorString = priorities[position];
                priorityID = priorityMap.get(priorString);

            }
        });

        View.OnClickListener saver = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String noteTitle = noteTitleET.getText().toString();
                String noteText = noteTextET.getText().toString();
                String noteCategory = category.getEditText().toString();
                String notePriority = priority.getEditText().toString();

                if (!noteTitle.isEmpty() && !noteText.isEmpty() && !noteCategory.isEmpty() && !notePriority.isEmpty()) {

                    Note note = new Note(0, noteTitle, noteText, categoryID, priorityID, false);
                    DB.Insert(note);

                    Toast.makeText(AddNote.this, "Note added succesfully", Toast.LENGTH_SHORT).show();

                    finish();
                }
                else {
                    Toast.makeText(AddNote.this, "You didn't fill all fields", Toast.LENGTH_SHORT).show();
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
