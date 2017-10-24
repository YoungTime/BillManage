package com.example.duzeming.billmanage.utility;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duzeming.billmanage.helper.BillDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DuZeming on 2017/9/13.
 */
public class BillDataBaseMethod {

    private BillDataBaseHelper helper;
    private Context mContext;
    private SQLiteDatabase database;
    private List<String> reason;
    private List<Integer> year;
    private List<Integer> month;
    private List<Integer> day;
    private List<Integer> hour;
    private List<Integer> minute;
    private List<Double> money;
    private List<Integer> mode;
    private List<String>time;
    private List<Integer>id;

    public BillDataBaseMethod(Context context){
        mContext = context;
        helper = new BillDataBaseHelper(context,"Bill.db",null,1);
        helper.getWritableDatabase();
        Log.i("数据库", "创建成功");
    }

    public void addData(String reason,int year,int month,int day,int hour,int minute,
                        Double money,int mode,String time){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("reason",reason);
        values.put("year",year);
        values.put("month",month);
        values.put("day",day);
        values.put("hour",hour);
        values.put("minute",minute);
        values.put("money",money);
        values.put("mode",mode);
        values.put("time",time);
        db.insert("Bill", null, values);
        values.clear();
    }

    public void deleteData(int pos){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("Bill", "id = ?", new String[]{pos + ""});
    }

    private void getData(){
        reason = new ArrayList<String>();
        year = new ArrayList<Integer>();
        month = new ArrayList<Integer>();
        day = new ArrayList<Integer>();
        hour = new ArrayList<Integer>();
        minute = new ArrayList<Integer>();
        money = new ArrayList<Double>();
        mode = new ArrayList<Integer>();
        time = new ArrayList<String>();
        id = new ArrayList<Integer>();

        database = helper.getWritableDatabase();
        Cursor cursor = database.query("Bill",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                reason.add(cursor.getString(cursor.getColumnIndex("reason")));
                year.add(cursor.getInt(cursor.getColumnIndex("year")));
                month.add(cursor.getInt(cursor.getColumnIndex("month")));
                day.add(cursor.getInt(cursor.getColumnIndex("day")));
                hour.add(cursor.getInt(cursor.getColumnIndex("hour")));
                minute.add(cursor.getInt(cursor.getColumnIndex("minute")));
                money.add(cursor.getDouble(cursor.getColumnIndex("money")));
                mode.add(cursor.getInt(cursor.getColumnIndex("mode")));
                time.add(cursor.getString(cursor.getColumnIndex("time")));
                id.add(cursor.getInt(cursor.getColumnIndex("id")));
            }while (cursor.moveToNext());
        }
    }
    public List<String> getReason() {
        getData();
        return reason;
    }

    public List<Integer> getId() {
        getData();
        return id;
    }

    public List<Integer> getYear() {
        getData();
        return year;
    }

    public List<Integer> getMonth() {
        getData();
        return month;
    }

    public List<Integer> getDay() {
        getData();
        return day;
    }

    public List<Integer> getHour() {
        getData();
        return hour;
    }

    public List<Integer> getMinute() {
        getData();
        return minute;
    }

    public List<Double> getMoney() {
        getData();
        return money;
    }

    public List<Integer> getMode() {
        getData();
        return mode;
    }

    public List<String> getTime() {
        getData();
        return time;
    }


}
