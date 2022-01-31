package com.sammoore.finalproject.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sammoore.finalproject.R;
import com.sammoore.finalproject.databinding.AnalyticsCardBinding;
import com.sammoore.finalproject.models.TagQuestionInformation;

import java.text.DecimalFormat;

public class AnalyticsAdapter extends RecyclerView.Adapter<AnalyticsAdapter.AnalyticHolder> {
    TagQuestionInformation[] tagQuestionInformation;

    public AnalyticsAdapter(TagQuestionInformation[] tagQuestionInformation) {
        this.tagQuestionInformation = tagQuestionInformation;
    }

    @NonNull
    @Override
    public AnalyticHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("View holder created");
        return new AnalyticHolder(AnalyticsCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnalyticHolder holder, int position) {
        System.out.println("View holder bound");
        holder.binding.imageView.setImageDrawable(null);
        switch (tagQuestionInformation[position].getTagName()) {
            case "Technologies":
                holder.binding.imageView.setBackgroundResource(R.drawable.ic_baseline_computer_24);
                break;
            case "Companies":
                holder.binding.imageView.setBackgroundResource(R.drawable.ic_baseline_people_24);
                break;
            case "People":
                holder.binding.imageView.setBackgroundResource(R.drawable.ic_baseline_person_24);
                break;
            default:
                holder.binding.imageView.setBackgroundResource(R.drawable.ic_baseline_android_24);
        }
        holder.binding.tagName.setText(tagQuestionInformation[position].getTagName() + ":");
        DecimalFormat format = new DecimalFormat("0.00");
        holder.binding.percentageCorrect.setText(format.format(tagQuestionInformation[position].getPercentageCorrect()*100) + "%");
    }

    @Override
    public int getItemCount() {
        return tagQuestionInformation.length;
    }

    public class AnalyticHolder extends RecyclerView.ViewHolder {
        AnalyticsCardBinding binding;

        public AnalyticHolder(AnalyticsCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
