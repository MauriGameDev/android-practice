
package com.example.quizstudycompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizstudycompanion.model.StudyTopic;

public class StudyTopicsFragment extends Fragment {

    // SharedPreferences settings
    public static final String PREFS_NAME = "quiz_preferences";
    public static final String KEY_SELECTED_TOPIC = "selected_topic";

    // Required empty constructor
    public StudyTopicsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        // Connect Fragment to its XML layout
        return inflater.inflate(
                R.layout.fragment_study_topics,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Python button
        view.findViewById(R.id.buttonPythonTopic)
                .setOnClickListener(v -> openTopic(
                        new StudyTopic(
                                getString(R.string.topic_python),
                                "python.txt"
                        ),
                        QuizActivity.TOPIC_PYTHON
                ));

        // Cybersecurity button
        view.findViewById(R.id.buttonCybersecurityTopic)
                .setOnClickListener(v -> openTopic(
                        new StudyTopic(
                                getString(R.string.topic_cybersecurity),
                                "cybersecurity.txt"
                        ),
                        QuizActivity.TOPIC_CYBERSECURITY
                ));

        // C# button
        view.findViewById(R.id.buttonCsharpTopic)
                .setOnClickListener(v -> openTopic(
                        new StudyTopic(
                                getString(R.string.topic_csharp),
                                "csharp.txt"
                        ),
                        QuizActivity.TOPIC_CSHARP
                ));
    }

    // Save the topic and open its study material
    private void openTopic(StudyTopic topic, String topicKey) {

        if (!isAdded()) {
            return;
        }

        // Save the selected topic for MainActivity
        SharedPreferences preferences =
                requireContext().getSharedPreferences(
                        PREFS_NAME,
                        Context.MODE_PRIVATE
                );

        preferences.edit()
                .putString(KEY_SELECTED_TOPIC, topicKey)
                .apply();

        // Create the Detail Fragment
        StudyDetailFragment detail =
                StudyDetailFragment.newInstance(topic);

        // Open study details and support Back navigation
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, detail)
                .addToBackStack(null)
                .commit();
    }
}
