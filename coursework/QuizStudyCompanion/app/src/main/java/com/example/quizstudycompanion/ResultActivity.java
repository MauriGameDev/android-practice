
package com.example.quizstudycompanion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Receive quiz results from QuizActivity
        Intent intent = getIntent();

        String topic = intent.getStringExtra("topic");
        int score = intent.getIntExtra("score", 0);
        int total = intent.getIntExtra("total", 0);

        // Default topic if none was provided
        if (topic == null) {
            topic = getString(R.string.topic_python);
        }

        // Connect the XML TextViews
        TextView heading = findViewById(R.id.resultHeading);
        TextView scoreView = findViewById(R.id.resultScore);

        // Display the quiz topic and final score
        heading.setText(
                getString(R.string.result_heading, topic)
        );

        scoreView.setText(
                getString(R.string.result_score, score, total)
        );

        // Return to the Main Screen
        findViewById(R.id.returnHomeButton)
                .setOnClickListener(view -> {

                    Intent home = new Intent(
                            ResultActivity.this,
                            MainActivity.class
                    );

                    home.addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_SINGLE_TOP
                    );

                    startActivity(home);
                    finish();
                });
    }
}
