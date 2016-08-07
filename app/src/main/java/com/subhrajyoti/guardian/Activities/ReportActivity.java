package com.subhrajyoti.guardian.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.subhrajyoti.guardian.Models.ReportModel;
import com.subhrajyoti.guardian.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    EditText locationText;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.imageView)
    ImageView imageView;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private DatabaseReference mDatabase;
    private String imageString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
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
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @OnClick(R.id.timeText)
    void setTimeText(){
        timePickerDialog.show();
    }

    @OnClick(R.id.dateText)
    void setDateText(){
        datePickerDialog.show();
    }

    @OnClick(R.id.fab)
    void fabClicked(){
        ReportModel reportModel = new ReportModel(dateText.getText().toString(),timeText.getText().toString()
                ,locationText.getText().toString(),crimeText.getText().toString(),imageString, FirebaseInstanceId.getInstance().getToken());
        mDatabase.child("reports").push().setValue(reportModel);
        Snackbar.make(fab,"Report Added",Snackbar.LENGTH_SHORT).show();
        clearViews();
    }

    private void clearViews(){
        Log.d("Date",dateText.getText().toString());
        Log.d("Time",timeText.getText().toString());
        Log.d("Crime",crimeText.getText().toString());
        Log.d("Location",locationText.getText().toString());
        Log.d("Image",imageString);
        locationText.setText("");
        dateText.setText("");
        timeText.setText("");
        crimeText.setText("");
        imageView.setVisibility(View.INVISIBLE);
        locationText.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_attach:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                //convert image to string
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                bmp.recycle();
                byte[] bytes = byteArrayOutputStream.toByteArray();
                imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(parseImage(imageString));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap parseImage(String string) {
        byte[] imageBytes = Base64.decode(string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }


}
