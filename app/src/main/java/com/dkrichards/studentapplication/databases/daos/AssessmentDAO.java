package com.dkrichards.studentapplication.databases.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.dkrichards.studentapplication.databases.DbContentProvider;
import com.dkrichards.studentapplication.models.Assessment;

import java.util.ArrayList;
import java.util.List;

/**
 * This DAO structure was largely derived from best practices laid out in:
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
public class AssessmentDAO extends DbContentProvider
        implements AssessmentSchema, AssessmentDAOInterface {

    private Cursor cursor;
    private ContentValues initialValues;

    public AssessmentDAO(SQLiteDatabase db) {
        super(db);
    }

    /**
     * Add an assessment
     * @param assessment
     * @return
     */
    public boolean addAssessment(Assessment assessment) {
        setContentValue(assessment);

        try {
            return super.insert(TABLE_ASSESSMENTS, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     * Get all assessments associated with a particular course
     * @param courseId
     * @return
     */
    public List<Assessment> getAssessmentsByCourse(int courseId) {
        final String selectionArgs[] = { String.valueOf(courseId) };
        final String selection = ASSESSMENT_COURSE_ID + " = ?";

        List<Assessment> assessmentList = new ArrayList<>();

        cursor = super.query(TABLE_ASSESSMENTS, ASSESSMENTS_COLUMNS, selection,
                selectionArgs, ASSESSMENT_ID);

        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Assessment assessment = cursorToEntity(cursor);
                assessmentList.add(assessment);

                cursor.moveToNext();
            }
            cursor.close();
        }

        return assessmentList;
    }

    /**
     * Get a particular assessment
     *
     * @param assessmentId
     * @return
     */
    public Assessment getAssessmentById(int assessmentId) {
        final String selectionArgs[] = { String.valueOf(assessmentId) };
        final String selection = ASSESSMENT_ID + " = ?";

        Assessment assessment = null;

        cursor = super.query(TABLE_ASSESSMENTS, ASSESSMENTS_COLUMNS, selection,
                selectionArgs, ASSESSMENT_ID);

        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                assessment = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return assessment;
    }

    /**
     * Get the number of assessments
     *
     * @return
     */
    public int getAssessmentCount() {
        List<Assessment> assessmentList = getAssessments();

        return assessmentList.size();
    }

    /**
     * Get all assessments
     *
     * @return
     */
    public List<Assessment> getAssessments() {
        List<Assessment> assessmentList = new ArrayList<>();

        cursor = super.query(TABLE_ASSESSMENTS, ASSESSMENTS_COLUMNS, null,
                null, ASSESSMENT_ID);

        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Assessment assessment = cursorToEntity(cursor);
                assessmentList.add(assessment);

                cursor.moveToNext();
            }
            cursor.close();
        }

        return assessmentList;
    }

    /**
     * Remove an assessment
     *
     * @param assessment
     * @return
     */
    public boolean removeAssessment(Assessment assessment) {
        final String selectionArgs[] = { String.valueOf(assessment.getId()) };
        final String selection = ASSESSMENT_ID + " = ?";

        return super.delete(TABLE_ASSESSMENTS, selection, selectionArgs) > 0;
    }

    /**
     * Update an assessment
     *
     * @param assessment
     * @return
     */
    public boolean updateAssessment(Assessment assessment) {
        final String selectionArgs[] = { String.valueOf(assessment.getId()) };
        final String selection = ASSESSMENT_ID + " = ?";

        setContentValue(assessment);
        try {
            return super.update(TABLE_ASSESSMENTS, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     * Covert a cursor to Assessment
     * @param cursor
     * @return
     */
    protected Assessment cursorToEntity(Cursor cursor) {
        int assessmentIdIdx;
        int assessmentNameIdx;
        int assessmentTypeIdx;
        int assessmentDateIdx;
        int assessmentCourseIdIdx;

        int assessmentId = -1;
        String assessmentName = "";
        String assessmentType = "";
        String assessmentDate = "";
        int assessmentCourseId = -1;

        if (cursor != null) {
            if (cursor.getColumnIndex(ASSESSMENT_ID) != -1) {
                assessmentIdIdx = cursor.getColumnIndexOrThrow(ASSESSMENT_ID);
                assessmentId = cursor.getInt(assessmentIdIdx);
            }
            if (cursor.getColumnIndex(ASSESSMENT_NAME) != -1) {
                assessmentNameIdx = cursor.getColumnIndexOrThrow(ASSESSMENT_NAME);
                assessmentName = cursor.getString(assessmentNameIdx);
            }
            if (cursor.getColumnIndex(ASSESSMENT_DATE) != -1) {
                assessmentDateIdx = cursor.getColumnIndexOrThrow(ASSESSMENT_DATE);
                assessmentDate = cursor.getString(assessmentDateIdx);
            }
            if (cursor.getColumnIndex(ASSESSMENT_TYPE) != -1) {
                assessmentTypeIdx = cursor.getColumnIndexOrThrow(ASSESSMENT_TYPE);
                assessmentType = cursor.getString(assessmentTypeIdx);
            }
            if (cursor.getColumnIndex(ASSESSMENT_COURSE_ID) != -1) {
                assessmentCourseIdIdx = cursor.getColumnIndexOrThrow(ASSESSMENT_COURSE_ID);
                assessmentCourseId = cursor.getInt(assessmentCourseIdIdx);
            }
        }

        return new Assessment(assessmentId, assessmentName, assessmentType, assessmentDate, assessmentCourseId);

    }

    /**
     * Set instance variables for insert/update into the database
     *
     * @param assessment
     */
    private void setContentValue(Assessment assessment) {
        initialValues = new ContentValues();
        initialValues.put(ASSESSMENT_ID, assessment.getId());
        initialValues.put(ASSESSMENT_NAME, assessment.getName());
        initialValues.put(ASSESSMENT_DATE, assessment.getDate());
        initialValues.put(ASSESSMENT_TYPE, assessment.getType());
        initialValues.put(ASSESSMENT_COURSE_ID, assessment.getCourseId());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }
}