
package com.example.quizstudycompanion.model;

public class QuizManager {

    // Store the current score
    private int score;

    // Track whether the current answer was submitted
    private boolean answerSubmitted;

    // Constructor
    public QuizManager() {
        score = 0;
        answerSubmitted = false;
    }

    // Check an answer and prevent double-counting
    public boolean submitAnswer(Question question, int selectedIndex) {

        // Ignore invalid or repeated submissions
        if (question == null || selectedIndex < 0 || answerSubmitted) {
            return false;
        }

        answerSubmitted = true;

        // Compare the selected answer with the correct answer
        boolean isCorrect =
                selectedIndex == question.getCorrectAnswerIndex();

        // Increase the score only for a correct answer
        if (isCorrect) {
            score++;
        }

        return isCorrect;
    }

    // Reset submission status for the next question
    public void resetForNextQuestion() {
        answerSubmitted = false;
    }

    // Return the current score
    public int getScore() {
        return score;
    }

    // Check whether an answer has already been submitted
    public boolean hasSubmittedAnswer() {
        return answerSubmitted;
    }

    // Restore quiz scoring state after screen rotation
    public void restoreState(int savedScore, boolean savedSubmitted) {
        score = savedScore;
        answerSubmitted = savedSubmitted;
    }
}
