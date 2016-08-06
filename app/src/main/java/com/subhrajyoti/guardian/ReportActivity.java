package com.subhrajyoti.guardian;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends AppCompatActivity {

    @BindView(R.id.dateText)
    EditText dateText;
    @BindView(R.id.timeText)
    EditText timeText;
    @BindView(R.id.crimeText)
    EditText crimeText;
    @BindView(R.id.locationText)
    EditText locatioText;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void init(){
        int year, month, day,hour, minutes;
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = String.valueOf(i).concat("/").concat(String.valueOf(i1)).concat("/").concat(String.valueOf(i2));
                dateText.setText(date);
            }
        },year, month, day);

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = String.valueOf(i).concat(":").concat(String.valueOf(i1));
                timeText.setText(time);
            }
        },hour, minutes, false);
    }

    @OnClick(R.id.timeText)
    void setTimeText(){
        timePickerDialog.show();
    }

    @OnClick(R.id.dateText)
    void setDateText(){
        datePickerDialog.show();
    }


}
