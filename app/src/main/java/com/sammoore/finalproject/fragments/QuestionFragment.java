package com.sammoore.finalproject.fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sammoore.finalproject.R;
import com.sammoore.finalproject.databinding.FragmentQuestionBinding;
import com.sammoore.finalproject.models.Question;
import com.sammoore.finalproject.viewmodels.QuizViewModel;

import java.util.Collections;

public class QuestionFragment extends Fragment {
    private QuizViewModel viewModel;
    private Question question;
    private RadioButton[] answerButtons;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

        question = viewModel.getNextQuestion();
        Collections.shuffle(question.getAnswers());

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .remove(QuestionFragment.this)
                        .commit();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentQuestionBinding binding = FragmentQuestionBinding.inflate(inflater, container, false);

        if (question != null) {
            binding.question.setText(question.getQuestion());

            int[] ids = { R.id.radioButton1, R.id.radioButton2, R.id.radioButton3, R.id.radioButton4 };

            // Create options
            answerButtons = new RadioButton[question.getAnswers().size()];
            for (int i=0; i<answerButtons.length; i++) {
                answerButtons[i] = new RadioButton(getContext());
                answerButtons[i].setId(ids[i]);
                answerButtons[i].setText(question.getAnswers().get(i).getAnswer());
                answerButtons[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                binding.answers.addView(answerButtons[i], i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
        binding.grade.setOnClickListener(view -> {
            int selectedId = binding.answers.getCheckedRadioButtonId();

            if (selectedId == -1) {
                AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Please answer the question!")
                        .setPositiveButton("Ok", ((dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        }))
                        .create();
                dialog.show();
            } else {
                int selectedAnswerIndex = 0;
                for (int i=0; i<answerButtons.length; i++) {
                    if (answerButtons[i].getId() == selectedId) {
                        selectedAnswerIndex = i;
                        break;
                    }
                }

                boolean isCorrect = viewModel.gradeQuestion(question, question.getAnswers().get(selectedAnswerIndex));

                AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                        .setTitle((isCorrect) ? "Nice job!" : "Sorry!")
                        .setMessage((isCorrect) ? "You got it correct" : "You didn't get it correct")
                        .setNegativeButton("Home", (dialogInterface, i) -> {
                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .remove(this)
                                    .commit();
                            requireActivity().getSupportFragmentManager().popBackStack();

                        })
                        .setPositiveButton("Continue", ((dialogInterface, i) -> {
                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainerView, QuestionFragment.class, null)
                                    .commit();
                        }))
                        .setCancelable(false)
                        .create();
                dialog.show();
            }
        });

        return binding.getRoot();
    }
}