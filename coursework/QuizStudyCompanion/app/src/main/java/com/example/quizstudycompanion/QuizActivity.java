
package com.example.quizstudycompanion;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizstudycompanion.model.Question;
import com.example.quizstudycompanion.model.QuizManager;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizLifecycle";

    // Intent extra used to identify the quiz topic
    public static final String EXTRA_QUIZ_TOPIC = "quiz_topic";

    // Supported quiz topics
    public static final String TOPIC_PYTHON = "python";
    public static final String TOPIC_CYBERSECURITY = "cybersecurity";
    public static final String TOPIC_CSHARP = "csharp";

    // Keys for saving quiz progress
    private static final String KEY_INDEX = "currentIndex";
    private static final String KEY_SCORE = "score";
    private static final String KEY_SUBMITTED = "answerSubmitted";
    private static final String KEY_SELECTION = "selectedAnswer";

    // Prevent rapid Next button presses
    private static final long NEXT_CLICK_GUARD_MS = 600;

    // MVC Model objects
    private Question[] questions;
    private QuizManager quizManager;

    // Current quiz information
    private String selectedTopic;
    private String topicDisplayName;

    private int currentIndex = 0;
    private int selectedAnswer = -1;
    private long lastNextClickTime = 0;

    // XML views
    private TextView quizTitle;
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

        // Create the scoring Model
        quizManager = new QuizManager();

        // Get the selected topic from MainActivity
        selectedTopic = getIntent().getStringExtra(EXTRA_QUIZ_TOPIC);

        // Default to Python if no valid topic was provided
        if (!TOPIC_CYBERSECURITY.equals(selectedTopic)
                && !TOPIC_CSHARP.equals(selectedTopic)) {

            selectedTopic = TOPIC_PYTHON;
        }

        // Load questions for the selected subject
        loadQuestions();

        // Connect Java to XML views
        quizTitle = findViewById(R.id.quizTitle);
        progressText = findViewById(R.id.progressText);
        difficultyText = findViewById(R.id.difficultyText);
        scoreText = findViewById(R.id.scoreText);
        questionText = findViewById(R.id.questionText);
        feedbackText = findViewById(R.id.feedbackText);

        answerGroup = findViewById(R.id.answerGroup);

        optionButtons = new RadioButton[]{
                findViewById(R.id.optionOne),
                findViewById(R.id.optionTwo),
                findViewById(R.id.optionThree),
                findViewById(R.id.optionFour)
        };

        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

        // NEW: Display the correct quiz heading
        updateQuizTitle();

        // Restore quiz state after screen rotation
        if (savedInstanceState != null) {

            currentIndex = savedInstanceState.getInt(
                    KEY_INDEX, 0
            );

            int savedScore = savedInstanceState.getInt(
                    KEY_SCORE, 0
            );

            boolean savedSubmitted = savedInstanceState.getBoolean(
                    KEY_SUBMITTED, false
            );

            selectedAnswer = savedInstanceState.getInt(
                    KEY_SELECTION, -1
            );

            quizManager.restoreState(
                    savedScore,
                    savedSubmitted
            );
        }

        // Button click events
        submitButton.setOnClickListener(v -> submitAnswer());
        nextButton.setOnClickListener(v -> nextQuestion());

        showQuestion();
    }

    // NEW: Update the quiz title based on the selected subject
    private void updateQuizTitle() {

        switch (selectedTopic) {

            case TOPIC_CYBERSECURITY:
                quizTitle.setText(
                        R.string.cybersecurity_quiz_title
                );
                break;

            case TOPIC_CSHARP:
                quizTitle.setText(
                        R.string.csharp_quiz_title
                );
                break;

            case TOPIC_PYTHON:
            default:
                quizTitle.setText(
                        R.string.quiz_title
                );
                break;
        }
    }

    // Create a Question Model using XML resources
    private Question createQuestion(
            int textId,
            int optionsId,
            int correctIndex,
            int difficultyId) {

        return new Question(
                getString(textId),
                getResources().getStringArray(optionsId),
                correctIndex,
                getString(difficultyId)
        );
    }

    // Load the five questions for the selected topic
    private void loadQuestions() {

        switch (selectedTopic) {

            case TOPIC_CYBERSECURITY:

                topicDisplayName = getString(
                        R.string.topic_cybersecurity
                );

                questions = new Question[]{
                        createQuestion(
                                R.string.cyber_q1,
                                R.array.cyber_q1_options,
                                1,
                                R.string.beginner
                        ),
                        createQuestion(
                                R.string.cyber_q2,
                                R.array.cyber_q2_options,
                                2,
                                R.string.beginner
                        ),
                        createQuestion(
                                R.string.cyber_q3,
                                R.array.cyber_q3_options,
                                0,
                                R.string.intermediate
                        ),
                        createQuestion(
                                R.string.cyber_q4,
                                R.array.cyber_q4_options,
                                1,
                                R.string.intermediate
                        ),
                        createQuestion(
                                R.string.cyber_q5,
                                R.array.cyber_q5_options,
                                2,
                                R.string.advanced
                        )
                };
                break;

            case TOPIC_CSHARP:

                topicDisplayName = getString(
                        R.string.topic_csharp
                );

                questions = new Question[]{
                        createQuestion(
                                R.string.csharp_q1,
                                R.array.csharp_q1_options,
                                2,
                                R.string.beginner
                        ),
                        createQuestion(
                                R.string.csharp_q2,
                                R.array.csharp_q2_options,
                                0,
                                R.string.beginner
                        ),
                        createQuestion(
                                R.string.csharp_q3,
                                R.array.csharp_q3_options,
                                2,
                                R.string.intermediate
                        ),
                        createQuestion(
                                R.string.csharp_q4,
                                R.array.csharp_q4_options,
                                1,
                                R.string.intermediate
                        ),
                        createQuestion(
                                R.string.csharp_q5,
                                R.array.csharp_q5_options,
                                3,
                                R.string.advanced
                        )
                };
                break;

            case TOPIC_PYTHON:
            default:

                topicDisplayName = getString(
                        R.string.topic_python
                );

                questions = new Question[]{
                        createQuestion(
                                R.string.python_q1,
                                R.array.python_q1_options,
                                1,
                                R.string.beginner
                        ),
                        createQuestion(
                                R.string.python_q2,
                                R.array.python_q2_options,
                                2,
                                R.string.beginner
                        ),
                        createQuestion(
                                R.string.python_q3,
                                R.array.python_q3_options,
                                1,
                                R.string.intermediate
                        ),
                        createQuestion(
                                R.string.python_q4,
                                R.array.python_q4_options,
                                1,
                                R.string.intermediate
                        ),
                        createQuestion(
                                R.string.python_q5,
                                R.array.python_q5_options,
                                2,
                                R.string.advanced
                        )
                };
                break;
        }
    }

    // Display the current question
    private void showQuestion() {

        Question question = questions[currentIndex];
        String[] options = question.getOptions();

        boolean submitted = quizManager.hasSubmittedAnswer();

        // Display question number
        progressText.setText(
                getString(
                        R.string.question_progress,
                        currentIndex + 1,
                        questions.length
                )
        );

        // Display difficulty
        difficultyText.setText(
                getString(
                        R.string.difficulty_format,
                        question.getDifficulty()
                )
        );

        // Display current score
        scoreText.setText(
                getString(
                        R.string.score_status,
                        quizManager.getScore()
                )
        );

        // Display question text
        questionText.setText(question.getQuestionText());

        // Display the answer choices
        answerGroup.clearCheck();

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setEnabled(!submitted);
        }

        // Restore the selected answer
        if (selectedAnswer >= 0
                && selectedAnswer < optionButtons.length) {

            answerGroup.check(
                    optionButtons[selectedAnswer].getId()
            );
        }

        // Update button states
        submitButton.setEnabled(!submitted);

        nextButton.setVisibility(
                submitted ? View.VISIBLE : View.GONE
        );

        nextButton.setEnabled(submitted);

        nextButton.setText(
                currentIndex == questions.length - 1
                        ? R.string.finish_quiz
                        : R.string.next_question
        );

        // Display answer feedback
        if (submitted) {

            boolean correct =
                    selectedAnswer == question.getCorrectAnswerIndex();

            String feedback = correct
                    ? getString(R.string.correct_feedback)
                    : getString(
                    R.string.incorrect_feedback,
                    options[question.getCorrectAnswerIndex()]
            );

            feedbackText.setText(feedback);
            feedbackText.setVisibility(View.VISIBLE);

        } else {

            feedbackText.setText("");
            feedbackText.setVisibility(View.GONE);
        }
    }

    // Submit an answer using QuizManager
    private void submitAnswer() {

        // Prevent submitting the same answer twice
        if (quizManager.hasSubmittedAnswer()) {
            return;
        }

        int checkedId = answerGroup.getCheckedRadioButtonId();

        // Require an answer selection
        if (checkedId == -1) {

            Toast.makeText(
                    this,
                    R.string.choose_answer,
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        selectedAnswer = -1;

        // Identify the selected option
        for (int i = 0; i < optionButtons.length; i++) {

            if (optionButtons[i].getId() == checkedId) {
                selectedAnswer = i;
                break;
            }
        }

        if (selectedAnswer == -1) {
            return;
        }

        // Let the Model handle scoring
        quizManager.submitAnswer(
                questions[currentIndex],
                selectedAnswer
        );

        // Update the UI
        showQuestion();
    }

    // Advance to the next question or finish the quiz
    private void nextQuestion() {

        if (!quizManager.hasSubmittedAnswer()) {
            return;
        }

        // Prevent rapid repeated Next presses
        long currentTime = SystemClock.elapsedRealtime();

        if (lastNextClickTime != 0
                && currentTime - lastNextClickTime
                < NEXT_CLICK_GUARD_MS) {
            return;
        }

        lastNextClickTime = currentTime;

        nextButton.setEnabled(false);

        if (currentIndex == questions.length - 1) {

            finishQuiz();

        } else {

            currentIndex++;
            selectedAnswer = -1;

            // Reset Model submission state
            quizManager.resetForNextQuestion();

            showQuestion();
        }
    }

    // Send final results to ResultActivity
    private void finishQuiz() {

        Intent intent = new Intent(
                this,
                ResultActivity.class
        );

        // Pass the selected topic, score, and total
        intent.putExtra("topic", topicDisplayName);

        intent.putExtra(
                "score",
                quizManager.getScore()
        );

        intent.putExtra("total", questions.length);

        startActivity(intent);
        finish();
    }

    // Save progress during screen rotation
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        // Save selected answer if not submitted
        if (!quizManager.hasSubmittedAnswer()) {

            int checkedId =
                    answerGroup.getCheckedRadioButtonId();

            selectedAnswer = -1;

            for (int i = 0; i < optionButtons.length; i++) {

                if (optionButtons[i].getId() == checkedId) {
                    selectedAnswer = i;
                    break;
                }
            }
        }

        // Save question number and scoring state
        outState.putInt(KEY_INDEX, currentIndex);

        outState.putInt(
                KEY_SCORE,
                quizManager.getScore()
        );

        outState.putBoolean(
                KEY_SUBMITTED,
                quizManager.hasSubmittedAnswer()
        );

        outState.putInt(KEY_SELECTION, selectedAnswer);

        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState");
    }

    // Activity lifecycle logging
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
