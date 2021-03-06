package com.polkapolka.bluetooth.le;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyNotificationListener extends NotificationListenerService {
    public final static String TAG = "MyNotificationListener";
    public static String title;
    public static CharSequence text;

    final static String folder_name = Environment.getRootDirectory().getAbsolutePath()+"/"+"TestLog";
    final static String filename = "logfile.txt";


    // 1 : kakao, 2 : call, 3 : msg
    public static int send_msg;

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        send_msg = 0 ;
        DeviceControlActivity.makeChange();
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

        // Output Toast message
        switch (sbn.getPackageName()){
            case "com.kakao.talk":
                if(sbn.getId()==2 && subText==null) {   //
                    Toast.makeText(this, "kakao msg : " +subText+ " : "+ title + " -> " + text, Toast.LENGTH_LONG).show();
                    send_msg=1;
                    DeviceControlActivity.makeChange();
                }
                break;
            case "com.samsung.android.incallui":
                Toast.makeText(this, "call : "+ title, Toast.LENGTH_SHORT).show();
                send_msg=2;
                DeviceControlActivity.makeChange();

                break;
            case "com.samsung.android.messaging":
                Toast.makeText(this, "msg : "+ title +" / "+ text, Toast.LENGTH_SHORT).show();
                send_msg=3;
                DeviceControlActivity.makeChange();
                break;
        }
        // Log Output


        Log.d("MSGBug" , "onNotificationPosted ~ " +
                " packageName: " + sbn.getPackageName() + //
                " id: " + sbn.getId() +
                " postTime: " + sbn.getPostTime() +
                " title: " + title + //
                " text : " + text + //
                " subText: " + subText ); //

        @SuppressLint("SimpleDateFormat") String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        @SuppressLint("SimpleDateFormat") String Today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String msg_content=now+" "+sbn.getPackageName()+" "+subText+" // "+title+" // "+text+"\n\n";

        File RootDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +"/secret");
        File DateDirectory = new File(RootDirectory+"/"+Today);
        File PackDirectory = new File(DateDirectory +"/"+sbn.getPackageName());
        File TargetPathFile;
        if (sbn.getPackageName().equals("com.kakao.talk") && sbn.getId()==2){
            if(subText==null) {
                  TargetPathFile = new File(PackDirectory, "/" + title + ".txt");//private
            }else {
                  TargetPathFile = new File(PackDirectory, "/" + subText + ".txt");//room
            }
        }
        else {
              TargetPathFile = new File(PackDirectory ,"/LogFile.txt");//etc
        }

        if ( !RootDirectory.exists() ) {
            RootDirectory.mkdirs();
        } if ( !DateDirectory.exists() ) {
            DateDirectory.mkdirs();
        } if ( !PackDirectory.exists() ) {
            PackDirectory.mkdirs();
        }

        try {
            FileWriter out = new FileWriter(TargetPathFile,true);
            out.write(msg_content);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}