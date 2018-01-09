package com.dkrichards.studentapplication.databases.daos;

/**
 * This schema structure was largely derived from best practices laid out in:
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
public interface TermSchema {
    String TABLE_TERMS = "terms";
    String TERM_ID = "id";
    String TERM_TITLE = "term_title";
    String TERM_START_DATE = "term_start_date";
    String TERM_END_DATE = "term_end_date";

    String[] TERMS_COLUMNS = {TERM_ID, TERM_TITLE, TERM_START_DATE, TERM_END_DATE};

    String TERMS_CREATE =
        "CREATE TABLE " + TABLE_TERMS + " (" +
            TERM_ID + " INTEGER PRIMARY KEY, " +
            TERM_TITLE + " TEXT, " +
            TERM_START_DATE + " TEXT, " +
            TERM_END_DATE + " TEXT" +
        ")";
}
