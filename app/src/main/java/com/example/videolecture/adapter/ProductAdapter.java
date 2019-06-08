package com.example.videolecture.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    Context context;
    List<Result> resultList;

    public ProductAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_product, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder myViewHolder, int i) {
        Glide.with(context).load(R.drawable.download).into(myViewHolder.item_video);
        myViewHolder.item_txt_title.setText(resultList.get(i).getV_title());
        myViewHolder.item_txt_description.setText(resultList.get(i).getV_description());
        myViewHolder.item_txt_review.setText(resultList.get(i).getV_review());
        myViewHolder.recycle_que_ans.setLayoutManager(new LinearLayoutManager(context));
        myViewHolder.recycle_que_ans.setAdapter(new QueAnsAdapter(context, resultList));
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_video;
        TextView item_txt_title, item_txt_description, item_txt_review, item_txt_que_time;
        EditText item_edt_ques;
        RatingBar item_rating;
        Button item_btn_submit;
        RecyclerView recycle_que_ans;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_video=itemView.findViewById(R.id.item_video);
            item_txt_title=itemView.findViewById(R.id.item_txt_title);
            item_txt_description=itemView.findViewById(R.id.item_txt_description);
            item_txt_review=itemView.findViewById(R.id.item_txt_review);
            item_edt_ques=itemView.findViewById(R.id.item_edt_ques);
            item_txt_que_time=itemView.findViewById(R.id.item_txt_que_time);
            item_btn_submit=itemView.findViewById(R.id.item_btn_submit);
            item_rating=itemView.findViewById(R.id.item_rating);
            recycle_que_ans=itemView.findViewById(R.id.recycle_que_ans);
        }
    }
}
