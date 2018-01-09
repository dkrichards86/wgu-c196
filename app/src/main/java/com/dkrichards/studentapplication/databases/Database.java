package com.dkrichards.studentapplication.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dkrichards.studentapplication.databases.daos.AssessmentDAO;
import com.dkrichards.studentapplication.databases.daos.AssessmentSchema;
import com.dkrichards.studentapplication.databases.daos.CourseDAO;
import com.dkrichards.studentapplication.databases.daos.CourseSchema;
import com.dkrichards.studentapplication.databases.daos.MentorDAO;
import com.dkrichards.studentapplication.databases.daos.MentorSchema;
import com.dkrichards.studentapplication.databases.daos.NoteDAO;
import com.dkrichards.studentapplication.databases.daos.NoteSchema;
import com.dkrichards.studentapplication.databases.daos.TermDAO;
import com.dkrichards.studentapplication.databases.daos.TermSchema;

/**
 * This Database class was largely derived from best practices laid out in:
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
public class Database {
    // Database name and version
    private static final String DATABASE_NAME = "studentapp.db";
    private static final int DATABASE_VERSION = 2;

    private DatabaseHelper mDbHelper;
    private final Context mContext;

    // Database DAOs
    public static TermDAO termDAO;
    public static CourseDAO courseDAO;
    public static AssessmentDAO assessmentDAO;
    public static MentorDAO mentorDAO;
    public static NoteDAO noteDAO;

    /**
     * Constructor
     *
     * @param context
     */
    public Database(Context context) {
        this.mContext = context;
    }

    /**
     * Open the database, attaching DAOs to the instance
     *
     * @return
     */
    public Database open() {
        mDbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

        termDAO = new TermDAO(mDb);
        courseDAO = new CourseDAO(mDb);
        assessmentDAO = new AssessmentDAO(mDb);
        mentorDAO = new MentorDAO(mDb);
        noteDAO = new NoteDAO(mDb);

        return this;
    }


    /**
     * Close the instance
     */
    public void close() {
        mDbHelper.close();
    }

    /**
     * Database helper. This provides basic migration functionality
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TermSchema.TERMS_CREATE);
            db.execSQL(CourseSchema.COURSES_CREATE);
            db.execSQL(AssessmentSchema.ASSESSMENTS_CREATE);
            db.execSQL(MentorSchema.MENTORS_CREATE);
            db.execSQL(NoteSchema.NOTES_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int o, int n) {
            db.execSQL("DROP TABLE IF EXISTS " + TermSchema.TABLE_TERMS);
            db.execSQL("DROP TABLE IF EXISTS " + CourseSchema.TABLE_COURSES);
            db.execSQL("DROP TABLE IF EXISTS " + AssessmentSchema.TABLE_ASSESSMENTS);
            db.execSQL("DROP TABLE IF EXISTS " + MentorSchema.TABLE_MENTORS);
            db.execSQL("DROP TABLE IF EXISTS " + NoteSchema.TABLE_NOTES);

            onCreate(db);
        }
    }

}