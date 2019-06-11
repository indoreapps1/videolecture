package com.example.videolecture.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.videolecture.R;
import com.example.videolecture.adapter.ProductAdapter;
import com.example.videolecture.framework.IAsyncWorkCompletedCallback;
import com.example.videolecture.framework.ServiceCaller;
import com.example.videolecture.model.MyPojo;
import com.example.videolecture.model.Result;
import com.example.videolecture.utilities.ReadMoreTextView;
import com.example.videolecture.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";

    // TODO: Rename and change types of parameters
    private String sub_category_id;

    public ProductFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String id) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString("sub_category_id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sub_category_id = getArguments().getString("sub_category_id");
//            Category_name = getArguments().getString("Category_name");
//            Category_image = getArguments().getString("Category_image");
        }
    }

    Context context;
    View view;
    ImageView image_view, video;
    TextView text_view, txt_title, txt_time, txt_review;
    ReadMoreTextView txt_description;
    List<Result> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_product, container, false);
        init();
        return view;
    }

    private void init() {
        text_view = view.findViewById(R.id.text_view);
        image_view = view.findViewById(R.id.image_view);
        txt_title = view.findViewById(R.id.txt_title);
        txt_time = view.findViewById(R.id.txt_time);
        txt_description = view.findViewById(R.id.txt_description);
        txt_review = view.findViewById(R.id.txt_review);
        video = view.findViewById(R.id.video);
        txt_review.setText("5480k");
        list = new ArrayList<>();
        SharedPreferences preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        int loginid = preferences.getInt("id", 0);
        if (Utility.isOnline(context)) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Loading Data..");
            dialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(context);
            serviceCaller.callAllProductData(sub_category_id,loginid, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    dialog.dismiss();
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo myPojo = new Gson().fromJson(workName, MyPojo.class);
                            for (Result result : myPojo.getResult()) {
//                                list.addAll(Arrays.asList(result));
                                txt_review.setText(result.getTotalRating()+"*");
                                txt_title.setText(result.getTitle());
                                txt_description.setText(result.getDescription());
                                txt_time.setText(result.getTime());

                            }

                        } else {
                            Toasty.error(context, "No data found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toasty.error(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        } else {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }


}
