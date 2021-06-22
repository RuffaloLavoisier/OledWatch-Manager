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
    public static String title;
    public static CharSequence text;

    // 1 : kakao, 2 : call, 3 : msg
    public static int send_msg;

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);

        Log.d("bug", "onNotificationRemoved ~ " +
                " packageName: " + sbn.getPackageName() +
                " id: " + sbn.getId());
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Notification notification = sbn.getNotification();
        Bundle extras = sbn.getNotification().extras;
        title = extras.getString(Notification.EXTRA_TITLE);
        text = extras.getCharSequence(Notification.EXTRA_TEXT);
        CharSequence subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);
     //   Icon smallIcon = notification.getSmallIcon();
     //   Icon largeIcon = notification.getLargeIcon();

        switch (sbn.getPackageName()){
            case "com.kakao.talk":
                if(sbn.getId()==2 && subText==null) {
                    Toast.makeText(this, "kakao msg : " + title + " / " + text, Toast.LENGTH_SHORT).show();
                    send_msg=1;
                }
                break;
            case "com.samsung.android.incallui":
                Toast.makeText(this, "call : "+ title, Toast.LENGTH_SHORT).show();
                send_msg=2;
                break;
            case "com.samsung.android.messaging":
                Toast.makeText(this, "msg : "+ title +" / "+ text, Toast.LENGTH_SHORT).show();
                send_msg=3;
                break;
        }
        DeviceControlActivity.makeChange();
        Log.d("MSGBug" , "onNotificationPosted ~ " +
                " packageName: " + sbn.getPackageName() +
                " id: " + sbn.getId() +
                " postTime: " + sbn.getPostTime() +
                " title: " + title +
                " text : " + text +
                " subText: " + subText);
    }

}