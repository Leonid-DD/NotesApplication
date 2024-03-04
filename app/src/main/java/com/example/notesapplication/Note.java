package com.example.notesapplication;

public class Note {

    private int ID;
    private String NoteName;
    private String NoteText;
    private int NoteCategory;
    private int NotePriority;

    public Note(int id, String name, String text, int category, int priority) {
        this.ID = id;
        this.NoteName = name;
        this.NoteText = text;
        this.NoteCategory = category;
        this.NotePriority = priority;
    }

    public int getNoteID() {
        return ID;
    }
    public String getNoteName() {
        return NoteName;
    }

    public String getNoteText() {
        return NoteText;
    }

    public String getNoteCategory() {
        return NoteCategory;
    }

    public String getNotePriority() {
        return NotePriority;
    }

    public void setNoteID(int id) {
        ID = id;
    }

    public void setNoteName(String noteName) {
        NoteName = noteName;
    }

    public void setNoteText(String noteText) {
        NoteText = noteText;
    }

    public void setNoteCategory(String noteCategory) {
        NoteCategory = noteCategory;
    }

    public void setNotePriority(String notePriority) {
        NotePriority = notePriority;
    }
}
