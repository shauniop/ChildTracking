package com.example.shaunkollannur.childtracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String str;
    Intent in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void startSer(View v){
        in=new Intent(getBaseContext(),MyService.class);
        startService(in);
    }
    public void stopSer(View v){
        stopService(in);
    }


}
