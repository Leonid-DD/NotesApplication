package com.example.notesapplication;

public class Note {

    private int ID;
    private String NoteName;
    private String NoteText;
    private int NoteCategory;
    private int NotePriority;
    private boolean NoteStatus;

    public Note(int id, String name, String text, int category, int priority, boolean status) {
        this.ID = id;
        this.NoteName = name;
        this.NoteText = text;
        this.NoteCategory = category;
        this.NotePriority = priority;
        this.NoteStatus = status;
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

    public int getNoteCategory() {
        return NoteCategory;
    }

    public int getNotePriority() {
        return NotePriority;
    }
    public boolean getNoteStatus() { return NoteStatus; }

    public void setNoteID(int id) {
        ID = id;
    }

    public void setNoteName(String noteName) {
        NoteName = noteName;
    }

    public void setNoteText(String noteText) {
        NoteText = noteText;
    }

    public void setNoteCategory(int noteCategory) {
        NoteCategory = noteCategory;
    }

    public void setNotePriority(int notePriority) {
        NotePriority = notePriority;
    }
    public void setNoteStatus(boolean noteStatus) { NoteStatus = noteStatus; }
}
