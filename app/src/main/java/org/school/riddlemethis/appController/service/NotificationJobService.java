package org.school.riddlemethis.appController.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import org.school.riddlemethis.R;
import org.school.riddlemethis.appController.AppController;
import org.school.riddlemethis.views.SplashActivity;

public class NotificationJobService extends JobService {
    private static final String TAG = "NotificationJobService";
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(TAG, "onStartJob JOB-STARTED");
        Log.i(TAG, "onStartJob: Notification Is On Now");
//        displayNotification();
        //i shouldn't typed line 23 cuz the ( if statement ) RUNS whats in it of Methods that returns
        if (displayNotification()){
            jobFinished(jobParameters,true);
        }
        return false;
        // i shouldn't but True unless i write a code big and need Thread to run ... so >if so i tell the system {return true} ..which means i have a running code don't end the service on your mind //As you know Thread has a deferring time in compilation so might be destroyed before it do her preps
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }


    public static final String CHANNEL_ID = "Riddle_CHANNELNAME";

    public boolean displayNotification(){
        //first call the channel to be build
        channelManger();
        realNotification();
        return true;
    }
    public void channelManger(){
        //Every Channel Must Have An ID
        //      like Messages -> CH_MESSAGES -for an example
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Riddles Notification", NotificationManager.IMPORTANCE_DEFAULT);
            //+ if you want a description =>
            channel.setDescription("Riddle App DailyReminder Notification");
            //LASTLY CREATE THE CHANNEL

            // NotificationManager notificationManager = getSystemService(NotificationManager.class);
            //TODO When Error  use line 31 instead of 33 --in areal Activity class
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }else {
                Log.e("NotificationManger", "channelManger: THERE WAS AN ERROR IN N_M=NULL");
            }
        }
    }
    private void realNotification(){
        //give the Activity Context than // CHANNEL were it have this nonfiction
        //Todo must have an activity class Context
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);

        //NOW BUILD THE NOTIFICATION
        builder.setSmallIcon(R.drawable.app_logo_modified);
        builder.setContentTitle("Riddles are waiting For You");
        builder.setContentText("ComeBack and solve them riddles");
        //for important notification --we use next line
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // for ACTION you can do like =>
        //note replace every word in this class under name context WHIT (THIS) in any activity you use
        Intent intent = new Intent(AppController.getInstance(), SplashActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(AppController.getInstance(),0,intent,0);

        //under test
        builder.addAction(R.drawable.app_logo_modified,"ComeBack",pendingIntent);
        //this same as action but the whole nonfiction clickable
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(AppController.getInstance());
        int NotificationID_optional = 1;
        notificationManagerCompat.notify(NotificationID_optional,builder.build());

    }
}
