package com.subhrajyoti.guardian;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
}
