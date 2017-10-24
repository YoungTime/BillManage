package com.example.duzeming.billmanage.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.duzeming.billmanage.R;
import com.example.duzeming.billmanage.adapter.ListRecycleViewAdapter;
import com.example.duzeming.billmanage.helper.BillDataBaseHelper;
import com.example.duzeming.billmanage.utility.BillDataBaseMethod;
import com.example.duzeming.billmanage.view.NewActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by DuZeming on 2017/9/12.
 */
public class ListFragment extends Fragment implements View.OnClickListener {

    private Button btnNew;
    private ListRecycleViewAdapter adapter;
    private RecyclerView recyclerView;

    private BillDataBaseMethod sqlMethod;
    private List<String> reason;
    private List<Integer> year;
    private List<Integer> month;
    private List<Integer> day;
    private List<Integer> hour;
    private List<Integer> minute;
    private List<Double> money;
    private List<Integer> mode;
    private List<String> showTime;
    private List<String> time;
    private String showHour;
    private String showMinute;
    private BillDataBaseHelper helper;
    private SQLiteDatabase database;
    private Button btnSearch;
    private Spinner spTime;
    private Spinner spMode;
    private int seTime;
    private int seMode;
    private EditText etSearch;
    private Button btnTooSea;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        btnNew = (Button) view.findViewById(R.id.btn_list_new);
        btnNew.setOnClickListener(this);

        reason = new ArrayList<String>();
        year = new ArrayList<Integer>();
        month = new ArrayList<Integer>();
        day = new ArrayList<Integer>();
        hour = new ArrayList<Integer>();
        minute = new ArrayList<Integer>();
        money = new ArrayList<Double>();
        mode = new ArrayList<Integer>();
        showTime = new ArrayList<String>();
        time = new ArrayList<String>();
        btnSearch = (Button) view.findViewById(R.id.btn_list_search);
        spTime= (Spinner) view.findViewById(R.id.sp_list_time);
        spMode= (Spinner) view.findViewById(R.id.sp_list_mode);
        etSearch = (EditText) view.findViewById(R.id.et_list_search);
        btnTooSea= (Button) view.findViewById(R.id.btn_list_toolSea);


