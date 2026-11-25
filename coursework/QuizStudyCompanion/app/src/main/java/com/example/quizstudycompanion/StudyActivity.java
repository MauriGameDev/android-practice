
package com.example.quizstudycompanion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the Activity layout containing the Fragment container
        setContentView(R.layout.activity_study);

        // Only add the starting Fragment when first opening the screen
        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.fragmentContainer,
                            new StudyTopicsFragment()
                    )
                    .commit();
        }

        // Android restores the existing Fragments after rotation
    }
}
