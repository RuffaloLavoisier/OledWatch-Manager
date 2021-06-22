package com.polkapolka.bluetooth.le;

import android.annotation.TargetApi;
import android.app.Notification;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

public class MyNotificationListener extends NotificationListenerService {
    public final static String TAG = "MyNotificationListener";

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);

        Log.d(TAG, "onNotificationRemoved ~ " +
                " packageName: " + sbn.getPackageName() +
                " id: " + sbn.getId());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Notification notification = sbn.getNotification();
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString(Notification.EXTRA_TITLE);
        CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
        CharSequence subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);
     //   Icon smallIcon = notification.getSmallIcon();
     //   Icon largeIcon = notification.getLargeIcon();

        switch (sbn.getPackageName()){
            case "com.kakao.talk":
                if(sbn.getId()==2 && subText==null) {
                    Toast.makeText(this, "kakao msg : " + title + " / " + text, Toast.LENGTH_SHORT).show();
                }
                break;
            case "com.samsung.android.incallui":
                Toast.makeText(this, "call : "+ title, Toast.LENGTH_SHORT).show();
                break;
            case "com.samsung.android.messaging":
                Toast.makeText(this, "msg : "+title+" / "+text, Toast.LENGTH_SHORT).show();
                break;
        }


        Log.d("MSGBug" , "onNotificationPosted ~ " +
                " packageName: " + sbn.getPackageName() +
                " id: " + sbn.getId() +
                " postTime: " + sbn.getPostTime() +
                " title: " + title +
                " text : " + text +
                " subText: " + subText);
    }

}