        btnTooSea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolSerach(etSearch.getText().toString());
            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_list);
        adapter = new ListRecycleViewAdapter(getActivity(), reason, showTime, money, mode);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        sqlMethod = new BillDataBaseMethod(getActivity());
        helper = new BillDataBaseHelper(getActivity(), "Bill.db", null, 1);
        initData();

        adapter.setListClickListener(new ListRecycleViewAdapter.ListClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });


        spMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seMode = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seTime=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSpring(seTime, seMode);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_list_new:
                startActivity(new Intent(getActivity(), NewActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        adapter.notifyDataSetChanged();

    }

    private void initData() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        for (int i = 0; i < sqlMethod.getReason().size(); i++) {
            reason.clear();
            showTime.clear();
            money.clear();
            mode.clear();
        }

        for (int i = sqlMethod.getReason().size() - 1; i >= 0; i--) {

            reason.add(sqlMethod.getReason().get(i));
            if (sqlMethod.getHour().get(i) < 10) {
                showHour = "0" + sqlMethod.getHour().get(i);
            } else {
                showHour = sqlMethod.getHour().get(i) + "";
            }
            if (sqlMethod.getMinute().get(i) < 10) {
                showMinute = "0" + sqlMethod.getMinute().get(i);
            } else {
                showMinute = sqlMethod.getMinute().get(i) + "";
            }

            if (day == sqlMethod.getDay().get(i) && year == sqlMethod.getYear().get(i)) {
                showTime.add("今天 " + showHour + ":" + showMinute);
            } else if (day - sqlMethod.getDay().get(i) == 1 && year == sqlMethod.getYear().get(i)) {
                showTime.add("昨天 " + showHour + ":" + showMinute);
            } else {
                showTime.add(sqlMethod.getTime().get(i));
            }

            money.add(sqlMethod.getMoney().get(i));
            mode.add(sqlMethod.getMode().get(i));
            adapter.addData(0);
        }


    }

    private void searchSpring(int time, int mode) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        reason.clear();
        showTime.clear();
        money.clear();
        this.mode.clear();
        database = helper.getWritableDatabase();

        if (time == 0 && mode != 0){
            Cursor cursor = database.rawQuery("select * from Bill where mode = ?"
                    , new String[]{String.valueOf(mode-1)});
            if (cursor.moveToFirst()) {
                do {
                    reason.add(cursor.getString(cursor.getColumnIndex("reason")));
                    showTime.add(cursor.getString(cursor.getColumnIndex("time")));
                    this.mode.add(cursor.getInt(cursor.getColumnIndex("mode")));
                    money.add(cursor.getDouble(cursor.getColumnIndex("money")));
                    adapter.addData(0);
                } while (cursor.moveToNext());
            }
        }else if (time == 1 && mode != 0) {
            Cursor cursor = database.rawQuery("select * from Bill where year = ?"+" and month = ?"+"and day = ?"+"and mode = ?"
                    , new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(mode-1)});
            if (cursor.moveToFirst()) {
                do {
                    reason.add(cursor.getString(cursor.getColumnIndex("reason")));
                    showTime.add(cursor.getString(cursor.getColumnIndex("time")));
                    this.mode.add(cursor.getInt(cursor.getColumnIndex("mode")));
                    money.add(cursor.getDouble(cursor.getColumnIndex("money")));
                    adapter.addData(0);
                } while (cursor.moveToNext());
            }

        }else if (time == 2 && mode != 0){
            Cursor cursor1 = database.rawQuery("select * from Bill where year = ?"+"and month = ?"+"and mode = ?"
                    , new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(mode-1)});
            if (cursor1.moveToFirst()) {
                do {
                    reason.add(cursor1.getString(cursor1.getColumnIndex("reason")));
                    showTime.add(cursor1.getString(cursor1.getColumnIndex("time")));
                    this.mode.add(cursor1.getInt(cursor1.getColumnIndex("mode")));
                    money.add(cursor1.getDouble(cursor1.getColumnIndex("money")));
                    adapter.addData(0);
                } while (cursor1.moveToNext());
            }

        }else if (time == 1 && mode == 0){
            Cursor cursor = database.rawQuery("select * from Bill where year = ?"+" and month = ?"+"and day = ?"
                    , new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day)});
            if (cursor.moveToFirst()) {
                do {
                    reason.add(cursor.getString(cursor.getColumnIndex("reason")));
                    showTime.add(cursor.getString(cursor.getColumnIndex("time")));
                    this.mode.add(cursor.getInt(cursor.getColumnIndex("mode")));
                    money.add(cursor.getDouble(cursor.getColumnIndex("money")));
                    adapter.addData(0);
                } while (cursor.moveToNext());
            }

        }else if (time == 2 && mode == 0){
            Cursor cursor = database.rawQuery("select * from Bill where year = ?"+" and month = ?"
                    , new String[]{String.valueOf(year), String.valueOf(month)});
            if (cursor.moveToFirst()) {
                do {
                    reason.add(cursor.getString(cursor.getColumnIndex("reason")));
                    showTime.add(cursor.getString(cursor.getColumnIndex("time")));
                    this.mode.add(cursor.getInt(cursor.getColumnIndex("mode")));
                    money.add(cursor.getDouble(cursor.getColumnIndex("money")));
                    adapter.addData(0);
                } while (cursor.moveToNext());
            }

        }else {
            initData();
        }
        adapter.notifyDataSetChanged();
    }

    private void toolSerach(String a){
        reason.clear();
        showTime.clear();
        money.clear();
        this.mode.clear();
        database = helper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from Bill where reason like ?"
                , new String[]{"%"+a+"%"});
        if (cursor.moveToFirst()) {
            do {
                reason.add(cursor.getString(cursor.getColumnIndex("reason")));
                showTime.add(cursor.getString(cursor.getColumnIndex("time")));
                this.mode.add(cursor.getInt(cursor.getColumnIndex("mode")));
                money.add(cursor.getDouble(cursor.getColumnIndex("money")));
                adapter.addData(reason.size()-1);
            } while (cursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }

}
