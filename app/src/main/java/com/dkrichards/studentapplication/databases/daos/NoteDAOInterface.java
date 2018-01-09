package com.dkrichards.studentapplication.databases.daos;

import com.dkrichards.studentapplication.models.Note;

import java.util.List;

/**
 * This interface structure was largely derived from best practices laid out in:
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
interface NoteDAOInterface {
    boolean addNote(Note note);

    Note getNoteById(int noteId);

    List<Note> getNotesByCourse(int courseId);

    int getNoteCount();

    List<Note> getNotes();

    boolean removeNote(Note note);

    boolean updateNote(Note note);
}
