package com.subhrajyoti.guardian;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.subhrajyoti.guardian.Services.MyService;

public class MyReceiver extends BroadcastReceiver {

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        SharedPreferences sharedPreferences = context.getSharedPreferences("Guardian", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("switch",false))
            context.startService(new Intent(context,MyService.class));

    }
}
