package com.child.tracking.system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    String str;
    Intent in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("450");
    }
    public void startSer(View v){
        in=new Intent(getBaseContext(), com.child.tracking.system.MyService.class);
        startService(in);
    }
    public void stopSer(View v){
        stopService(in);
    }


}
