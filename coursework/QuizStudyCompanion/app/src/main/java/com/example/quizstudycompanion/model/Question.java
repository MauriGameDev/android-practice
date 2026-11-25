
package com.example.quizstudycompanion.model;

public class Question {

    // Variables to store question information
    private final String questionText;
    private final String[] options;
    private final int correctAnswerIndex;
    private final String difficulty;

    // Constructor
    public Question(String questionText, String[] options,
                    int correctAnswerIndex, String difficulty) {

        this.questionText = questionText;
        this.options = options.clone();
        this.correctAnswerIndex = correctAnswerIndex;
        this.difficulty = difficulty;
    }

    // Return the question text
    public String getQuestionText() {
        return questionText;
    }

    // Return the answer choices
    public String[] getOptions() {
        return options.clone();
    }

    // Return the correct answer index
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    // Return the difficulty level
    public String getDifficulty() {
        return difficulty;
    }
}
