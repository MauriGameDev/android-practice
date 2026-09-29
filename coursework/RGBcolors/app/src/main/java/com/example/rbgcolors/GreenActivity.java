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

public class GreenActivity extends AppCompatActivity {

    // Step 17:
    // Declare the Blue button.
    private Button btn_blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        // Connect this Activity to activity_green.xml.
        setContentView(R.layout.activity_green);


        // Step 17:
        // Connect the Java variable to the Blue button
        // in activity_green.xml.
        btn_blue = findViewById(R.id.btn_blue);


        // Step 17:
        // Listen for the Blue button to be clicked.
        btn_blue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Create an Intent that points to BlueActivity.
                Intent redirect =
                        new Intent(view.getContext(), BlueActivity.class);

                // Launch BlueActivity.
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