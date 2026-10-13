package com.example.capitalquiz;

public class Question {

    // Stores the resource ID for the question text
    int textResId;

    // Stores the correct answer
    // true = True
    // false = False
    boolean answer;

    // Constructor used when creating a new Question object
    public Question(int textResId, boolean answer) {

        // Save the question's text resource ID
        this.textResId = textResId;

        // Save the correct answer
        this.answer = answer;
    }
}