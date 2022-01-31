package com.sammoore.finalproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sammoore.finalproject.R;
import com.sammoore.finalproject.databinding.FragmentHomeBinding;
import com.sammoore.finalproject.viewmodels.QuizViewModel;


public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);

        QuizViewModel viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

        binding.quiz.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, QuestionFragment.class, null)
                    .addToBackStack(null)
                    .commit();
        });

        binding.analytics.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, AnalyticsFragment.class, null)
                    .addToBackStack(null)
                    .commit();
        });

        return binding.getRoot();
    }
}