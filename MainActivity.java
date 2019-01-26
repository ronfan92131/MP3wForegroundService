package com.doyen.fans.mp3Player;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.doyen.fans.mp3player.R;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MP3_ MainActivity";
    private static final  int REQUEST_CODE = 43;

    Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
    }

    public void selectFile(View v){
        Log.d(TAG, "selectFile");
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + " " + resultCode);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (data != null){
                uri = data.getData();
                Log.d(TAG, "onActivityResult: " + uri);
                Toast.makeText(this, "Uri: " + uri, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startService(View v){
        Log.d(TAG, "startService: ");

        Intent serviceIntent = new Intent(this, ForegroundService.class);
        if(uri != null) {
            serviceIntent.putExtra("URI", uri.toString());
        }else{
            serviceIntent.putExtra("URI", R.raw.c192);
        }
        startService(serviceIntent);
    }

    public void stopService(View v){
        Log.d(TAG, "stopService");
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}
