package com.example.videolecture.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.videolecture.R;
import com.example.videolecture.model.Result;

import java.util.List;

public class QueAnsAdapter extends RecyclerView.Adapter<QueAnsAdapter.MyViewHolder> {

    Context context;
    List<Result> resultList;

    public QueAnsAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public QueAnsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_que_ans, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.item_txt_que.setText(resultList.get(i).getQues());
        myViewHolder.recycle_inner_ans.setLayoutManager(new LinearLayoutManager(context));
        myViewHolder.recycle_inner_ans.setAdapter(new AnswerAdater(context, resultList));
    }


    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_txt_que, item_txt_view_more, item_txt_ans_time;
        EditText item_edt_ans;
        RecyclerView recycle_inner_ans;
        Button item_btn_submit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_txt_que=itemView.findViewById(R.id.item_txt_que);
            item_txt_view_more=itemView.findViewById(R.id.item_txt_view_more);
            item_txt_ans_time=itemView.findViewById(R.id.item_txt_ans_time);
            item_edt_ans=itemView.findViewById(R.id.item_edt_ans);
            item_btn_submit=itemView.findViewById(R.id.item_btn_submit);
            recycle_inner_ans=itemView.findViewById(R.id.recycle_inner_ans);
        }
    }
}
