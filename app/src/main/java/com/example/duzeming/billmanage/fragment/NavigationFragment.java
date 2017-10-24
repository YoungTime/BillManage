package com.example.duzeming.billmanage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duzeming.billmanage.R;
import com.example.duzeming.billmanage.utility.ChartEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by DuZeming on 2017/9/12.
 */
public class NavigationFragment extends Fragment {

    private LinearLayout llToList;
    private LinearLayout llToChart;
    private LinearLayout llToMy;

    private ImageView ivList;
    private ImageView ivChart;
    private ImageView ivMy;

    private TextView tvList;
    private TextView tvChart;
    private TextView tvMy;


    private ListFragment frgList;
    private ChartFragment frgChart;
    private MyFragment frgMy;

    private FragmentManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        llToList = (LinearLayout) view.findViewById(R.id.ll_navigation_list);
        llToChart = (LinearLayout) view.findViewById(R.id.ll_navigation_chart);
        llToMy = (LinearLayout) view.findViewById(R.id.ll_navigation_my);

        ivList = (ImageView) view.findViewById(R.id.iv_navigation_list);
        ivChart = (ImageView) view.findViewById(R.id.iv_navigation_chart);
        ivMy = (ImageView) view.findViewById(R.id.iv_navigation_my);

        tvList = (TextView) view.findViewById(R.id.tv_navigation_list);
        tvChart = (TextView) view.findViewById(R.id.tv_navigation_chart);
        tvMy = (TextView) view.findViewById(R.id.tv_navigation_my);

        frgList = new ListFragment();
        frgChart = new ChartFragment();
        frgMy = new MyFragment();

        changeColor(2);

        manager = getActivity().getSupportFragmentManager();

        manager.beginTransaction().add(R.id.fra_up, frgList).add(R.id.fra_up, frgChart).add(R.id.fra_up, frgMy)
                .hide(frgList).hide(frgMy).commit();

        llToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().hide(frgChart).hide(frgMy).show(frgList).commit();
                changeColor(1);
            }
        });
        llToChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().hide(frgList).hide(frgMy).show(frgChart).commit();
                changeColor(2);
                EventBus.getDefault().post(new ChartEvent(1));
            }
        });
        llToMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().hide(frgList).hide(frgChart).show(frgMy).commit();
                changeColor(3);
            }
        });

        return view;
    }
    
    private void changeColor(int a){
        if (a == 1){
            ivList.setImageResource(R.drawable.list);
            tvList.setTextColor(getResources().getColor(R.color.colorPrimary));
            ivChart.setImageResource(R.drawable.chart_out);
            tvChart.setTextColor(getResources().getColor(R.color.colorOut));
            ivMy.setImageResource(R.drawable.my_out);
            tvMy.setTextColor(getResources().getColor(R.color.colorOut));
        }else if(a == 2){
            ivList.setImageResource(R.drawable.list_out);
            tvList.setTextColor(getResources().getColor(R.color.colorOut));
            ivChart.setImageResource(R.drawable.chart);
            tvChart.setTextColor(getResources().getColor(R.color.colorPrimary));
            ivMy.setImageResource(R.drawable.my_out);
            tvMy.setTextColor(getResources().getColor(R.color.colorOut));
        }else {
            ivList.setImageResource(R.drawable.list_out);
            tvList.setTextColor(getResources().getColor(R.color.colorOut));
            ivChart.setImageResource(R.drawable.chart_out);
            tvChart.setTextColor(getResources().getColor(R.color.colorOut));
            ivMy.setImageResource(R.drawable.my);
            tvMy.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}
