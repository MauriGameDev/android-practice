
package com.example.quizstudycompanion;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizstudycompanion.model.StudyTopic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudyDetailFragment extends Fragment {

    private static final String TAG = "StudyDetailLifecycle";

    // Bundle argument keys
    private static final String ARG_TOPIC_NAME = "topicName";
    private static final String ARG_FILE_NAME = "fileName";

    // NEW: Keep Loading... visible for at least one second
    private static final long LOADING_DELAY_MS = 1000;

    // Background thread executor
    private ExecutorService executor;

    // Handler for updating the UI on the main thread
    private final Handler handler =
            new Handler(Looper.getMainLooper());

    // XML views
    private TextView topicHeading;
    private TextView loadingText;
    private TextView materialText;

    // Required empty constructor
    public StudyDetailFragment() {
    }

    // Create a Fragment and pass the selected study topic
    public static StudyDetailFragment newInstance(
            StudyTopic topic) {

        StudyDetailFragment fragment =
                new StudyDetailFragment();

        Bundle args = new Bundle();

        args.putString(
                ARG_TOPIC_NAME,
                topic.getTopicName()
        );

        args.putString(
                ARG_FILE_NAME,
                topic.getFileName()
        );

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");

        return inflater.inflate(
                R.layout.fragment_study_detail,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");

        // Connect Java to XML
        topicHeading = view.findViewById(
                R.id.detailTopicHeading
        );

        loadingText = view.findViewById(
                R.id.detailLoadingText
        );

        materialText = view.findViewById(
                R.id.detailMaterialText
        );

        // Create the background executor
        executor = Executors.newSingleThreadExecutor();

        // Retrieve the selected topic from the Bundle
        Bundle args = getArguments();

        String topicName = "";
        String fileName = "";

        if (args != null) {
            topicName = args.getString(
                    ARG_TOPIC_NAME, ""
            );

            fileName = args.getString(
                    ARG_FILE_NAME, ""
            );
        }

        // Display the study topic name
        topicHeading.setText(topicName);

        // Show Loading... before reading the file
        loadingText.setText(R.string.fragment_loading);
        loadingText.setVisibility(View.VISIBLE);

        // Hide the study material until it finishes loading
        materialText.setText("");
        materialText.setVisibility(View.GONE);

        // Start loading the selected study guide
        loadStudyMaterial(view, fileName);
    }

    // Load study material using a background thread
    private void loadStudyMaterial(
            View currentView,
            String fileName) {

        // Capture the application context
        Context appContext =
                requireContext().getApplicationContext();

        executor.execute(() -> {

            // Read the study guide on a background thread
            String studyMaterial =
                    readAsset(appContext, fileName);

            // NEW: Add a one-second delay
            // This runs on the background thread,
            // NOT the main UI thread.
            try {

                Thread.sleep(LOADING_DELAY_MS);

            } catch (InterruptedException e) {

                // Stop if the Fragment was closed
                Thread.currentThread().interrupt();
                return;
            }

            // Return to the main UI thread
            handler.post(() -> {

                // Check that the Fragment is still active
                if (!isAdded()
                        || getView() != currentView
                        || loadingText == null
                        || materialText == null) {

                    return;
                }

                // Display the study material
                materialText.setText(studyMaterial);
                materialText.setVisibility(View.VISIBLE);

                // Hide the Loading... message
                loadingText.setVisibility(View.GONE);

            });
        });
    }

    // Read the selected .txt file from assets
    private String readAsset(
            Context context,
            String fileName) {

        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        context.getAssets().open(fileName),
                        StandardCharsets.UTF_8
                ))) {

            String line;

            while ((line = reader.readLine()) != null) {

                content.append(line);
                content.append("\n");
            }

        } catch (IOException e) {

            Log.e(
                    TAG,
                    "Error loading study material",
                    e
            );

            return context.getString(
                    R.string.fragment_load_error
            );
        }

        return content.toString();
    }

    // Fragment lifecycle methods

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {

        Log.d(TAG, "onDestroyView");

        // Stop background work when the view is destroyed
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }

        // Release references to the old views
        topicHeading = null;
        loadingText = null;
        materialText = null;

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
