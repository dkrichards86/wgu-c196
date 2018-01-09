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
import com.dkrichards.studentapplication.models.Assessment;
import com.dkrichards.studentapplication.models.Course;
import com.dkrichards.studentapplication.ui.fragment.AssessmentDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class AssessmentEditorActivity extends AppCompatActivity {
    private Assessment modifiedAssessment;
    private Spinner courseSpinner;
    private Spinner typeSpinner;
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_editor);

        db = new Database(this);
        db.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseSpinner = (Spinner) findViewById(R.id.term_spinner);
        List<Course> courseList = db.courseDAO.getCourses();
        ArrayAdapter<Course> courseDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, courseList);
        courseDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseDataAdapter);

        typeSpinner = (Spinner) findViewById(R.id.type_spinner);
        List<String> statusList = new ArrayList<>();
        statusList.add("Objective Assessment");
        statusList.add("Performance Assessment");
        ArrayAdapter<String> statusDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, statusList);
        statusDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(statusDataAdapter);

        Intent intent = getIntent();

        int modifiedAssessmentId = intent.getIntExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, -1);

        if (modifiedAssessmentId != -1) {
            modifiedAssessment = db.assessmentDAO.getAssessmentById(modifiedAssessmentId);

            String assessmentTitle = modifiedAssessment.getName();
            String assessmentDate = modifiedAssessment.getDate();

            EditText editTitle = findViewById(R.id.assessment_title);
            EditText editDate = findViewById(R.id.assessment_date);

            editTitle.setText(assessmentTitle);
            editDate.setText(assessmentDate);

            findViewById(R.id.delete_course).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public void onDelete(View view) {
        boolean removed = db.assessmentDAO.removeAssessment(modifiedAssessment);

        if (removed) {
            Intent intent = new Intent(this, AssessmentListActivity.class);
            startActivity(intent);
        }
    }

    public void onSave(View view) {
        EditText editTitle = findViewById(R.id.assessment_title);
        EditText editDate = findViewById(R.id.assessment_date);

        String assessmentTitle = editTitle.getText().toString();
        String assessmentDate = editDate.getText().toString();
        String assessmentType = String.valueOf(typeSpinner.getSelectedItem());
        Course assessmentCourse = (Course) courseSpinner.getSelectedItem();

        int assessmentId = db.assessmentDAO.getAssessmentCount();

        int modifiedAssessmentId = -1;

        if (modifiedAssessment != null) {
            modifiedAssessmentId = modifiedAssessment.getId();
            assessmentId = modifiedAssessmentId;
        }
        Assessment newAssessment = new Assessment(assessmentId, assessmentTitle, assessmentType, assessmentDate, assessmentCourse.getId());

        boolean didSave = false;
        boolean isValid = newAssessment.isValid();

        if (isValid && modifiedAssessmentId == -1) {
            // This is a new course, so add it to the DAO
            didSave = db.assessmentDAO.addAssessment(newAssessment);
        }
        else if (isValid) {
            // This is an updated course.
            didSave = db.assessmentDAO.updateAssessment(newAssessment);
        }

        if (didSave) {
            Intent intent = new Intent(this, AssessmentListActivity.class);
            startActivity(intent);
        }
    }
}
