
package com.example.fragmentcommunication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Step 7.1: Implement ToolbarListener interface
public class MainActivity extends AppCompatActivity
        implements ToolbarFragment.ToolbarListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Load the main activity layout
        setContentView(R.layout.activity_main);

        // Apply padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                });
    }

    // Step 7.2 and Step 9: Handle communication
    // Receives font size and text from ToolbarFragment
    @Override
    public void onButtonClick(int fontSize, String text) {

        // Find TextFragment using its container ID
        TextFragment textFragment = (TextFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragmentContainerView2);

        // Check if TextFragment exists
        if (textFragment != null) {

            // Send font size and text to TextFragment
            textFragment.changeTextProperties(fontSize, text);

        }
    }
}
