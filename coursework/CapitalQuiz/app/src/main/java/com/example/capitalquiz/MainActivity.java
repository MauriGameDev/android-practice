package com.example.capitalquiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Buttons used by the quiz
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;

    // TextView that displays the current question
    private TextView questionText;

    // Stores the string resource ID of the current question
    int questionResId;

    // Array containing all quiz questions and their correct answers
    private Question[] questions = {
            new Question(R.string.ques_il, true),
            new Question(R.string.ques_ca, false),
            new Question(R.string.ques_ny, false),
            new Question(R.string.ques_tx, false),
            new Question(R.string.ques_wa, true)
    };

    // Keeps track of which question is currently displayed
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Allows the app to use the full screen area
        EdgeToEdge.enable(this);

        // Connects MainActivity to activity_main.xml
        setContentView(R.layout.activity_main);

        // Connect the Java button variables to the XML buttons
        trueButton = findViewById(R.id.button);
        falseButton = findViewById(R.id.button2);
        nextButton = findViewById(R.id.button3);

        // Connect the Java TextView variable to the XML TextView
        questionText = findViewById(R.id.textView);

        // Display the first question when the app starts
        updateQuestion();

        // Adds padding so content does not overlap system bars
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // Runs when the True button is clicked
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Sends True as the user's answer
                checkAnswer(true);
            }
        });

        // Runs when the False button is clicked
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Sends False as the user's answer
                checkAnswer(false);
            }
        });

        // Runs when the Next button is clicked
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Move to the next question
                // % causes the quiz to return to question 0 after the last question
                currentIndex = (currentIndex + 1) % questions.length;

                // Display the new question
                updateQuestion();
            }
        });
    }

    // Displays the question at the current array position
    private void updateQuestion() {

        // Get the string resource ID from the current Question object
        questionResId = questions[currentIndex].textResId;

        // Display that question in the TextView
        questionText.setText(questionResId);
    }

    // Checks whether the user's answer matches the correct answer
    private void checkAnswer(boolean userAnswer) {

        // Get the correct answer from the current Question object
        boolean correctAnswer = questions[currentIndex].answer;

        // Choose which message should be displayed
        int messageResId =
                userAnswer == correctAnswer
                        ? R.string.correct
                        : R.string.incorrect;

        // Display Correct or Incorrect
        Toast.makeText(
                getApplicationContext(),
                messageResId,
                Toast.LENGTH_SHORT
        ).show();
    }
}