package com.subhrajyoti.guardian.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.subhrajyoti.guardian.MyService;
import com.subhrajyoti.guardian.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.switchButton)
    Switch switchButton;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getPermissions();
        sharedPreferences = getSharedPreferences("Guardian", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("switch",false))
            switchButton.setChecked(true);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("switch",b);
                editor.apply();
                if (b)
                    startService(new Intent(MainActivity.this,MyService.class));
                else
                    stopService(new Intent(MainActivity.this,MyService.class));
            }
        });


    }

    @OnClick(R.id.fab)
    void fabClick(){
       startActivity(new Intent(MainActivity.this,ReportActivity.class));
    }

    private void getPermissions() {
        ArrayList<String> permissions = new ArrayList<>();

        int hasPermissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int hasPermissionContact = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int hasPermissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        //check if WRITE permission has been granted
        if (hasPermissionStorage != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.SEND_SMS);
        }

        if (hasPermissionContact != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_CONTACTS);
        }
        if (hasPermissionLocation != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissions.toArray(new String[permissions.size()]), 1);
        }
    }

    @OnClick(R.id.imageButton)
    public void startContacts(){
        startActivity(new Intent(MainActivity.this, ContactsActivity.class));
    }




}
