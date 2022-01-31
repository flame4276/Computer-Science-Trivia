package com.sammoore.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.sammoore.finalproject.models.QuestionModel;
import com.sammoore.finalproject.viewmodels.QuizViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuizViewModel viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
    }
}