package com.dkrichards.studentapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.dkrichards.studentapplication.R;
import com.dkrichards.studentapplication.databases.Database;
import com.dkrichards.studentapplication.models.Mentor;
import com.dkrichards.studentapplication.ui.fragment.CourseDetailFragment;

/**
 * Editor activity
 *
 * This was bootstrapped by AndroidStudio's activity template.
 */
public class MentorEditorActivity extends AppCompatActivity {
    private Mentor modifiedMentor;
    private int courseId;
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_editor);

        db = new Database(this);
        db.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        int modifiedMentorId = intent.getIntExtra(CourseDetailFragment.ARG_MENTOR_ID, -1);
        courseId = intent.getIntExtra(CourseDetailFragment.ARG_COURSE_ID, -1);

        if (modifiedMentorId != -1) {
            modifiedMentor = db.mentorDAO.getMentorById(modifiedMentorId);

            String mentorName = modifiedMentor.getName();
            String mentorEmail = modifiedMentor.getEmail();
            String mentorPhone = modifiedMentor.getPhone();

            EditText editName = findViewById(R.id.mentor_name);
            EditText editEmail = findViewById(R.id.mentor_email);
            EditText editPhone = findViewById(R.id.mentor_phone);

            editName.setText(mentorName);
            editEmail.setText(mentorEmail);
            editPhone.setText(mentorPhone);

            findViewById(R.id.delete_mentor).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public void onDelete(View view) {
        int mentorCourse = modifiedMentor.getCourseId();
        boolean removed = db.mentorDAO.removeMentor(modifiedMentor);

        if (removed) {
            Intent intent = new Intent(this, CourseDetailActivity.class);

            intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, mentorCourse);

            startActivity(intent);
        }
    }

    public void onSave(View view) {

        EditText editName = findViewById(R.id.mentor_name);
        EditText editEmail = findViewById(R.id.mentor_email);
        EditText editPhone = findViewById(R.id.mentor_phone);

        String mentorName = editName.getText().toString();
        String mentorEmail = editEmail.getText().toString();
        String mentorPhone = editPhone.getText().toString();

        int mentorId = db.mentorDAO.getMentorCount();

        int modifiedMentorId = -1;

        if (modifiedMentor != null) {
            modifiedMentorId = modifiedMentor.getId();
            mentorId = modifiedMentorId;
        }

        Mentor newMentor = new Mentor(mentorId, mentorName, mentorPhone, mentorEmail, courseId);

        boolean didSave = false;
        boolean isValid = newMentor.isValid();

        if (isValid && modifiedMentorId == -1) {
            didSave = db.mentorDAO.addMentor(newMentor);
        }
        else if (isValid) {
            didSave = db.mentorDAO.updateMentor(newMentor);
        }

        if (didSave) {
            Intent intent = new Intent(this, CourseDetailActivity.class);

            intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, courseId);

            startActivity(intent);
        }
    }
}
