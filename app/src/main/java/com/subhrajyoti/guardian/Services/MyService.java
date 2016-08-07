package com.subhrajyoti.guardian.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.subhrajyoti.guardian.Activities.MainActivity;
import com.subhrajyoti.guardian.Db.DBController;
import com.subhrajyoti.guardian.Models.ContactModel;
import com.subhrajyoti.guardian.R;
import com.subhrajyoti.guardian.Utils.GPSTracker;
import com.subhrajyoti.guardian.Utils.ShakeListener;

import java.util.ArrayList;

public class MyService extends Service {

    private ShakeListener mShaker;
    private int count = 0;
    private GPSTracker gps;
    private DBController dbController;
    private AudioManager audioManager;
    private int originalVolume;
    private MediaPlayer mediaPlayer;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        gps = new GPSTracker(getApplicationContext());
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        dbController = new DBController(getApplicationContext());
        mediaPlayer= MediaPlayer.create(getApplicationContext(), R.raw.scream);
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
                    playMax();
                    if (gps.canGetLocation())
                        sendMessage();
                    else
                        Toast.makeText(MyService.this, "Please switch on Location", Toast.LENGTH_SHORT).show();

                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        count = 0;
                        Log.d("Count","RESET");
                    }
                }, 5000);

            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0);            }
        });

        return Service.START_NOT_STICKY;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mShaker.pause();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    private void playMax(){
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        mediaPlayer.start();
    }
}
