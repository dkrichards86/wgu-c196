package com.dkrichards.studentapplication.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dkrichards.studentapplication.R;
import com.dkrichards.studentapplication.databases.Database;
import com.dkrichards.studentapplication.models.Assessment;
import com.dkrichards.studentapplication.receivers.AlarmReceiver;
import com.dkrichards.studentapplication.ui.fragment.AssessmentDetailFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An activity representing a single Assessment detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link Assessment}.
 *
 * This was bootstrapped by AndroidStudio's master/detail template.
 */
public class AssessmentDetailActivity extends AppCompatActivity {
    private Assessment selectedAssessment;
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        db = new Database(this);
        db.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedAssessmentId = getIntent().getIntExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, 0);

        selectedAssessment = db.assessmentDAO.getAssessmentById(selectedAssessmentId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentDetailActivity.this, AssessmentEditorActivity.class);

                intent.putExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, selectedAssessmentId);

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
            arguments.putInt(AssessmentDetailFragment.ARG_ASSESSMENT_ID,
                    getIntent().getIntExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, 0));

            AssessmentDetailFragment fragment = new AssessmentDetailFragment();

            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.assessment_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpTo(this, new Intent(this, AssessmentListActivity.class));
                return true;

            case R.id.action_reminder:
                String notificationTitle = "Assessment Reminder";
                String notificationText = "Assessment '" + selectedAssessment.getName() + "' is today.";

                Intent notificationIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                notificationIntent.putExtra("mNotificationTitle", notificationTitle);
                notificationIntent.putExtra("mNotificationContent", notificationText);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 123456789, notificationIntent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                    Date date = dateFormat.parse(selectedAssessment.getDate());

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
