package com.dkrichards.studentapplication.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dkrichards.studentapplication.R;
import com.dkrichards.studentapplication.databases.Database;
import com.dkrichards.studentapplication.models.Assessment;
import com.dkrichards.studentapplication.models.Course;
import com.dkrichards.studentapplication.models.Mentor;
import com.dkrichards.studentapplication.models.Note;
import com.dkrichards.studentapplication.ui.activity.AssessmentEditorActivity;
import com.dkrichards.studentapplication.ui.activity.CourseDetailActivity;
import com.dkrichards.studentapplication.ui.activity.CourseListActivity;
import com.dkrichards.studentapplication.ui.activity.MentorEditorActivity;
import com.dkrichards.studentapplication.ui.activity.NoteDetailActivity;

import java.util.List;

/**
 * A fragment representing a single Course detail screen.
 * This fragment is either contained in a {@link CourseListActivity}
 * in two-pane mode (on tablets) or a {@link CourseDetailActivity}
 * on handsets.
 *
 * This was bootstrapped by AndroidStudio's fragment template.
 */
public class CourseDetailFragment extends Fragment {

    public static final String ARG_COURSE_ID = "com.dkrichards.studentapplication.coursedetailfragment.courseid";
    public static final String ARG_MENTOR_ID = "com.dkrichards.studentapplication.coursedetailfragment.mentorid";

    private Course mCourse;
    public Database db;

    public CourseDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new Database(getContext());
        db.open();

        if (getArguments().containsKey(ARG_COURSE_ID)) {
            mCourse = db.courseDAO.getCourseById(getArguments().getInt(ARG_COURSE_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mCourse.getTitle());
            }
        }
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    private void setListHeight(ArrayAdapter adapter, ListView lv) {
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, lv);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (lv.getCount() - 1));
        lv.setLayoutParams(params);
        lv.requestLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.course_detail, container, false);

        if (mCourse != null) {
            ((TextView) rootView.findViewById(R.id.assessment_title_field)).setText(mCourse.getTitle());
            ((TextView) rootView.findViewById(R.id.assessment_type_field)).setText(mCourse.getStatus());
            ((TextView) rootView.findViewById(R.id.assessment_date_field)).setText(mCourse.getStartDate());
            ((TextView) rootView.findViewById(R.id.course_end_field)).setText(mCourse.getEndDate());

            final int mCourseId = mCourse.getId();

            List<Mentor> mentorList = db.mentorDAO.getMentorsByCourse(mCourseId);
            if (mentorList.size() > 0) {
                rootView.findViewById(R.id.mentor_list).setVisibility(View.VISIBLE);

                ListView mentorListView = (ListView) rootView.findViewById(R.id.mentor_list);
                ArrayAdapter<Mentor> mentorListDataAdapter = new ArrayAdapter<Mentor>(getActivity(),
                        android.R.layout.simple_list_item_1, mentorList);

                mentorListView.setAdapter(mentorListDataAdapter);

                mentorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        Mentor m = (Mentor) adapter.getItemAtPosition(position);

                        Intent intent = new Intent(getActivity(), MentorEditorActivity.class);

                        intent.putExtra(ARG_MENTOR_ID, m.getId());

                        startActivity(intent);
                    }
                });

                setListHeight(mentorListDataAdapter, mentorListView);
            }
            else {
                rootView.findViewById(R.id.mentor_list).setVisibility(View.GONE);
            }

            List<Assessment> assessmentList = db.assessmentDAO.getAssessmentsByCourse(mCourseId);
            if (assessmentList.size() > 0) {
                rootView.findViewById(R.id.assessment_list).setVisibility(View.VISIBLE);

                ListView assessmentListView = (ListView) rootView.findViewById(R.id.assessment_list);

                ArrayAdapter<Assessment> assessmentListDataAdapter = new ArrayAdapter<Assessment>(getActivity(),
                        android.R.layout.simple_list_item_1, assessmentList);

                assessmentListView.setAdapter(assessmentListDataAdapter);

                assessmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        Assessment a = (Assessment) adapter.getItemAtPosition(position);

                        Intent intent = new Intent(getActivity(), AssessmentEditorActivity.class);

                        intent.putExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, a.getId());

                        startActivity(intent);
                    }
                });

                setListHeight(assessmentListDataAdapter, assessmentListView);
            }
            else {
                rootView.findViewById(R.id.assessment_list).setVisibility(View.GONE);
            }

            List<Note> noteList = db.noteDAO.getNotesByCourse(mCourseId);
            if (noteList.size() > 0) {
                rootView.findViewById(R.id.note_list).setVisibility(View.VISIBLE);

                ListView noteListView = (ListView) rootView.findViewById(R.id.note_list);

                ArrayAdapter<Note> noteListDataAdapter = new ArrayAdapter<Note>(getActivity(),
                        android.R.layout.simple_list_item_1, noteList);

                noteListView.setAdapter(noteListDataAdapter);

                noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        Note n = (Note) adapter.getItemAtPosition(position);

                        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);

                        intent.putExtra(NoteDetailFragment.ARG_NOTE_ID, n.getId());

                        startActivity(intent);
                    }
                });

                setListHeight(noteListDataAdapter, noteListView);
            }
            else {
                rootView.findViewById(R.id.note_list).setVisibility(View.GONE);
            }
        }

        return rootView;
    }
}
