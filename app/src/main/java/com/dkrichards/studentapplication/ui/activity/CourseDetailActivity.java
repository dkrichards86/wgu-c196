package com.dkrichards.studentapplication.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.dkrichards.studentapplication.R;
import com.dkrichards.studentapplication.databases.Database;
import com.dkrichards.studentapplication.models.Course;
import com.dkrichards.studentapplication.receivers.AlarmReceiver;
import com.dkrichards.studentapplication.ui.fragment.CourseDetailFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An activity representing a single Course detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CourseListActivity}.
 *
 * This was bootstrapped by AndroidStudio's master/detail template.
 */
public class CourseDetailActivity extends AppCompatActivity {
    private Course selectedCourse;
    public Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        db = new Database(this);
        db.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedCourseId = getIntent().getIntExtra(CourseDetailFragment.ARG_COURSE_ID, 0);

        selectedCourse = db.courseDAO.getCourseById(selectedCourseId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetailActivity.this, CourseEditorActivity.class);

                intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourseId);

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
            arguments.putInt(CourseDetailFragment.ARG_COURSE_ID,
                    getIntent().getIntExtra(CourseDetailFragment.ARG_COURSE_ID, 0));

            CourseDetailFragment fragment = new CourseDetailFragment();

            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.course_detail_container, fragment)
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

                // Course start alarm set up
                String startNotificationTitle = "Course Start Reminder";
                String startNotificationText = selectedCourse.getTitle() + " begins today.";

                Intent startNotificationIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                startNotificationIntent.putExtra("mNotificationTitle", startNotificationTitle);
                startNotificationIntent.putExtra("mNotificationContent", startNotificationText);

                PendingIntent startPendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, startNotificationIntent, 0);

                AlarmManager startAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                // Course end alarm set up
                String endNotificationTitle = "Course End Reminder";
                String endNotificationText = selectedCourse.getTitle() + " ends today.";

                Intent endNotificationIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                endNotificationIntent.putExtra("mNotificationTitle", endNotificationTitle);
                endNotificationIntent.putExtra("mNotificationContent", endNotificationText);

                PendingIntent endPendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1, endNotificationIntent, 0);

                AlarmManager endAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

                    // Start alarm
                    Date startDate = dateFormat.parse(selectedCourse.getStartDate());
                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(startDate);
                    startAlarmManager.set(AlarmManager.RTC_WAKEUP, startCal.getTimeInMillis(), startPendingIntent);

                    // End alarm
                    Date endDate = dateFormat.parse(selectedCourse.getEndDate());
                    Calendar endCal = Calendar.getInstance();
                    endCal.setTime(endDate);
                    endAlarmManager.set(AlarmManager.RTC_WAKEUP, endCal.getTimeInMillis(), endPendingIntent);
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

    public void onMentorEdit(View view) {
        Intent intent = new Intent(this, MentorEditorActivity.class);

        intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourse.getId());

        startActivity(intent);
    }

    public void onAssessmentEdit(View view) {
        Intent intent = new Intent(this, AssessmentEditorActivity.class);

        intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourse.getId());

        startActivity(intent);
    }

    public void onNoteEdit(View view) {
        Intent intent = new Intent(this, NoteEditorActivity.class);

        intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourse.getId());

        startActivity(intent);
    }
}
