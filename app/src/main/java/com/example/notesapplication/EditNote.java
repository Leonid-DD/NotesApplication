package com.example.notesapplication;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditNote extends AppCompatActivity {

    Note note;
    DBHelper DB;
    Map<String, Integer> categoryMap = new HashMap<>();
    Map<String, Integer> priorityMap = new HashMap<>();
    int categoryID;
    int priorityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note_layout);

        DB = DBHelper.getInstance(this);

        int noteID = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("noteID")));
        note = DB.getNoteById(noteID);

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

        categoryID = note.getNoteCategory();
        categoryACTV.setText(categories[categoryID - 1]);
        priorityID = note.getNotePriority();
        priorityACTV.setText(priorities[priorityID - 1]);

        Button confirm = findViewById(R.id.confirmEdit);
        Button cancel = findViewById(R.id.cancelEdit);

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

        noteTitleET.setText(note.getNoteTitle());
        noteTextET.setText(note.getNoteText());

        View.OnClickListener confirmer = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String noteTitle = noteTitleET.getText().toString();
                String noteText = noteTextET.getText().toString();
                String noteCategory = category.getEditText().toString();
                String notePriority = priority.getEditText().toString();

                if (!noteTitle.isEmpty() && !noteText.isEmpty() && !noteCategory.isEmpty() && !notePriority.isEmpty()) {

                    // Inside your EditNote activity or wherever you edit a note
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditNote.this);
                    builder.setTitle("Confirm Edition");
                    builder.setMessage("Are you sure you want to save the changes to this note?");

                    // Positive button for confirmation
                    builder.setPositiveButton("Yes", (dialog, which) -> {

                        ContentValues newValue = new ContentValues();
                        newValue.put("title", noteTitle);
                        newValue.put("note_text", noteText);
                        newValue.put("category_id", categoryID);
                        newValue.put("priority_id", priorityID);

                        DB.Update(noteID, newValue);

                        dialog.dismiss();

                        finish();

                        // Optionally, navigate back to the previous screen or perform other actions
                        finish();
                    });

                    // Negative button for cancellation
                    builder.setNegativeButton("No", (dialog, which) -> {

                        dialog.dismiss();
                    });

                    // Create and show the AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                } else {
                    Toast.makeText(EditNote.this, "You didn't fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        };
        confirm.setOnClickListener(confirmer);

        View.OnClickListener canceler = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        cancel.setOnClickListener(canceler);
    }
}
