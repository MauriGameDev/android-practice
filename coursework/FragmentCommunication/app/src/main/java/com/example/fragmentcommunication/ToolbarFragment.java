
package com.example.fragmentcommunication;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ToolbarFragment extends Fragment
        implements SeekBar.OnSeekBarChangeListener {

    // Step 6.2.1: Class variables
    private int seekvalue = 10;
    private EditText editText;

    // Step 6.2.5: Declare ToolbarListener interface
    ToolbarListener activityCallback;

    public interface ToolbarListener {

        public void onButtonClick(int fontSize, String text);

    }

    // Original fragment parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // Required empty public constructor
    public ToolbarFragment() {

    }

    // Creates a new instance of ToolbarFragment
    public static ToolbarFragment newInstance(String param1, String param2) {

        ToolbarFragment fragment = new ToolbarFragment();

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

    // Step 3: Create the fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.fragment_toolbar,
                container,
                false
        );
    }

    // Step 6.2.2: Connect widgets to Java code
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Get the SeekBar and attach its listener
        final SeekBar seekbar = view.findViewById(R.id.seekBar1);

        seekbar.setOnSeekBarChangeListener(this);

        // Get the Button and attach its click listener
        final Button button = view.findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                buttonClicked(view);

            }
        });

        // Get the EditText
        editText = view.findViewById(R.id.editText1);

        // Hide the keyboard when Done is pressed
        editText.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v,
                                                  int actionId,
                                                  KeyEvent event) {

                        if (actionId == EditorInfo.IME_ACTION_DONE) {

                            InputMethodManager imm =
                                    (InputMethodManager) requireContext()
                                            .getSystemService(
                                                    Context.INPUT_METHOD_SERVICE
                                            );

                            imm.hideSoftInputFromWindow(
                                    v.getWindowToken(), 0
                            );

                            return true;
                        }

                        return false;
                    }
                });
    }

    // Step 6.2.6: Connect the fragment to MainActivity
    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        try {

            // Establish communication with the activity
            activityCallback = (ToolbarListener) context;

        } catch (ClassCastException e) {

            throw new ClassCastException(
                    context.toString() + " must implement ToolbarListener"
            );
        }
    }

    // Step 6.2.6: Disconnect the fragment from MainActivity
    @Override
    public void onDetach() {

        super.onDetach();

        // Clear the activity reference
        activityCallback = null;
    }

    // Step 6.2.7: Handle button clicks
    public void buttonClicked(View view) {

        // Check whether the activity is connected
        if (activityCallback != null) {

            // Send the selected font size and entered text
            activityCallback.onButtonClick(
                    seekvalue,
                    editText.getText().toString()
            );
        }
    }

    // Step 6.1: Implement SeekBar listener methods

    // Step 6.2.4: Store the current SeekBar value
    @Override
    public void onProgressChanged(SeekBar seekBar,
                                  int progress,
                                  boolean fromUser) {

        // Save the selected font size
        seekvalue = progress;

    }

    // Called when the user starts moving the SeekBar
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    // Called when the user stops moving the SeekBar
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
