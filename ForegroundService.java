package com.doyen.fans.mp3Player;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.doyen.fans.mp3player.R;

import static com.doyen.fans.mp3Player.NotificationApp.CHANNEL_ID;

public class ForegroundService extends Service {
    public static final String TAG = "MP3_ ForegroundService";
    Uri uri = null;
    MediaPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0
                );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Serviee")
                .setContentIntent(pendingIntent)
                .build();


        //startService(notificationIntent);  // android os may kill it when in background over 1 min
        startForeground(1, notification);  //for long running service, even app is gone

        try{
            uri = Uri.parse(intent.getStringExtra("URI"));
        }catch (Exception e){
            Log.d(TAG, "uri not found");
        }
        Log.d(TAG, "onStartCommand uri: " + uri);
        startMP3Player();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
     //   stopSelf();
        stopMP3Player();
        Log.d(TAG, "onDestroy: stopSelf()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    private void startMP3Player() {
        Log.d(TAG, "startMP3Player");
        if (player == null){
            if (uri != null){
                Log.d(TAG, "startMP3Player uri");
                player = MediaPlayer.create(this, uri);
            }else {
                Log.d(TAG, "startMP3Player raw");
                player = MediaPlayer.create(this, R.raw.c192);
            }
        }
        player.setLooping(true);
        player.start();
    }

    private void stopMP3Player() {
        Log.d(TAG, "stopMP3Player");
        if (player != null){
            player.release();
            player = null;
            Log.d(TAG, "stopMP3Player: released");
            Toast.makeText(this, "MP3 player released", Toast.LENGTH_SHORT).show();
        }
    }
}
