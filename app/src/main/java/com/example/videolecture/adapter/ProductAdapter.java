package com.example.videolecture.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        View view= LayoutInflater.from(context).inflate(R.layout.item_category, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder myViewHolder, int i) {
        Glide.with(context).load(resultList.get(i).getImage()).into(myViewHolder.item_category_img);
        myViewHolder.item_category_txt.setText(resultList.get(i).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_category_img;
        TextView item_category_txt;
        CardView item_category_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_category_img=itemView.findViewById(R.id.item_category_img);
            item_category_txt=itemView.findViewById(R.id.item_category_txt);
            item_category_card=itemView.findViewById(R.id.item_category_card);
        }
    }
}
