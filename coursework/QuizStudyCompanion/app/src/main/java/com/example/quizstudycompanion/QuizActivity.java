package com.example.quizstudycompanion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizLifecycle";
    private static final String KEY_INDEX = "currentIndex";
    private static final String KEY_SCORE = "score";
    private static final String KEY_SUBMITTED = "answerSubmitted";
    private static final String KEY_SELECTION = "selectedAnswer";

    // Milestone 1 uses simple Java data structures; MVC comes in Milestone 2.
    private static class Question {
        final int textId;
        final int choicesId;
        final int answerIndex;
        final int difficultyId;

        Question(int textId, int choicesId, int answerIndex, int difficultyId) {
            this.textId = textId;
            this.choicesId = choicesId;
            this.answerIndex = answerIndex;
            this.difficultyId = difficultyId;
        }
    }

    private final Question[] questions = {
            new Question(R.string.python_q1, R.array.python_q1_options, 1, R.string.beginner),
            new Question(R.string.python_q2, R.array.python_q2_options, 2, R.string.beginner),
            new Question(R.string.python_q3, R.array.python_q3_options, 1, R.string.intermediate),
            new Question(R.string.python_q4, R.array.python_q4_options, 1, R.string.intermediate),
            new Question(R.string.python_q5, R.array.python_q5_options, 2, R.string.advanced)
    };

    private int currentIndex = 0;
    private int score = 0;
    private int selectedAnswer = -1;
    private boolean answerSubmitted = false;

    private TextView progressText;
    private TextView difficultyText;
    private TextView scoreText;
    private TextView questionText;
    private TextView feedbackText;
    private RadioGroup answerGroup;
    private RadioButton[] optionButtons;
    private Button submitButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "onCreate");

        progressText = findViewById(R.id.progressText);
        difficultyText = findViewById(R.id.difficultyText);
        scoreText = findViewById(R.id.scoreText);
        questionText = findViewById(R.id.questionText);
        feedbackText = findViewById(R.id.feedbackText);
        answerGroup = findViewById(R.id.answerGroup);
        optionButtons = new RadioButton[] {
                findViewById(R.id.optionOne),
                findViewById(R.id.optionTwo),
                findViewById(R.id.optionThree),
                findViewById(R.id.optionFour)
        };
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            score = savedInstanceState.getInt(KEY_SCORE, 0);
            answerSubmitted = savedInstanceState.getBoolean(KEY_SUBMITTED, false);
            selectedAnswer = savedInstanceState.getInt(KEY_SELECTION, -1);
        }

        submitButton.setOnClickListener(view -> submitAnswer());
        nextButton.setOnClickListener(view -> nextQuestion());
        showQuestion();
    }

    private void showQuestion() {
        Question question = questions[currentIndex];
        String[] options = getResources().getStringArray(question.choicesId);

        progressText.setText(getString(R.string.question_progress, currentIndex + 1, questions.length));
        difficultyText.setText(getString(R.string.difficulty_format, getString(question.difficultyId)));
        scoreText.setText(getString(R.string.score_status, score));
        questionText.setText(question.textId);

        answerGroup.clearCheck();
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setEnabled(!answerSubmitted);
        }
        if (selectedAnswer >= 0 && selectedAnswer < optionButtons.length) {
            answerGroup.check(optionButtons[selectedAnswer].getId());
        }

        submitButton.setEnabled(!answerSubmitted);
        nextButton.setVisibility(answerSubmitted ? View.VISIBLE : View.GONE);
        nextButton.setText(currentIndex == questions.length - 1
                ? R.string.finish_quiz : R.string.next_question);

        if (answerSubmitted) {
            String feedback = selectedAnswer == question.answerIndex
                    ? getString(R.string.correct_feedback)
                    : getString(R.string.incorrect_feedback, options[question.answerIndex]);
            feedbackText.setText(feedback);
            feedbackText.setVisibility(View.VISIBLE);
        } else {
            feedbackText.setVisibility(View.GONE);
        }
    }

    private void submitAnswer() {
        if (answerSubmitted) return; // No double-counting on rapid taps.

        int checkedId = answerGroup.getCheckedRadioButtonId();
        if (checkedId == -1) {
            Toast.makeText(this, R.string.choose_answer, Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].getId() == checkedId) {
                selectedAnswer = i;
                break;
            }
        }
        if (selectedAnswer == -1) return;

        answerSubmitted = true;
        if (selectedAnswer == questions[currentIndex].answerIndex) score++;
        showQuestion();
    }

    private void nextQuestion() {
        if (!answerSubmitted) return;
        nextButton.setEnabled(false); // No double-advance on rapid taps.

        if (currentIndex == questions.length - 1) {
            finishQuiz();
        } else {
            currentIndex++;
            selectedAnswer = -1;
            answerSubmitted = false;
            nextButton.setEnabled(true);
            showQuestion();
        }
    }

    private void finishQuiz() {
        // Transfer the result with an explicit Intent and extras.
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("topic", getString(R.string.topic_python));
        intent.putExtra("score", score);
        intent.putExtra("total", questions.length);
        startActivity(intent);
        finish(); // Back from Result returns to Main.
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Preserve a selected answer even if it has not been submitted yet.
        if (!answerSubmitted) {
            int checkedId = answerGroup.getCheckedRadioButtonId();
            selectedAnswer = -1;
            for (int i = 0; i < optionButtons.length; i++) {
                if (optionButtons[i].getId() == checkedId) {
                    selectedAnswer = i;
                    break;
                }
            }
        }
        outState.putInt(KEY_INDEX, currentIndex);
        outState.putInt(KEY_SCORE, score);
        outState.putBoolean(KEY_SUBMITTED, answerSubmitted);
        outState.putInt(KEY_SELECTION, selectedAnswer);
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override protected void onStart() { super.onStart(); Log.d(TAG, "onStart"); }
    @Override protected void onResume() { super.onResume(); Log.d(TAG, "onResume"); }
    @Override protected void onPause() { super.onPause(); Log.d(TAG, "onPause"); }
    @Override protected void onStop() { super.onStop(); Log.d(TAG, "onStop"); }
    @Override protected void onRestart() { super.onRestart(); Log.d(TAG, "onRestart"); }
    @Override protected void onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy"); }
}