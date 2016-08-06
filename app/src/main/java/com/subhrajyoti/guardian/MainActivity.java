package com.subhrajyoti.guardian;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<ContactModel> arrayList;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getPermissions();
        arrayList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(arrayList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        startService(new Intent(MainActivity.this,MyService.class));
        startActivity(new Intent(MainActivity.this, ContactsActivity.class));

    }

    @OnClick(R.id.fab)
    void fabClick(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final View myView = findViewById(R.id.coordinatorLayout);

            int cx = fab.getLeft();
            int cy = fab.getTop();
            float radius = (float) Math.hypot(myView.getWidth(),myView.getHeight());

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, radius);
            myView.setBackgroundColor(ContextCompat.getColor(this,R.color.circularRevealColor));
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    startActivity(new Intent(MainActivity.this,ReportActivity.class));
                }
            });

            anim.start();
        }
        else
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



}
