
package com.example.fragmentcommunication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TextFragment extends Fragment {

    // Step 8.1: Declare the TextView variable
    private TextView textView;

    // Original fragment parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Required empty public constructor
    public TextFragment() {

    }

    // Creates a new instance of TextFragment
    public static TextFragment newInstance(
            String param1, String param2) {

        TextFragment fragment = new TextFragment();

        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    // Step 4: Create the fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.fragment_text,
                container,
                false
        );
    }

    // Step 8.2: Connect the TextView to Java
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Find the TextView in fragment_text.xml
        textView = view.findViewById(R.id.textView2);

    }

    // Step 8.3: Change the text and font size
    public void changeTextProperties(int fontSize,
                                     String text) {

        // Change the font size
        textView.setTextSize(fontSize);

        // Change the displayed text
        textView.setText(text);

    }
}
