package com.dkrichards.studentapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.dkrichards.studentapplication.R;
import com.dkrichards.studentapplication.databases.Database;
import com.dkrichards.studentapplication.models.Course;
import com.dkrichards.studentapplication.models.Term;
import com.dkrichards.studentapplication.ui.fragment.CourseDetailFragment;
import com.dkrichards.studentapplication.ui.fragment.TermDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Editor activity
 *
 * This was bootstrapped by AndroidStudio's activity template.
 */
public class CourseEditorActivity extends AppCompatActivity {
    private Course modifiedCourse;
    private Spinner termSpinner;
    private Spinner statusSpinner;
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);

        db = new Database(this);
        db.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        termSpinner = (Spinner) findViewById(R.id.term_spinner);
        List<Term> courseList = db.termDAO.getTerms();
        ArrayAdapter<Term> courseDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, courseList);
        courseDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(courseDataAdapter);

        statusSpinner = (Spinner) findViewById(R.id.type_spinner);
        List<String> statusList = new ArrayList<>();
        statusList.add("Plan to Take");
        statusList.add("In Progress");
        statusList.add("Completed");
        statusList.add("Dropped");
        ArrayAdapter<String> statusDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, statusList);
        statusDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusDataAdapter);

        Intent intent = getIntent();

        int modifiedCourseId = intent.getIntExtra(CourseDetailFragment.ARG_COURSE_ID, -1);
        int selectedTermId = intent.getIntExtra(TermDetailFragment.ARG_TERM_ID, -1);

        if (modifiedCourseId != -1) {
            modifiedCourse = db.courseDAO.getCourseById(modifiedCourseId);

            String courseTitle = modifiedCourse.getTitle();
            String courseStart = modifiedCourse.getStartDate();
            String courseEnd = modifiedCourse.getEndDate();

            EditText editTitle = findViewById(R.id.assessment_title);
            EditText editStart = findViewById(R.id.assessment_date);
            EditText editEnd = findViewById(R.id.course_end);

            editTitle.setText(courseTitle);
            editStart.setText(courseStart);
            editEnd.setText(courseEnd);

            findViewById(R.id.delete_course).setVisibility(View.VISIBLE);
        }

        if (selectedTermId != -1) {
            termSpinner.setSelection(selectedTermId);
        }
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public void onDelete(View view) {
        boolean removed = db.courseDAO.removeCourse(modifiedCourse);

        if (removed) {
            Intent intent = new Intent(this, CourseListActivity.class);
            startActivity(intent);
        }
    }

    public void onSave(View view) {
        EditText editTitle = findViewById(R.id.assessment_title);
        EditText editStart = findViewById(R.id.assessment_date);
        EditText editEnd = findViewById(R.id.course_end);

        String courseTitle = editTitle.getText().toString();
        String courseStart = editStart.getText().toString();
        String courseEnd = editEnd.getText().toString();
        String courseStatus = String.valueOf(statusSpinner.getSelectedItem());
        Term courseTerm = (Term) termSpinner.getSelectedItem();

        int courseId = db.courseDAO.getCourseCount();

        int modifiedCourseId = -1;

        if (modifiedCourse != null) {
            modifiedCourseId = modifiedCourse.getId();
            courseId = modifiedCourseId;
        }

        Course newCourse = new Course(courseId, courseTitle, courseStart, courseEnd, courseStatus, courseTerm.getId());

        boolean didSave = false;
        boolean isValid = newCourse.isValid();

        if (isValid && modifiedCourseId == -1) {
            // This is a new course, so add it to the DAO
            didSave = db.courseDAO.addCourse(newCourse);
        }
        else if (isValid) {
            // This is an updated course.
            didSave = db.courseDAO.updateCourse(newCourse);
        }

        if (didSave) {
            Intent intent = new Intent(this, CourseListActivity.class);
            startActivity(intent);
        }
    }
}
