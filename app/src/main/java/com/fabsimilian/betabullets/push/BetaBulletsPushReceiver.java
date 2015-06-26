package com.fabsimilian.betabullets.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.fabsimilian.betabullets.MainActivity;
import com.fabsimilian.betabullets.R;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by tafabs on 26.06.15.
 */
public class BetaBulletsPushReceiver extends ParsePushBroadcastReceiver {

    private static final int NOTIFICATION_ID = 1234 ;

    @Override
    protected Bitmap getLargeIcon(Context context, Intent intent) {
        return ((BitmapDrawable)context.getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String jsonData = intent.getExtras().getString("com.parse.Data");
            JSONObject json = new JSONObject(jsonData);

            String title = "BetaBullets";
            generateNotification(context, title, "test");

//            String message = "foo";
//            if(json.has("alert")) {
//                message = json.getString("alert");
//            }
//
//            if(message != null) {
//                generateNotification(context, title, message);
//            }
        } catch(Exception e) {
            Log.e("NOTIF ERROR", e.toString());
        }
    }
    String mMessage;
    private void generateNotification(Context context, String title, final String message) {
        mMessage = message;
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

        final NotificationManager mNotifM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(title == null) {
            title = context.getResources().getString(R.string.app_name);
        }



        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap())
                        .setContentTitle(title)
                        .setContentText(mMessage)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(mMessage))
//                        .addAction(0, "View", contentIntent)
                        .setAutoCancel(true)
                        .setDefaults(new NotificationCompat().DEFAULT_VIBRATE);
//                        .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.gong));

        mBuilder.setStyle(new NotificationCompat.BigTextStyle(mBuilder)
                .bigText(mMessage))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(getLargeIcon(context, intent))
//                .setContentTitle("SexGong")
                .setContentIntent(contentIntent);
        mBuilder.setContentIntent(contentIntent);
        final Notification notification = mBuilder.build();

        mNotifM.notify(NOTIFICATION_ID, notification);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMessage = "change message";
                mNotifM.notify(NOTIFICATION_ID, notification);
            }
        }, 2000);
//
//        final NotificationCompat.Builder mBuilder2 =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap())
//                        .setContentTitle(title)
//                        .setContentText(mMessage)
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                                .bigText(message))
////                        .addAction(0, "View", contentIntent)
//                        .setAutoCancel(true)
//                        .setDefaults(new NotificationCompat().DEFAULT_VIBRATE);
////                        .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.gong));
//
//        mBuilder.setStyle(new NotificationCompat.BigTextStyle(mBuilder)
//                .bigText("LALALALALALA"))
////                .setSmallIcon(R.drawable.app_push_android)
//                .setLargeIcon(getLargeIcon(context,intent))
////                .setContentTitle("SexGong")
//                .setContentIntent(contentIntent);
//        mBuilder.setContentIntent(contentIntent);
//        final Notification notification2 = mBuilder2.build();



    }


    /*
    public void Update(String apkurl){
        try {
            URL url = new URL(apkurl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            String PATH = Environment.getExternalStorageDirectory() + "/download/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "app.apk");
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();//till here, it works fine - .apk is download to my sdcard in download file

            Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(PATH + "app.apk"))
                    .setType("application/android.com.app");
            startActivity(promptInstall);//installation is not working

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Update error!", Toast.LENGTH_LONG).show();
        }
    }
    */

}
