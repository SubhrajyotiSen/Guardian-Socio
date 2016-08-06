package com.subhrajyoti.guardian;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MyService extends Service {

    private ShakeListener mShaker;
    private int count = 0;
    private GPSTracker gps;
    private DBController dbController;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        gps = new GPSTracker(getApplicationContext());
        dbController = new DBController(getApplicationContext());
        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        startForeground();
        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake()
            {
                count++;
                vibe.vibrate(100);
                if (count>=3) {
                    count = 0;
                    if (gps.canGetLocation())
                        sendMessage();
                    else
                        Toast.makeText(MyService.this, "Please switch on Location", Toast.LENGTH_SHORT).show();

                }

            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                count = 0;
                Log.d("Count","RESET");
                //Do something after 100ms
            }
        }, 5000);

        return Service.START_STICKY;
    }

    private void startForeground() {
        int ID = 1234;
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(getBaseContext());
        builder.setContentIntent(pendIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("CUSTOM MESSAGE");
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(false);
        builder.setContentTitle("Guardian");
        builder.setContentText("Watching over you");

        Notification notification = builder.build();

        startForeground(ID, notification);
    }

    private void sendMessage(){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<ContactModel> contactModels;
            try {
                contactModels = (ArrayList<ContactModel>) dbController.getAllContacts();
            }
            catch (Exception e){
                contactModels = new ArrayList<>();
            }
            for (ContactModel contactModel:contactModels)
                smsManager.sendTextMessage(contactModel.getPhone(), null, "HELP, I am in DANGER: \n My Coordinates are"+gps.getLatitude()+" , "+gps.getLongitude(), null, null);
            Toast.makeText(MyService.this, contactModels.size()+" messages sent", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(MyService.this, "Unable to send message", Toast.LENGTH_SHORT).show();
            Log.d("SMS",e.getLocalizedMessage());
        }

    }
}
