package com.example.videolecture.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.videolecture.R;

public class ProductFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "text";
    private static final String ARG_PARAM3 = "image";

    // TODO: Rename and change types of parameters
    private int Category_id;
    private String Category_name;
    private String Category_image;

    public ProductFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(int id, String text, String image) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt("Category_id", id);
        args.putString("Category_name", text);
        args.putString("Category_image", image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Category_id = getArguments().getInt("Category_id");
            Category_name = getArguments().getString("Category_name");
            Category_image = getArguments().getString("Category_image");
        }
    }

    Context context;
    View view;
    ImageView image_view;
    TextView text_view;

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
        text_view.setText(Category_name);
        Glide.with(context).load(Category_image).into(image_view);
    }
}
