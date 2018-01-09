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
import com.dkrichards.studentapplication.models.Note;

/**
 * A fragment representing a single Note detail screen.
 *
 * This was bootstrapped by AndroidStudio's fragment template.
 */
public class NoteDetailFragment extends Fragment {

    public static final String ARG_NOTE_ID = "com.dkrichards.studentapplication.assessmentdetailfragment.assessmentid";

    private Note mNote;
    public Database db;

    public NoteDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new Database(getContext());
        db.open();

        if (getArguments().containsKey(ARG_NOTE_ID)) {
            mNote = db.noteDAO.getNoteById(getArguments().getInt(ARG_NOTE_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mNote.getTitle());
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
        View rootView = inflater.inflate(R.layout.note_detail, container, false);

        if (mNote != null) {
            ((TextView) rootView.findViewById(R.id.note_title_field)).setText(mNote.getTitle());
            ((TextView) rootView.findViewById(R.id.note_text_field)).setText(mNote.getText());
        }

        return rootView;
    }
}
