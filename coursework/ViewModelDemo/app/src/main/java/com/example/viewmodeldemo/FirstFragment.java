package com.example.viewmodeldemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.viewmodeldemo.databinding.FragmentFirstBinding;

import java.util.Locale;

public class FirstFragment extends Fragment {

    // View Binding reference
    private FragmentFirstBinding binding;

    // ViewModel reference
    private MainViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create or retrieve the existing ViewModel
        viewModel = new ViewModelProvider(this)
                .get(MainViewModel.class);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        // Inflate the Fragment layout
        binding = FragmentFirstBinding.inflate(
                inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Observe changes to the LiveData result
        viewModel.getResult().observe(
                getViewLifecycleOwner(), result -> {

                    if (result == null) {

                        // Display a message when the result is cleared
                        binding.resultText.setText(
                                "Enter an amount");

                    } else {

                        // Automatically display the updated result
                        binding.resultText.setText(
                                String.format(
                                        Locale.US,
                                        "%.2f euros",
                                        result));
                    }
                });

        // Convert button click listener
        binding.convertButton.setOnClickListener(v -> {

            // Read the entered dollar amount
            String input = binding.dollarText
                    .getText().toString().trim();

            if (input.isEmpty()) {

                // Clear LiveData if no amount was entered
                viewModel.clearResult();

            } else {

                // Update LiveData through the ViewModel
                viewModel.setAmount(input);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Clean up View Binding
        binding = null;
    }
}
