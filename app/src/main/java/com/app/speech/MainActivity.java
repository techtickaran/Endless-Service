package com.app.speech;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import net.gotev.speech.Speech;

public class MainActivity extends AppCompatActivity {
    SpeechReciever speechReciever;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        Speech.init(getApplicationContext(), getPackageName());
                        Intent intent = new Intent(MainActivity.this, GoogleSTT.class);
                        intent.setAction(GoogleSTT.ACTION_START_FOREGROUND_SERVICE);
                        startService(intent);
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();


    }
    @Override
    protected void onResume() {

        speechReciever = new SpeechReciever();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GoogleSTT.MY_ACTION);
        registerReceiver(speechReciever, intentFilter);
        super.onResume();
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(speechReciever);
//    }

    private class SpeechReciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            String datapassed = arg1.getStringExtra("DATAPASSED").trim();

            Log.e("datapass", "" + datapassed);

            if (datapassed.toLowerCase().contains("help")) {
                Toast.makeText(MainActivity.this,"Done",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

//    @Override
//    protected void onDestroy() {
//        stopService(mServiceIntent);
//        Log.i("MAINACT", "onDestroy!");
//        super.onDestroy();
//
//    }
