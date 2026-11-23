
package com.example.quizstudycompanion;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class StudyActivity extends AppCompatActivity {

    // Key used to preserve selected study topic
    private static final String KEY_TOPIC = "selectedTopic";

    // View declarations
    private RadioGroup topicGroup;
    private TextView topicHeading;
    private TextView topicMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect Java to the Study XML layout
        setContentView(R.layout.activity_study);

        // Connect XML views
        topicGroup = findViewById(R.id.topicGroup);
        topicHeading = findViewById(R.id.studyTopicHeading);
        topicMaterial = findViewById(R.id.studyMaterial);

        // Detect when the user selects a topic
        topicGroup.setOnCheckedChangeListener(
                (group, checkedId) -> showTopic(checkedId)
        );

        // Restore the selected topic after rotation
        int topicId = savedInstanceState == null
                ? R.id.pythonTopic
                : savedInstanceState.getInt(
                KEY_TOPIC,
                R.id.pythonTopic
        );

        topicGroup.check(topicId);
        showTopic(topicId);
    }

    // Display study material based on topic selection
    private void showTopic(int topicId) {

        if (topicId == R.id.cybersecurityTopic) {

            // Cybersecurity study material
            topicHeading.setText(
                    R.string.study_cybersecurity_title
            );

            topicMaterial.setText(
                    R.string.study_cybersecurity_material
            );

        } else if (topicId == R.id.csharpTopic) {

            // C# study material
            topicHeading.setText(
                    R.string.study_csharp_title
            );

            topicMaterial.setText(
                    R.string.study_csharp_material
            );

        } else {

            // Python study material
            topicHeading.setText(
                    R.string.study_python_title
            );

            topicMaterial.setText(
                    R.string.study_python_material
            );
        }
    }

    // Preserve selected topic during screen rotation
    @Override
    protected void onSaveInstanceState(
            @NonNull Bundle outState) {

        outState.putInt(
                KEY_TOPIC,
                topicGroup.getCheckedRadioButtonId()
        );

        super.onSaveInstanceState(outState);
    }
}
