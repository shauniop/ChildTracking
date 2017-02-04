package com.example.shaunkollannur.childtracking;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String str;
    Intent in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        Pusher pusher = new Pusher("73f50b06535941848a73", options);

        Channel channel = pusher.subscribe("my-channel");

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                System.out.println(data);
                JSONObject jObj = null;
                try {
                    jObj = new JSONObject(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    str="From:- ";
                    str+=jObj.getString("name");
                    str+="   Message:- ";
                    str+=jObj.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                addNotification(str);

            }
        });
        pusher.connect();
    }
    public void startSer(View v){
        in=new Intent(getBaseContext(),MyService.class);
        startService(in);
    }
    public void stopSer(View v){
        stopService(in);
    }
    public void addNotification(String s) {
        Bitmap bmp= BitmapFactory.decodeResource(this.getResources(),R.drawable.icon);
        NotificationCompat.BigPictureStyle style=new NotificationCompat.BigPictureStyle();
        style.setBigContentTitle("Child Tracker");
        style.setBigContentTitle(s);
        style.bigPicture(bmp);
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_menu_save)
                        .setContentTitle("Notifications Example")
                        .setContentText("Check the app for further details")
                        .setAutoCancel(true)
                        .setStyle(style);


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }

}
