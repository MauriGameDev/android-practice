package com.example.rbgcolors;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Step 11:
    // Declare the Green button.
    private Button btn_green;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        // Connect this Activity to activity_main.xml.
        setContentView(R.layout.activity_main);


        // Step 11:
        // Connect the Java variable to the Green button
        // in activity_main.xml.
        btn_green = findViewById(R.id.btn_green);


        // Step 11:
        // Listen for the Green button to be clicked.
        btn_green.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Create an Intent that points to GreenActivity.
                Intent redirect =
                        new Intent(view.getContext(), GreenActivity.class);

                // Launch GreenActivity.
                startActivity(redirect);
            }
        });


        // Android Studio generated Edge-to-Edge code.
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
                });
    }
}