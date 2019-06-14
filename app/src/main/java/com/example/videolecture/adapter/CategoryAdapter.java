package com.example.videolecture.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.videolecture.R;
import com.example.videolecture.fragment.ProductFragment;
import com.example.videolecture.fragment.SubCategoryFragment;
import com.example.videolecture.model.Result;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Result> resultList;

    public CategoryAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder myViewHolder, final int i) {
        byte[] decodedString = Base64.decode(resultList.get(i).getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        if (resultList.get(i).getImage().equalsIgnoreCase("")) {
            Glide.with(context).load(R.drawable.demo_logo).into(myViewHolder.item_category_img);
        } else {
            Glide.with(context).load(decodedByte).into(myViewHolder.item_category_img);
        }
        myViewHolder.item_category_txt.setText(resultList.get(i).getCategoryName());
        myViewHolder.item_category_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubCategoryFragment subCategoryFragment=SubCategoryFragment.newInstance(resultList.get(i).getId(), resultList.get(i).getImage(), resultList.get(i).getCategoryName());
                moveFragment(subCategoryFragment);
            }
        });
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
            item_category_img = itemView.findViewById(R.id.item_category_img);
            item_category_txt = itemView.findViewById(R.id.item_category_txt);
            item_category_card = itemView.findViewById(R.id.item_category_card);
        }
    }

    public void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
