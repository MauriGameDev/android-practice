
package com.example.quizstudycompanion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityLifecycle";

    // Main Screen quiz button
    private Button startQuizButton;

    // Current selected quiz topic
    private String selectedTopic = QuizActivity.TOPIC_PYTHON;

    // Save the original Python button text
    private CharSequence pythonButtonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect Java to the Main Screen layout
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate called");

        // Connect the XML buttons
        startQuizButton = findViewById(R.id.buttonStartQuiz);

        Button studyButton = findViewById(R.id.buttonStudy);
        Button historyButton = findViewById(R.id.buttonHistory);

        // Remember the default Python button label
        pythonButtonText = startQuizButton.getText();

        // Start the selected quiz
        startQuizButton.setOnClickListener(view -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    QuizActivity.class
            );

            // Send the selected topic to QuizActivity
            intent.putExtra(
                    QuizActivity.EXTRA_QUIZ_TOPIC,
                    selectedTopic
            );

            startActivity(intent);
        });

        // Open Study Topics
        studyButton.setOnClickListener(view -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    StudyActivity.class
            );

            startActivity(intent);
        });

        // Open Quiz History
        historyButton.setOnClickListener(view -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    HistoryActivity.class
            );

            startActivity(intent);
        });
    }

    // Read the selected topic and update the button
    private void updateQuizButton() {

        SharedPreferences preferences = getSharedPreferences(
                StudyTopicsFragment.PREFS_NAME,
                MODE_PRIVATE
        );

        // Get the topic selected in StudyTopicsFragment
        String savedTopic = preferences.getString(
                StudyTopicsFragment.KEY_SELECTED_TOPIC,
                QuizActivity.TOPIC_PYTHON
        );

        if (QuizActivity.TOPIC_CYBERSECURITY.equals(savedTopic)) {

            selectedTopic = QuizActivity.TOPIC_CYBERSECURITY;

            startQuizButton.setText(
                    R.string.start_cybersecurity_quiz
            );

        } else if (QuizActivity.TOPIC_CSHARP.equals(savedTopic)) {

            selectedTopic = QuizActivity.TOPIC_CSHARP;

            startQuizButton.setText(
                    R.string.start_csharp_quiz
            );

        } else {

            // Default to Python
            selectedTopic = QuizActivity.TOPIC_PYTHON;

            startQuizButton.setText(pythonButtonText);
        }
    }

    // Activity lifecycle methods

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume called");

        // Refresh the quiz button whenever Main Screen resumes
        updateQuizButton();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }
}
