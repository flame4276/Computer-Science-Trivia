package com.sammoore.finalproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sammoore.finalproject.databinding.FragmentAnalyticsBinding;
import com.sammoore.finalproject.models.TagQuestionInformation;
import com.sammoore.finalproject.viewmodels.QuizViewModel;

import java.util.HashMap;

public class AnalyticsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        QuizViewModel viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        FragmentAnalyticsBinding binding = FragmentAnalyticsBinding.inflate(inflater, container, false);

        HashMap<String, TagQuestionInformation> tagQuestionInformation = viewModel.getTagQuestionInformation();

        System.out.println("Size of tag array: " + tagQuestionInformation.size());
        binding.recyclerView.setAdapter(new AnalyticsAdapter(tagQuestionInformation.values().toArray(new TagQuestionInformation[0])));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }
}