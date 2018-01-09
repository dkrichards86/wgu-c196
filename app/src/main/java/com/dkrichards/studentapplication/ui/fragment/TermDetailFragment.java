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
import com.dkrichards.studentapplication.models.Course;
import com.dkrichards.studentapplication.models.Term;
import com.dkrichards.studentapplication.ui.activity.CourseDetailActivity;
import com.dkrichards.studentapplication.ui.activity.CourseEditorActivity;
import com.dkrichards.studentapplication.ui.activity.TermListActivity;
import com.dkrichards.studentapplication.ui.activity.TermDetailActivity;

import java.util.List;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link TermListActivity}
 * in two-pane mode (on tablets) or a {@link TermDetailActivity}
 * on handsets.
 *
 * This was bootstrapped by AndroidStudio's fragment template.
 */
public class TermDetailFragment extends Fragment {
    public static final String ARG_TERM_ID = "com.dkrichards.studentapplication.termdetailfragment.termid";
    private Term mTerm;
    public Database db;

    public TermDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new Database(getContext());
        db.open();

        if (getArguments().containsKey(ARG_TERM_ID)) {
            mTerm = db.termDAO.getTermById(getArguments().getInt(ARG_TERM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mTerm.getTitle());
            }
        }
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
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.term_detail, container, false);

        if (mTerm != null) {
            final int mTermId = mTerm.getId();

            ((TextView) rootView.findViewById(R.id.term_start_field)).setText(mTerm.getStartDate());
            ((TextView) rootView.findViewById(R.id.term_end_field)).setText(mTerm.getEndDate());

            ListView courseListView = (ListView) rootView.findViewById(R.id.course_list);
            List<Course> courseList = db.courseDAO.getCoursesByTerm(mTermId);

            if (courseList.size() > 0) {
                rootView.findViewById(R.id.course_list).setVisibility(View.VISIBLE);

                ArrayAdapter<Course> courseListDataAdapter = new ArrayAdapter<Course>(getActivity(),
                        android.R.layout.simple_list_item_1, courseList);

                courseListView.setAdapter(courseListDataAdapter);

                courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        Course c = (Course) adapter.getItemAtPosition(position);

                        Intent intent = new Intent(getActivity(), CourseDetailActivity.class);

                        intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, c.getId());

                        startActivity(intent);
                    }
                });


                setListHeight(courseListDataAdapter, courseListView);
            }
            else {
                rootView.findViewById(R.id.course_list).setVisibility(View.GONE);
            }
        }

        return rootView;
    }
}
