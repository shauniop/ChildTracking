package com.child.tracking.system;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MyService extends Service {
    String str;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Started",Toast.LENGTH_LONG).show();
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
        return START_STICKY;
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

    @Override
    public void onDestroy() {
        Toast.makeText(this,"Destroy",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
