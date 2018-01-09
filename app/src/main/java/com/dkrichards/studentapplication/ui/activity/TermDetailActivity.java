package com.dkrichards.studentapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.dkrichards.studentapplication.R;
import com.dkrichards.studentapplication.databases.Database;
import com.dkrichards.studentapplication.models.Course;
import com.dkrichards.studentapplication.models.Term;
import com.dkrichards.studentapplication.ui.fragment.CourseDetailFragment;
import com.dkrichards.studentapplication.ui.fragment.TermDetailFragment;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TermListActivity}.
 *
 * This was bootstrapped by AndroidStudio's master/detail template.
 */
public class TermDetailActivity extends AppCompatActivity {
    public static final String TERM_ID = "com.dkrichards.studentapplication.termdetailactivity.termid";
    private Term selectedTerm;
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        db = new Database(this);
        db.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedTermId = getIntent().getIntExtra(TermDetailFragment.ARG_TERM_ID, 0);

        selectedTerm = db.termDAO.getTermById(selectedTermId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(TermDetailActivity.this, TermEditorActivity.class);

            intent.putExtra(TermDetailFragment.ARG_TERM_ID, selectedTermId);

            startActivity(intent);
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(TermDetailFragment.ARG_TERM_ID,
                    getIntent().getIntExtra(TermDetailFragment.ARG_TERM_ID, 0));

            TermDetailFragment fragment = new TermDetailFragment();

            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.term_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, TermListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCourseEdit(View view) {
        Intent intent = new Intent(this, CourseEditorActivity.class);

        intent.putExtra(TermDetailFragment.ARG_TERM_ID, selectedTerm.getId());

        startActivity(intent);
    }
}
