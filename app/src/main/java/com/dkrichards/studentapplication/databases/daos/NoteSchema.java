package com.dkrichards.studentapplication.databases.daos;

/**
 * This schema structure was largely derived from best practices laid out in:
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
public interface NoteSchema {
    String TABLE_NOTES = "notes";
    String NOTE_ID = "id";
    String NOTE_TITLE = "note_title";
    String NOTE_TEXT = "note_text";
    String NOTE_COURSE_ID = "note_course_id";

    String[] NOTES_COLUMNS = {NOTE_ID, NOTE_TITLE, NOTE_TEXT, NOTE_COURSE_ID};

    String NOTES_CREATE =
        "CREATE TABLE " + TABLE_NOTES + " (" +
            NOTE_ID + " INTEGER PRIMARY KEY, " +
            NOTE_TITLE + " TEXT, " +
            NOTE_TEXT + " TEXT, " +
            NOTE_COURSE_ID + " INTEGER " +
        ")";
}
