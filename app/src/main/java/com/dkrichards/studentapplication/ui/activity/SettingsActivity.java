package com.dkrichards.studentapplication.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.dkrichards.studentapplication.R;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPref = this.getSharedPreferences(SharingActivity.SHARED_PREFS_FILENAME, 0);

        RadioButton shareEmail = findViewById(R.id.share_email);
        RadioButton shareSMS = findViewById(R.id.share_sms);

        String shareFromSharedPref = sharedPref.getString(SharingActivity.SHARED_PREF_SHARE, "EMAIL");
        if (shareFromSharedPref.equals("EMAIL")) {
            shareEmail.setChecked(true);
        }
        else {
            shareSMS.setChecked(true);
        }

    }

    public void onShareClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        SharedPreferences.Editor editor = sharedPref.edit();

        switch(view.getId()) {
            case R.id.share_email:
                if (checked) {
                    editor.putString(SharingActivity.SHARED_PREF_SHARE, "EMAIL");
                }
                else {
                    editor.putString(SharingActivity.SHARED_PREF_SHARE, "SMS");
                }
                break;
            case R.id.share_sms:
                if (checked) {
                    editor.putString(SharingActivity.SHARED_PREF_SHARE, "SMS");
                }
                else {
                    editor.putString(SharingActivity.SHARED_PREF_SHARE, "EMAIL");
                }
                break;
        }

        editor.commit();
    }
}
