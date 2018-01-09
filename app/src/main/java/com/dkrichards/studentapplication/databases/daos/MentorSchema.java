package com.dkrichards.studentapplication.databases.daos;

/**
 * This schema structure was largely derived from best practices laid out in:
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
public interface MentorSchema {
    String TABLE_MENTORS = "mentors";
    String MENTOR_ID = "id";
    String MENTOR_NAME = "mentor_name";
    String MENTOR_PHONE = "mentor_phone";
    String MENTOR_EMAIL = "mentor_email";
    String MENTOR_COURSE_ID = "mentor_course_id";

    String[] MENTOR_COLUMNS = {MENTOR_ID, MENTOR_NAME, MENTOR_PHONE, MENTOR_EMAIL, MENTOR_COURSE_ID};

    String MENTORS_CREATE =
        "CREATE TABLE " + TABLE_MENTORS + " (" +
            MENTOR_ID + " INTEGER PRIMARY KEY, " +
            MENTOR_NAME + " TEXT, " +
            MENTOR_PHONE + " TEXT, " +
            MENTOR_EMAIL + " TEXT, " +
            MENTOR_COURSE_ID + " INTEGER" +
        ")";
}
