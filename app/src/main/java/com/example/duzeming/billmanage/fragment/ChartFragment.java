package com.example.duzeming.billmanage.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duzeming.billmanage.R;
import com.example.duzeming.billmanage.helper.BillDataBaseHelper;
import com.example.duzeming.billmanage.utility.BillDataBaseMethod;
import com.example.duzeming.billmanage.utility.ChartEvent;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by DuZeming on 2017/9/12.
 */
public class ChartFragment extends Fragment {

    private TextView tvMon;
    private TextView tvZhi;
    private TextView tvWei;
    private TextView tvCash;
    private TextView tvOth;
    private TextView tvLeft;
    private LinearLayout linAll;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;

    private SQLiteDatabase database;
    private BillDataBaseHelper helper;
    private BillDataBaseMethod method;
    private AnimatorSet set;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        EventBus.getDefault().register(this);
        tvMon = (TextView) view.findViewById(R.id.tv_chart_mon);
        tvZhi = (TextView) view.findViewById(R.id.tv_chart_zhi);
        tvWei = (TextView) view.findViewById(R.id.tv_chart_wei);
        tvCash = (TextView) view.findViewById(R.id.tv_chart_cash);
        tvOth = (TextView) view.findViewById(R.id.tv_chart_oth);
        tvLeft = (TextView) view.findViewById(R.id.tv_chart_left);

        linAll= (LinearLayout) view.findViewById(R.id.lin_all);
        iv1= (ImageView) view.findViewById(R.id.iv_1);
        iv2= (ImageView) view.findViewById(R.id.iv_2);
        iv3= (ImageView) view.findViewById(R.id.iv_3);

        int x = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        helper = new BillDataBaseHelper(getActivity(), "Bill.db", null, 1);


        if (getMon() == -0.0){
            tvMon.setText("0.0元");
        }else {
            tvMon.setText(-getMon() + "元");
        }
        tvMon.setText(-getMon() + "元");
        tvZhi.setText(getZhi()+"元");
        tvWei.setText(getWei()+"元");
        tvCash.setText(getCash()+"元");
        tvOth.setText(getOth()+"元");
        tvLeft.setText(getZhi()+getWei()+getCash()+getOth()+"元");

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv1, "rotation", -0f, 360f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(iv2, "rotation", -0f, 360f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(iv3, "rotation", -0f, 360f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(linAll, "translationX", 0, 2 * x);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(linAll, "translationX", -x, 0);

         set = new AnimatorSet();


        set.play(animator1).with(animator3);
        set.play(animator2).after(animator1);
        set.play(animator4).after(animator2);
        set.play(animator5).after(animator4);
        set.setDuration(1000).start();



        return view;
    }


    private double getMon() {


         double mon = 0.0;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from Bill where year = ?"+" and  month = ?"
                , new String[]{String.valueOf(year),String.valueOf(month)});
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getDouble(cursor.getColumnIndex("money")) < 0) {
                    mon += cursor.getDouble(cursor.getColumnIndex("money"));
                    System.out.println("月份"+cursor.getDouble(cursor.getColumnIndex("money"))+"元");
                }
            } while (cursor.moveToNext());
        }

        return mon;

    }

    private double getZhi() {

         double zhi = 0.0;

        database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from Bill where mode = ?"
                , new String[]{String.valueOf(0)});
        if (cursor.moveToFirst()) {
            do {
                zhi += cursor.getDouble(cursor.getColumnIndex("money"));

            } while (cursor.moveToNext());
        }

        return zhi;
    }

    private double getWei() {

         double wei = 0.0;
        database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from Bill where mode = ?"
                , new String[]{String.valueOf(1)});
        if (cursor.moveToFirst()) {
            do {
                wei += cursor.getDouble(cursor.getColumnIndex("money"));

            } while (cursor.moveToNext());
        }

        return wei;
    }

    private double getCash() {

         double cash = 0.0;
        database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from Bill where mode = ?"
                , new String[]{String.valueOf(2)});
        if (cursor.moveToFirst()) {
            do {
                cash += cursor.getDouble(cursor.getColumnIndex("money"));

            } while (cursor.moveToNext());
        }

        return cash;
    }

    private double getOth() {
        double oth = 0.0;

        database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from Bill where mode = ?"
                , new String[]{String.valueOf(3)});
        if (cursor.moveToFirst()) {
            do {
                oth += cursor.getDouble(cursor.getColumnIndex("money"));

            } while (cursor.moveToNext());
        }

        return oth;
    }

    private double getLeft() {
        double left = 0.0;

        database = helper.getWritableDatabase();
        Cursor cursor = database.query("Bill", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                left += cursor.getDouble(cursor.getColumnIndex("money"));

            } while (cursor.moveToNext());


        }

        return left;
    }
    @Subscribe
    public void onEventMainThread(ChartEvent event){
        if (getMon() == -0.0){
            tvMon.setText("0.0元");
        }else {
            tvMon.setText(-getMon() + "元");
        }
        tvMon.setText(-getMon() + "元");
        tvZhi.setText(getZhi()+"元");
        tvWei.setText(getWei()+"元");
        tvCash.setText(getCash()+"元");
        tvOth.setText(getOth()+"元");
        tvLeft.setText(getZhi() + getWei() + getCash() + getOth() + "元");
        set.start();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}