
package com.example.navdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class Frag1 extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_frag1, container, false);

        Button button = view.findViewById(R.id.btn_submit);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Retrieve the user's message
                EditText userText =
                        view.findViewById(R.id.userText);

                String message =
                        userText.getText().toString().trim();

                // Check if the message is empty
                if (!message.isEmpty()) {

                    // Create the Safe Args action
                    Frag1Directions.ActionFrag1ToFrag2 action =
                            Frag1Directions.actionFrag1ToFrag2();

                    // Attach the message
                    action.setMesg(message);

                    // Navigate to Frag2
                    Navigation.findNavController(v)
                            .navigate(action);

                } else {

                    // Display an error
                    userText.setError("Please enter a message!");
                }
            }
        });

        return view;
    }
}
