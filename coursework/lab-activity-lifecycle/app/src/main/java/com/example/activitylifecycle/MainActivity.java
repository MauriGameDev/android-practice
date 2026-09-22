package com.example.activitylifecycle;

import android.os.Bundle;
import android.util.Log;

// Step 21:
// Import EditText so MainActivity can access the text field.
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Step 2:
    // Tag used to identify lifecycle messages in Logcat.
    private static final String tag = "StateChangeEvent";


    // Step 3:
    // Called when the Activity is first created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        Log.d(tag, "In the onCreate() event");
    }


    // Step 4:
    // Activity is becoming visible.
    @Override
    protected void onStart() {
        super.onStart();

        Log.d(tag, "In the onStart() event");
    }


    // Step 5:
    // Activity is in the foreground and ready for interaction.
    @Override
    protected void onResume() {
        super.onResume();

        Log.d(tag, "In the onResume() event");
    }


    // Step 6:
    // Activity is beginning to leave the foreground.
    @Override
    protected void onPause() {
        super.onPause();

        Log.d(tag, "In the onPause() event");
    }


    // Step 6:
    // Activity is no longer visible.
    @Override
    protected void onStop() {
        super.onStop();

        Log.d(tag, "In the onStop() event");
    }


    // Step 12:
    // Activity was stopped and is now returning.
    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(tag, "In the onRestart() event");
    }


    // Step 17:
    // Android calls this method when Activity state
    // needs to be saved.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(
                tag,
                "In the onSaveInstanceState() event"
        );


        // Step 22:
        // Find the EditText using the ID from activity_main.xml.
        final EditText editText =
                findViewById(R.id.editTextText);


        // Step 22:
        // Get whatever text the user typed.
        CharSequence userText =
                editText.getEditableText();


        // Step 22:
        // Save the user's text inside the Bundle.
        //
        // "savedText" is the key.
        // userText is the value.
        outState.putCharSequence(
                "savedText",
                userText
        );
    }


    // Step 17:
    // Android calls this method when previously saved
    // Activity state is available to restore.
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        Log.d(
                tag,
                "In the onRestoreInstanceState() event"
        );


        // Step 23:
        // Find the EditText again after the Activity
        // has been recreated.
        final EditText editText =
                findViewById(R.id.editTextText);


        // Step 23:
        // Retrieve the text saved in Step 22.
        //
        // We must use the same key: "savedText".
        CharSequence userText =
                savedInstanceState.getCharSequence(
                        "savedText"
                );


        // Step 23:
        // Put the saved text back into the EditText.
        editText.setText(userText);
    }


    // Step 6:
    // Called before the Activity is destroyed.
    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(tag, "In the onDestroy() event");
    }
}