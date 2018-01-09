package com.dkrichards.studentapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.dkrichards.studentapplication.R;
import com.dkrichards.studentapplication.databases.Database;
import com.dkrichards.studentapplication.models.Course;
import com.dkrichards.studentapplication.models.Term;
import com.dkrichards.studentapplication.ui.fragment.TermDetailFragment;

import java.util.List;

/**
 * Editor activity
 *
 * This was bootstrapped by AndroidStudio's activity template.
 */
public class TermEditorActivity extends AppCompatActivity {
    private Term modifiedTerm;
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);

        db = new Database(this);
        db.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        int modifiedTermId = intent.getIntExtra(TermDetailFragment.ARG_TERM_ID, -1);

        if (modifiedTermId != -1) {
            modifiedTerm = db.termDAO.getTermById(modifiedTermId);

            String termTitle = modifiedTerm.getTitle();
            String termStart = modifiedTerm.getStartDate();
            String termEnd = modifiedTerm.getEndDate();


            EditText editTitle = findViewById(R.id.term_title);
            EditText editStart = findViewById(R.id.term_start);
            EditText editEnd = findViewById(R.id.term_end);

            editTitle.setText(termTitle);
            editStart.setText(termStart);
            editEnd.setText(termEnd);

            findViewById(R.id.delete_term).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public void onDelete(View view) {
        List<Course> courseList = db.courseDAO.getCoursesByTerm(modifiedTerm.getId());

        boolean removed = false;

        if (courseList.size() == 0 ) {
            removed = db.termDAO.removeTerm(modifiedTerm);
        }

        if (removed) {
            Intent intent = new Intent(this, TermListActivity.class);
            startActivity(intent);
        }
    }

    public void onSave(View view) {
        EditText editTitle = findViewById(R.id.term_title);
        EditText editStart = findViewById(R.id.term_start);
        EditText editEnd = findViewById(R.id.term_end);

        String termTitle = editTitle.getText().toString();
        String termStart = editStart.getText().toString();
        String termEnd = editEnd.getText().toString();

        int termId = db.termDAO.getTermCount();

        int modifiedTermId = -1;

        if (modifiedTerm != null) {
            modifiedTermId = modifiedTerm.getId();
            termId = modifiedTermId;
        }

        Term newTerm = new Term(termId, termTitle, termStart, termEnd);

        boolean didSave = false;
        boolean isValid = newTerm.isValid();

        if (isValid && modifiedTermId == -1) {
            didSave = db.termDAO.addTerm(newTerm);
        }
        else if (isValid) {
            didSave = db.termDAO.updateTerm(newTerm);
        }

        if (didSave) {
            Intent intent = new Intent(this, TermListActivity.class);
            startActivity(intent);
        }
    }
}
