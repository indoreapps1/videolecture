package com.example.videolecture.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import com.example.videolecture.R;
import com.example.videolecture.activity.LoginActivity;
import com.example.videolecture.activity.MainActivity;
import com.example.videolecture.adapter.QueAnsAdapter;
import com.example.videolecture.adapter.SubCategoryAdapter;
import com.example.videolecture.database.DbHelper;
import com.example.videolecture.framework.IAsyncWorkCompletedCallback;
import com.example.videolecture.framework.ServiceCaller;
import com.example.videolecture.model.MyPojo;
import com.example.videolecture.model.Result;
import com.example.videolecture.utilities.CompatibilityUtility;
import com.example.videolecture.utilities.ExpandableTextView;
import com.example.videolecture.utilities.ReadMoreTextView;
import com.example.videolecture.utilities.Utility;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class ProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    public String productId;

    public ProductFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String id) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString("productId", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getString("productId");
//            Category_name = getArguments().getString("Category_name");
//            Category_image = getArguments().getString("Category_image");
        }
    }

    Context context;
    View view;
    //    ImageView  video;
    TextView text_view, txt_title, txt_time, txt_review, tv_share;
    List<Result> list;
    RecyclerView reycycle_ques_ans;
    int loginid;
    Button btn_submit;
    public EditText edt_ques;
    String quesTxt, currentRating, url, title, description;
    JCVideoPlayerStandard video_player;
    List<Result> resultList;
    QueAnsAdapter queAnsAdapter;
    DbHelper dbHelper;
    ExpandableTextView txt_description;
    RatingBar ratingbar;
    float ratedValue;
    private AdView adView;
    AdRequest adRequest;
    boolean CheckOrientation = false;
    InterstitialAd mInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_product, container, false);
        init();
        getProductData();
        uploadQues();
        showQuesAns();
        chechPortaitAndLandSacpe();
        return view;
    }

    //chech Portait And LandSacpe Orientation
    public void chechPortaitAndLandSacpe() {
        if (CompatibilityUtility.isTablet(getActivity())) {
            CheckOrientation = true;
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            CheckOrientation = false;
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        JCVideoPlayer.releaseAllVideos();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void getProductData() {
//        Toast.makeText(context, "--"+loginid, Toast.LENGTH_SHORT).show();
        if (Utility.isOnline(context)) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Loading Data..");
            dialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(context);
            serviceCaller.callAllProductData(productId, loginid, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo myPojo = new Gson().fromJson(workName, MyPojo.class);
                            if (myPojo != null) {
                                for (Result result : myPojo.getResult()) {
                                    if (result != null) {
                                        productId = result.getProductId();
                                        currentRating = result.getCurrentRating();
                                        url = result.getVideo();
                                        title = result.getTitle();
                                        description = result.getDescription();
                                        video_player.setUp(url, video_player.SCREEN_LAYOUT_NORMAL);
                                        if (result.getTotalRating() != null) {
                                            txt_review.setText(result.getTotalRating() + "*");
                                        } else {
                                            txt_review.setText(0 + "*");
                                        }
                                        txt_title.setText(title);
                                        txt_description.setText(description);
//                                makeTextViewResizable(DetailTv, 3, "See More", true);
                                        txt_time.setText(result.getTime());
                                        manageRatingBar();
                                    }
                                }
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
            Toasty.info(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {

        adView = (AdView) view.findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        reycycle_ques_ans = view.findViewById(R.id.reycycle_ques_ans);
        SharedPreferences preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        loginid = preferences.getInt("id", 0);
//        Toast.makeText(context, "--"+loginid, Toast.LENGTH_SHORT).show();
        dbHelper = new DbHelper(context);
        text_view = view.findViewById(R.id.text_view);
        video_player = view.findViewById(R.id.video_player);
        txt_title = view.findViewById(R.id.txt_title);
        txt_time = view.findViewById(R.id.txt_time);
        txt_description = view.findViewById(R.id.txt_description);
        txt_review = view.findViewById(R.id.txt_review);
        tv_share = view.findViewById(R.id.tv_share);
        list = new ArrayList<>();
        String link = "https://play.google.com/store/apps/details?id=com.videolecture";
        tv_share.setOnClickListener(v -> {
            if (url != null && title != null && description != null) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, url + "\n" + title + "\n" + description + "\n" + "Download This App click this link " + link);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        adInst();
    }

    private void adInst() {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void uploadQues() {
        btn_submit = view.findViewById(R.id.btn_submit);
        edt_ques = view.findViewById(R.id.edt_ques);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quesTxt = edt_ques.getText().toString();
                if (quesTxt.length() != 0) {
                    if (Utility.isOnline(context)) {
                        final ProgressDialog dialog = new ProgressDialog(context);
                        dialog.setMessage("Loading Data..");
                        dialog.show();
                        ServiceCaller serviceCaller = new ServiceCaller(context);
                        serviceCaller.callUploadQuesData(loginid, quesTxt, productId, new IAsyncWorkCompletedCallback() {
                            @Override
                            public void onDone(String workName, boolean isComplete) {
                                dialog.dismiss();
                                if (isComplete) {
                                    if (workName.trim().equalsIgnoreCase("success")) {
                                        showQuesAns();
                                        Toasty.success(context, "Your question uploaded successfull", Toast.LENGTH_SHORT).show();
                                        edt_ques.setText("");
                                    } else {
                                        Toasty.error(context, "Your question does not upload", Toast.LENGTH_SHORT).show();
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
                    edt_ques.requestFocus();
                    edt_ques.setError("Enter Question Please");
                }
            }
        });

    }


    public void showQuesAns() {
        resultList = new ArrayList<>();
        if (Utility.isOnline(context)) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Fetching Data...");
            dialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(context);
            serviceCaller.callShowQuesAnsData(loginid, productId, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo myPojo = new Gson().fromJson(workName, MyPojo.class);
                            for (Result result : myPojo.getResult()) {
                                resultList.addAll(Arrays.asList(result));
                            }
                            if (resultList != null && resultList.size() > 0) {
                                for (Result result : resultList) {
                                    dbHelper.upsertProductData(result);
                                }
                                sortProductData(resultList);
                            }
                        }

                    } else {
                        Toasty.error(context, "Something went wrong In Getting Qus Ans", Toast.LENGTH_SHORT).show();
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
            if (!categoryDataExist(newList, c.getQuestion())) {
                newList.add(c);
            }
        }
        Collections.reverse(newList);
        queAnsAdapter = new QueAnsAdapter(context, newList, ProductFragment.this, productId);
        reycycle_ques_ans.setLayoutManager(new LinearLayoutManager(context));
        reycycle_ques_ans.setAdapter(queAnsAdapter);
    }


    private boolean categoryDataExist(List<Result> newList, String name) {
        for (Result c : newList) {
            if (c.getQuestion().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private void manageRatingBar() {
        ratingbar = view.findViewById(R.id.rating);
        if (currentRating != null && !currentRating.equalsIgnoreCase("0")) {
            ratingbar.setRating(Float.parseFloat(currentRating));
            ratingbar.setClickable(false);
            ratingbar.setSelected(false);
            ratingbar.setIsIndicator(true);
        } else {
            ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom_rating_dialog);
                    final RatingBar ratingBarii = dialog.findViewById(R.id.ratingBar);
                    Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                    Button btn_submit = dialog.findViewById(R.id.btn_submit);
                    dialog.show();
                    ratingBarii.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            ratedValue = ratingBar.getRating();

                        }
                    });
                    btn_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ratedValue != 0) {
                                uploadRating();
                                dialog.dismiss();
                            } else {
                                Toasty.info(context, "Select Feedback").show();
                            }
                        }
                    });
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                }
            });
        }
    }

    private void uploadRating() {
        if (Utility.isOnline(context)) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Uploading your feedback...");
            dialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(context);
            serviceCaller.callUploadRatingData(loginid, ratedValue, productId, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    dialog.dismiss();
                    if (isComplete) {
                        if (!workName.equalsIgnoreCase("no")) {
                            Toasty.success(context, "Your feedback send successfully", Toast.LENGTH_SHORT).show();
                            getProductData();
                        } else {
                            Toasty.error(context, "Your feedback does not send", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toasty.error(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toasty.info(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();

        }

    }

}

