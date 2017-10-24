package com.example.duzeming.billmanage.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duzeming.billmanage.R;
import com.example.duzeming.billmanage.utility.BillDataBaseMethod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DuZeming on 2017/9/12.
 */
public class MyFragment extends Fragment {

    private ImageView imageView;
    private TextView textView;
    private LinearLayout linMy;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

//        imageView = (ImageView) view.findViewById(R.id.iv_my);
//        textView = new TextView(getContext());
//        linMy = (LinearLayout) view.findViewById(R.id.lin_my);
//
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        textView.setLayoutParams(lp);
//        textView.setText("叫你不要点啦");
//        textView.setTextSize(30.f);
//        linMy.addView(textView);
//
//        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "TranslationX", 0f, 200f);
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(textView, "TranslationY", 0f, 200f);
//
//        final AnimatorSet set = new AnimatorSet();
//        set.play(animator).with(animator1);
//        set.setDuration(1000);
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                set.start();
//            }
//        });


        return view;
    }
}
