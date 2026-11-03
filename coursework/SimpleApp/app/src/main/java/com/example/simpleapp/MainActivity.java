
package com.example.simpleapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the activity_main.xml layout.
        setContentView(R.layout.activity_main);

        // Connect the TextView to Java using its ID.
        TextView textView = findViewById(R.id.textView);

        // Connect the Button to Java using its ID.
        Button btn = findViewById(R.id.button);

        // Register a listener for the button click.
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Update the TextView after the button is clicked.
                textView.setText("Hello Android Programmers");

            }
        });
    }
}
