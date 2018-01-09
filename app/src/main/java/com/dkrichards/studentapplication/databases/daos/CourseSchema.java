package com.dkrichards.studentapplication.databases.daos;

/**
 * This schema structure was largely derived from best practices laid out in:
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
public interface CourseSchema {
    String TABLE_COURSES = "courses";
    String COURSE_ID = "id";
    String COURSE_TITLE = "course_title";
    String COURSE_START_DATE = "course_start_date";
    String COURSE_END_DATE = "course_end_date";
    String COURSE_STATUS = "course_status";
    String COURSE_TERM_ID = "course_term_id";

    String[] COURSES_COLUMNS = {COURSE_ID, COURSE_TITLE, COURSE_START_DATE, COURSE_END_DATE, COURSE_STATUS, COURSE_TERM_ID};

    String COURSES_CREATE =
        "CREATE TABLE " + TABLE_COURSES + " (" +
            COURSE_ID + " INTEGER PRIMARY KEY, " +
            COURSE_TITLE + " TEXT, " +
            COURSE_START_DATE + " TEXT, " +
            COURSE_END_DATE + " TEXT, " +
            COURSE_STATUS + " TEXT, " +
            COURSE_TERM_ID + " INTEGER" +
        ")";
}
