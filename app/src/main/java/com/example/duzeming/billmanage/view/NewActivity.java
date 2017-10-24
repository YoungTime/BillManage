package com.example.duzeming.billmanage.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.duzeming.billmanage.R;
import com.example.duzeming.billmanage.utility.BillDataBaseMethod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCancel;
    private Button btnOk;
    private EditText etReason;
    private EditText etMoney;
    private Spinner spOr;
    private Spinner spMode;

    private String reason;
    private Double money;
    private String time;
    private int mode;
    private int or;

    private BillDataBaseMethod method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        etReason = (EditText) findViewById(R.id.et_new_reason);
        etMoney = (EditText) findViewById(R.id.et_new_money);
        spOr = (Spinner) findViewById(R.id.sp_new_or);
        spMode = (Spinner) findViewById(R.id.sp_new_mode);

        method = new BillDataBaseMethod(this);

        btnCancel = (Button) findViewById(R.id.btn_toolbar_cancel);
        btnOk = (Button) findViewById(R.id.btn_toolbar_ok);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        spOr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                or = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_toolbar_cancel:
                finish();
                break;
            case R.id.btn_toolbar_ok:
                addData();
                finish();
                break;
            default:
                break;
        }
    }

    private void addData(){
        reason = etReason.getText().toString();

        if (etMoney.getText().toString().equals("")){
            money = 0.0;
        }else {
            if (or == 0){
                money = 0 - Double.valueOf(etMoney.getText().toString());
            }else {
                money = Double.valueOf(etMoney.getText().toString());
            }
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        time = format.format(date);

        method.addData(reason,year,month,day,hour,minute,money,mode,time);



    }
}
