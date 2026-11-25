
package com.example.navdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag2 extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        // Inflate the second fragment layout
        return inflater.inflate(
                R.layout.fragment_frag2, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Locate the TextView
        TextView argText = view.findViewById(R.id.argText);

        // Verify that the TextView and arguments exist
        if (argText != null && getArguments() != null) {

            // Retrieve the Safe Args arguments
            Frag2Args args =
                    Frag2Args.fromBundle(getArguments());

            // Retrieve the message from Frag1
            String mesg = args.getMesg();

            // Display the message
            argText.setText(mesg);
        }
    }
}
