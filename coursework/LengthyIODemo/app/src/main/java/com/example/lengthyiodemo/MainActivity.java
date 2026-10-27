package com.example.lengthyiodemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    Button button2;

    ExecutorService executor;
    Future<String> future;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                new androidx.core.view.OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(
                            View v, WindowInsetsCompat insets) {
                        Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                        v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                        return insets;
                    }
                });

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick(view);
            }
        });
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusClick(view);
            }
        });
    }

    public void buttonClick(View view) {
        executor = Executors.newSingleThreadExecutor();
        future = executor.submit(new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return "Task Interrupted";
                }
                return "Task Completed";
            }
        });
        executor.shutdown();
    }

    public void statusClick(View view) {
        if (future == null) {
            textView.setText("Please click Submit first");
            return;
        }
        if (future.isDone()) {
            try {
                String result = future.get();
                textView.setText(result);
            } catch (Exception e) {
                textView.setText("Task failed");
                e.printStackTrace();
            }
        } else {
            textView.setText("Waiting");
        }
    }
}
