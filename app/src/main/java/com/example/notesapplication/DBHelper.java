package com.example.notesapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_NOTE_TEXT = "note_text";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_PRIORITY_ID = "priority_id";
    private static final String COLUMN_STATUS = "status";

    // Category reference table constants
    private static final String TABLE_CATEGORY = "category";
    private static final String COLUMN_CATEGORY_ID_REF = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";

    // Priority reference table constants
    private static final String TABLE_PRIORITY = "priority";
    private static final String COLUMN_PRIORITY_ID_REF = "id";
    private static final String COLUMN_PRIORITY_NAME = "name";

    private DBHelper(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNotesTable = "CREATE TABLE " + TABLE_NOTES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_NOTE_TEXT + " TEXT, "
                + COLUMN_CATEGORY_ID + " INTEGER, "
                + COLUMN_PRIORITY_ID + " INTEGER, "
                + COLUMN_STATUS + " INTEGER)";
        db.execSQL(createNotesTable);

        // Create category reference table
        String createCategoryTable = "CREATE TABLE " + TABLE_CATEGORY + " ("
                + COLUMN_CATEGORY_ID_REF + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CATEGORY_NAME + " TEXT)";
        db.execSQL(createCategoryTable);

        // Create priority reference table
        String createPriorityTable = "CREATE TABLE " + TABLE_PRIORITY + " ("
                + COLUMN_PRIORITY_ID_REF + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRIORITY_NAME + " TEXT)";
        db.execSQL(createPriorityTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {

    }

    public void Insert(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getNoteName());
        values.put(COLUMN_NOTE_TEXT, note.getNoteText());
        values.put(COLUMN_CATEGORY_ID, note.getNoteCategory());
        values.put(COLUMN_PRIORITY_ID, note.getNotePriority());
        values.put(COLUMN_STATUS, note.getNoteStatus() ? 1 : 0);
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    public Note getNoteById(int noteId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NOTES,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(noteId)},
                null,
                null,
                null
        );

        Note note = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int statusInt = cursor.getInt(5);
                boolean status = (statusInt == 1);
                note = new Note(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        status
                );
            }
            cursor.close();
        }

        db.close();
        return note;
    }

    public List<Note> GetAllNotes() {

        List<Note> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES, null);

        if (cursor.moveToFirst()) {
            do {
                int statusInt = cursor.getInt(5);
                boolean status = (statusInt == 1);
                Note note = new Note(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        status);
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notesList;
    }

    public void Update(int id, ContentValues newValue) {
        SQLiteDatabase db = getWritableDatabase();
        db.update("Notes", newValue, "ID = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    public void Delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from Notes where ID = ?", new String[]{Integer.toString(id)});
        db.close();
    }
}
