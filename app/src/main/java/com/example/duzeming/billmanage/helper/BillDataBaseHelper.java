package com.example.duzeming.billmanage.helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by DuZeming on 2017/9/13.
 */
public class BillDataBaseHelper extends SQLiteOpenHelper {

    /**创建数据库表*/
    public static final String CREATE_BILL = "create table bill("
            +" id integer primary key autoincrement,"
            +" reason text,"
            +" year integer,"
            +" month integer,"
            +" day integer,"
            +" hour integer,"
            +" minute integer,"
            +" time text,"
            +" money real,"
            +" mode integer)";

    private Context mContext;


    public BillDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BILL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
