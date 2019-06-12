package com.example.videolecture.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.videolecture.R;
import com.example.videolecture.database.DbHelper;
import com.example.videolecture.fragment.ProductFragment;
import com.example.videolecture.framework.IAsyncWorkCompletedCallback;
import com.example.videolecture.framework.ServiceCaller;
import com.example.videolecture.model.Result;
import com.example.videolecture.utilities.Utility;

import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class QueAnsAdapter extends RecyclerView.Adapter<QueAnsAdapter.MyViewHolder> {

    Context context;
    List<Result> resultList;
    ProductFragment productFragment;
    String sub_category_id;

    public QueAnsAdapter(Context context, List<Result> resultList, ProductFragment productFragment, String sub_category_id) {
        this.context = context;
        this.resultList = resultList;
        this.productFragment = productFragment;
        this.sub_category_id = sub_category_id;
    }

    @NonNull
    @Override
    public QueAnsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_que_ans, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        DbHelper dbHelper=new DbHelper(context);
        List<Result> resultList1=dbHelper.GetAllQuesDataBasedOnQues(resultList.get(i).getQuestion());
        myViewHolder.item_txt_que.setText(resultList.get(i).getQuestion());
        myViewHolder.item_txt_ques_time.setText(resultList1.get(i).getTime());
        myViewHolder.recycle_inner_ans.setLayoutManager(new LinearLayoutManager(context));
        myViewHolder.recycle_inner_ans.setAdapter(new AnswerAdater(context, resultList1));
        SharedPreferences preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        final int loginid = preferences.getInt("id", 0);
        myViewHolder.item_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ansText = myViewHolder.item_edt_ans.getText().toString();
                if (ansText.length() != 0) {
                    if (Utility.isOnline(context)) {
                        final ProgressDialog dialog = new ProgressDialog(context);
                        dialog.setMessage("Loading Data..");
                        dialog.show();
                        ServiceCaller serviceCaller = new ServiceCaller(context);
                        serviceCaller.callUploadQuesAnsData(loginid, resultList.get(i).getQuestion(), sub_category_id, ansText, new IAsyncWorkCompletedCallback() {
                            @Override
                            public void onDone(String workName, boolean isComplete) {
                                dialog.dismiss();
                                if (isComplete) {
                                    if (!workName.trim().equalsIgnoreCase("no")) {
                                        productFragment.showQuesAns();
                                        Toast.makeText(context, "Your answer uploaded successfull", Toast.LENGTH_SHORT).show();
                                        myViewHolder.item_edt_ans.setText("");
                                    } else {
                                        Toasty.error(context, "Your answer does not upload", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toasty.error(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    myViewHolder.item_edt_ans.setError("Enter Answer Please");
                    myViewHolder.item_edt_ans.requestFocus();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_txt_que, item_txt_ques_time;
        EditText item_edt_ans;
        RecyclerView recycle_inner_ans;
        Button item_btn_submit;
        String ansText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_txt_que = itemView.findViewById(R.id.item_txt_que);
            item_txt_ques_time = itemView.findViewById(R.id.item_txt_ques_time);
            item_edt_ans = itemView.findViewById(R.id.item_edt_ans);
            item_btn_submit = itemView.findViewById(R.id.item_btn_submit);
            recycle_inner_ans = itemView.findViewById(R.id.recycle_inner_ans);
        }
    }
}
