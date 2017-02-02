package com.example.shaunkollannur.childtracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pusher.android.PusherAndroid;
import com.pusher.android.notifications.ManifestValidator;
import com.pusher.android.notifications.PushNotificationRegistration;
import com.pusher.android.notifications.tokens.PushNotificationRegistrationListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PusherAndroid pusher = new PusherAndroid("73f50b06535941848a73");
        PushNotificationRegistration nativePusher = pusher.nativePusher();
        try {
            nativePusher.registerFCM(this, new PushNotificationRegistrationListener() {
                @Override
                public void onSuccessfulRegistration() {
                    System.out.println("REGISTRATION SUCCESSFUL!!! YEEEEEHAWWWWW!");
                }

                @Override
                public void onFailedRegistration(int statusCode, String response) {
                    System.out.println(
                            "A real sad day. Registration failed with code " + statusCode +
                                    " " + response
                    );
                }
            });
        } catch (ManifestValidator.InvalidManifestException e) {
            e.printStackTrace();
        }
        nativePusher = pusher.nativePusher();
        nativePusher.subscribe("message");


    }
}
