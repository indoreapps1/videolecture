package com.example.videolecture.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.videolecture.R;
import com.example.videolecture.activity.OtpVerificationActivity;
import com.example.videolecture.adapter.SubCategoryAdapter;
import com.example.videolecture.framework.IAsyncWorkCompletedCallback;
import com.example.videolecture.framework.ServiceCaller;
import com.example.videolecture.model.MyPojo;
import com.example.videolecture.model.Result;
import com.example.videolecture.utilities.CompatibilityUtility;
import com.example.videolecture.utilities.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SubCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubCategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "image";
    private static final String ARG_PARAM3 = "text";

    // TODO: Rename and change types of parameters
    private int category_id;
    private String category_image;
    private String category_text;
    // Required empty public constructor

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //     * @param param1 Parameter 1.
     * //     * @param param2 Parameter 2.
     *
     * @return A new instance of fragment SubCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubCategoryFragment newInstance(int id, String image, String text) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putInt("category_id", id);
        args.putString("category_image", image);
        args.putString("category_text", text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category_id = getArguments().getInt("category_id");
            category_image = getArguments().getString("category_image");
            category_text = getArguments().getString("category_text");
        }
    }

    Context context;
    View view;
    RecyclerView recycle_sub_category;
    SubCategoryAdapter subCategoryAdapter;
    ImageView image_view;
    TextView text_view;
    List<Result> resultList;
    boolean CheckOrientation = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        recycle_sub_category = view.findViewById(R.id.recycle_sub_category);
        image_view = view.findViewById(R.id.image_view);
        text_view = view.findViewById(R.id.text_view);
        Glide.with(context).load(category_image).placeholder(R.drawable.logo).into(image_view);
        text_view.setText(category_text);
        setSubCategory();
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

    private void setSubCategory() {
        resultList = new ArrayList<>();
        if (Utility.isOnline(context)) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Loading Data...");
            dialog.show();
            ServiceCaller serviceCaller = new ServiceCaller(context);
            serviceCaller.callSubCategoryData(category_id, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String workName, boolean isComplete) {
                    dialog.dismiss();
                    if (isComplete) {
                        if (!workName.trim().equalsIgnoreCase("no")) {
                            MyPojo myPojo = new Gson().fromJson(workName, MyPojo.class);
                            for (Result result : myPojo.getResult()) {
                                resultList.addAll(Arrays.asList(result));
                            }
                            if (resultList != null && resultList.size() > 0) {
                                recycle_sub_category.setLayoutManager(new LinearLayoutManager(context));
                                recycle_sub_category.setAdapter(new SubCategoryAdapter(context, resultList));
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
}
