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

public class BlueActivity extends AppCompatActivity {

    // Step 22:
    // Declare the Red button.
    private Button btn_red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        // Connect this Activity to activity_blue.xml.
        setContentView(R.layout.activity_blue);


        // Step 22:
        // Connect the Java variable to the Red button
        // in activity_blue.xml.
        btn_red = findViewById(R.id.btn_red);


        // Step 22:
        // Listen for the Red button to be clicked.
        btn_red.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Create an Intent that points back to MainActivity.
                Intent redirect =
                        new Intent(view.getContext(), MainActivity.class);

                // Launch MainActivity.
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