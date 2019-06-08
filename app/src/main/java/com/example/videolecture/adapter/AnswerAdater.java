package com.example.videolecture.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.videolecture.model.Result;

import java.util.List;

public class AnswerAdater extends RecyclerView.Adapter<AnswerAdater.MYViewHolder> {
    Context context;
    List<Result> resultList;
    @NonNull
    @Override
    public AnswerAdater.MYViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdater.MYViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MYViewHolder extends RecyclerView.ViewHolder {
        public MYViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
