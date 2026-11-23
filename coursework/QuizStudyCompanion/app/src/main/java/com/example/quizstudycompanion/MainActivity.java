
package com.example.quizstudycompanion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Used to identify lifecycle messages in Logcat
    private static final String TAG = "MainActivityLifecycle";

    // Button declarations
    private Button buttonStartQuiz;
    private Button buttonStudy;
    private Button buttonHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect Java to the Main Screen XML
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate called");

        // Connect buttons to XML IDs
        buttonStartQuiz = findViewById(R.id.buttonStartQuiz);
        buttonStudy = findViewById(R.id.buttonStudy);
        buttonHistory = findViewById(R.id.buttonHistory);

        // Open Quiz Activity
        buttonStartQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    QuizActivity.class
            );
            startActivity(intent);
        });

        // Open Study Activity
        buttonStudy.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    StudyActivity.class
            );
            startActivity(intent);
        });

        // Open History Activity
        buttonHistory.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    HistoryActivity.class
            );
            startActivity(intent);
        });
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
