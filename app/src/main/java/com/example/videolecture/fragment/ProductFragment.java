package com.example.videolecture.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videolecture.R;
import com.example.videolecture.adapter.QueAnsAdapter;
import com.example.videolecture.adapter.SubCategoryAdapter;
import com.example.videolecture.database.DbHelper;
import com.example.videolecture.framework.IAsyncWorkCompletedCallback;
import com.example.videolecture.framework.ServiceCaller;
import com.example.videolecture.model.MyPojo;
import com.example.videolecture.model.Result;
import com.example.videolecture.utilities.ReadMoreTextView;
import com.example.videolecture.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class ProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";

    // TODO: Rename and change types of parameters
    public String sub_category_id;

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
    //    ImageView  video;
    TextView text_view, txt_title, txt_time, txt_review;
    ReadMoreTextView txt_description;
    List<Result> list;
    RecyclerView reycycle_ques_ans;
    int loginid;
    Button btn_submit;
    public EditText edt_ques;
    String quesTxt;
    JCVideoPlayerStandard video_player;
    List<Result> resultList;
    QueAnsAdapter queAnsAdapter;
    DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_product, container, false);
        reycycle_ques_ans=view.findViewById(R.id.reycycle_ques_ans);
        SharedPreferences preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        loginid = preferences.getInt("id", 0);
        dbHelper=new DbHelper(context);
        init();
        uploadQues();
        showQuesAns();
        return view;
    }

    private void init() {
        text_view = view.findViewById(R.id.text_view);
        video_player = view.findViewById(R.id.video_player);
        txt_title = view.findViewById(R.id.txt_title);
        txt_time = view.findViewById(R.id.txt_time);
        txt_description = view.findViewById(R.id.txt_description);
        txt_review = view.findViewById(R.id.txt_review);
//        video = view.findViewById(R.id.video);
        reycycle_ques_ans = view.findViewById(R.id.reycycle_ques_ans);
        list = new ArrayList<>();
//        SharedPreferences preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
//        loginid = preferences.getInt("id", 0);
        if (Utility.isOnline(context)) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Loading Data..");
            dialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(context);
            serviceCaller.callAllProductData(sub_category_id, loginid, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    dialog.dismiss();
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo myPojo = new Gson().fromJson(workName, MyPojo.class);
                            for (Result result : myPojo.getResult()) {
//                                list.addAll(Arrays.asList(result));
                                video_player.setUp(result.getVideo(), video_player.SCREEN_LAYOUT_NORMAL, "Lk");
                                video_player.thumbImageView.setImageURI(Uri.parse("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"));
                                txt_review.setText(result.getTotalRating() + "*");
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


    private void uploadQues() {
        btn_submit = view.findViewById(R.id.btn_submit);
        edt_ques = view.findViewById(R.id.edt_ques);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quesTxt = edt_ques.getText().toString();
                if (Utility.isOnline(context)) {
                    final ProgressDialog dialog = new ProgressDialog(context);
                    dialog.setMessage("Loading Data..");
                    dialog.show();
                    ServiceCaller serviceCaller = new ServiceCaller(context);
                    serviceCaller.callUploadQuesData(loginid, quesTxt, sub_category_id, new IAsyncWorkCompletedCallback() {
                        @Override
                        public void onDone(String workName, boolean isComplete) {
                            dialog.dismiss();
                            if (isComplete){
                                if (workName.trim().equalsIgnoreCase("success")){
                                    if (quesTxt.length()!=0){
                                        showQuesAns();
                                        Collections.reverse(resultList);
                                        Toast.makeText(context, "Your question uploaded successfull", Toast.LENGTH_SHORT).show();
                                        edt_ques.setText("");
                                    }
                                    else {
                                        Toasty.error(context, "Please enter question", Toast.LENGTH_SHORT).show();

                                    }

                                }

                                else {
                                    Toasty.error(context, "Your question does not upload", Toast.LENGTH_SHORT).show();
                                }
                            }

                            else {
                                Toasty.error(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                else {
                    Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void showQuesAns() {
        resultList = new ArrayList<>();
        if (Utility.isOnline(context)) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Loading Data...");
            dialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(context);
            serviceCaller.callShowQuesAnsData(loginid, sub_category_id, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    dialog.dismiss();
                   if (isComplete){
                       if (!workName.trim().equalsIgnoreCase("no")){
                           MyPojo myPojo=new Gson().fromJson(workName, MyPojo.class);
                           for (Result result:myPojo.getResult()){
                               resultList.addAll(Arrays.asList(result));
                           }
                           if (resultList!=null && resultList.size()>0){
                               for (Result result:resultList){
                                   dbHelper.upsertProductData(result);
                               }

                               sortProductData(resultList);
                           }

                       }

                       else {
                           Toasty.error(context, "No data found", Toast.LENGTH_SHORT).show();
                       }

                   }

                   else {
                       Toasty.error(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                   }
                }
            });
        } else {
            Toasty.info(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();

        }
    }

    private void sortProductData(List<Result> resultList) {
        List<Result> newList = new ArrayList<Result>();
        for (Result c : resultList) {
            if (!categoryDataExist(newList, c.getId().trim())) {
                newList.add(c);
            }
        }
        queAnsAdapter = new QueAnsAdapter(context, newList, ProductFragment.this);
        reycycle_ques_ans.setLayoutManager(new LinearLayoutManager(context));
        reycycle_ques_ans.setAdapter(queAnsAdapter);
            }


    private boolean categoryDataExist(List<Result> newList, String name) {
        for (Result c : newList) {
            if (c.getId().trim().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }


        }

