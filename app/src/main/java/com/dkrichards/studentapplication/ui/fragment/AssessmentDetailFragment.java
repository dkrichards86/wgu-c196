package com.dkrichards.studentapplication.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dkrichards.studentapplication.R;
import com.dkrichards.studentapplication.databases.Database;
import com.dkrichards.studentapplication.models.Assessment;
import com.dkrichards.studentapplication.ui.activity.CourseDetailActivity;
import com.dkrichards.studentapplication.ui.activity.CourseListActivity;

/**
 * A fragment representing a single Course detail screen.
 * This fragment is either contained in a {@link CourseListActivity}
 * in two-pane mode (on tablets) or a {@link CourseDetailActivity}
 * on handsets.
 *
 * This was bootstrapped by AndroidStudio's fragment template.
 */
public class AssessmentDetailFragment extends Fragment {

    public static final String ARG_ASSESSMENT_ID = "com.dkrichards.studentapplication.assessmentdetailfragment.assessmentid";

    private Assessment mAssessment;
    public Database db;

    public AssessmentDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new Database(getContext());
        db.open();

        if (getArguments().containsKey(ARG_ASSESSMENT_ID)) {
            mAssessment = db.assessmentDAO.getAssessmentById(getArguments().getInt(ARG_ASSESSMENT_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mAssessment.getName());
            }
        }
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.assessment_detail, container, false);

        if (mAssessment != null) {
            ((TextView) rootView.findViewById(R.id.assessment_title_field)).setText(mAssessment.getName());
            ((TextView) rootView.findViewById(R.id.assessment_type_field)).setText(mAssessment.getType());
            ((TextView) rootView.findViewById(R.id.assessment_date_field)).setText(mAssessment.getDate());
        }

        return rootView;
    }
}
