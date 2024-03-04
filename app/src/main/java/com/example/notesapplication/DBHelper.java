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
    private DBHelper(@Nullable Context context) {
        super(context, "match.db", null, 1);
    }

    public String createTable = "CREATE TABLE Notes (\n" +
            "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    NoteName TEXT,\n" +
            "    NoteText TEXT,\n" +
            "    NoteCategory INT,\n" +
            "    NotePriority INT\n" +
            ");";
    public String dropTable = "DROP TABLE MatchResults";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        if (i != il) {
            db.execSQL(dropTable);
            onCreate(db);
        }
    }

    public void Insert(String team1, int goal1, String team2, int goal2) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("INSERT into Notes('TeamHome', 'TeamGuest','GoalsHome','GoalsGuest') VALUES(?,?,?,?)", new Object[]{team1, team2, goal1, goal2});
    }

    public boolean Search(int id) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery("select * from Notes where ID = ?", new String[]{Integer.toString(id)});
            db.close();
            return c.getCount() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public Note Select(int id) {
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor c = db.rawQuery("select * FROM MatchResults", null);
//        if (c.moveToFirst()) {
//            do {
//
//                if (id == i) {
//                    return match;
//                }
//            } while (c.moveToNext());
//        }
//        c.close();
//        return null;
//    }

    public List<Note> SelectAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * FROM Notes", null);
        List<Note> notes = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                String name = c.getString(1);
                String text = c.getString(2);
                int category = c.getInt(3);
                int priority = c.getInt(4);
                Note note = new Note(id, name, text, category, priority);
                notes.add(note);
            } while (c.moveToNext());
        }
        c.close();
        return notes;
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
