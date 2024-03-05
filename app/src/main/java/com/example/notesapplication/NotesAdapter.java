package com.example.notesapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapplication.Note;
import com.example.notesapplication.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private List<Note> notesList;
    DBHelper DB;

    public NotesAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.notesList = notesList;
        DB = DBHelper.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notesList.get(position);

        holder.noteTitleTextView.setText(note.getNoteTitle());
        holder.noteTextTextView.setText(note.getNoteText());

        // Combine category and priority in one TextView
        String categoryPriorityText = "Категория: " + DB.GetCategory(note.getNoteCategory()) +
                "\nПриоритет: " + DB.GetPriority(note.getNotePriority());
        holder.noteCategoryPriorityTextView.setText(categoryPriorityText);

        // Set the checkbox status
        holder.noteStatusCheckBox.setChecked(note.getNoteStatus());

        // Set click listeners for buttons
        holder.noteEditButton.setOnClickListener(v -> {
            // Implement your edit logic here
        });

        holder.noteDeleteButton.setOnClickListener(v -> {
            // Implement your delete logic here
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitleTextView;
        TextView noteTextTextView;
        TextView noteCategoryPriorityTextView;
        CheckBox noteStatusCheckBox;
        com.google.android.material.button.MaterialButton noteEditButton;
        com.google.android.material.button.MaterialButton noteDeleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView);
            noteTextTextView = itemView.findViewById(R.id.noteTextTextView);
            noteCategoryPriorityTextView = itemView.findViewById(R.id.noteCategoryPriorityTextView);
            noteStatusCheckBox = itemView.findViewById(R.id.noteStatusCheckBox);
            noteEditButton = itemView.findViewById(R.id.noteEditButton);
            noteDeleteButton = itemView.findViewById(R.id.noteDeleteButton);
        }
    }

    private String getCategoryName(int categoryId) {
        return "Category " + categoryId; // Replace with actual logic
    }

    private String getPriorityName(int priorityId) {
        // Implement logic to get priority name based on priorityId
        return "Priority " + priorityId; // Replace with actual logic
    }
}
