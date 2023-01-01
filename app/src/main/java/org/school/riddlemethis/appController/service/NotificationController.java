package org.school.riddlemethis.appController.service;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import org.school.riddlemethis.appController.AppController;
import org.school.riddlemethis.prefs.AppSharedPreferences;

public class NotificationController {
    private static final String TAG = "NotificationController";

    private boolean caseToService;

    private static NotificationController Instance;

    private ComponentName componentName ;
    private JobInfo jobInfo;
    private JobScheduler jobScheduler;

    int id_forService_optional = 123;

    public static NotificationController getInstance() {
        if (Instance == null) {
            Instance = new NotificationController();
        }
        return Instance;
    }

    public NotificationController() {
    }

    // MANGER
    public void notificationRunner() {
        setCaseToService(AppSharedPreferences.getInstance(AppController.getInstance()).getNotificationSwitchCase());

        if (isCaseToService()){
            Log.i(TAG, "notificationRunner: On&Ready2go");
            if (componentName == null) {
                Log.i(TAG, "notificationRunner: componentName is null Building inProgress");
                componentName = new ComponentName(AppController.getInstance(),NotificationJobService.class);
            }
            if (jobInfo == null){
                Log.i(TAG, "notificationRunner: jobInfo is null Building inProgress");
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                    jobInfo = new JobInfo.Builder(id_forService_optional,componentName)
                            //for testing im setting a 1000 ms and it works fine
                            //day 60 * 60 * 24
                            //day 86_400_000
//                            .setPeriodic(1000)
                            .setPeriodic(86_400_000)
                            .build();
                }else {
                    jobInfo = new JobInfo.Builder(id_forService_optional,componentName)
                            //for testing im setting a 1000 ms and it works fine
                            //day 60 * 60 * 24
                            //day 86_400_000
//                            .setMinimumLatency(1000)
                            .setMinimumLatency(86_400_000)
                            .build();
                }
            }
            if (jobScheduler == null){
                Log.i(TAG, "notificationRunner: jobScheduler is null Building inProgress");
                jobScheduler = (JobScheduler) AppController.getInstance().getSystemService(Context.JOB_SCHEDULER_SERVICE);

                int result = jobScheduler.schedule(jobInfo);
                if (result == JobScheduler.RESULT_SUCCESS){
                    Log.i(TAG, "notificationRunner: jobScheduled Successfully");
                }

            }
        }else {
            if (jobScheduler != null){
                jobScheduler.cancel(id_forService_optional);
                Log.i(TAG, "notificationRunner: jobScheduler-Destroy");
            }
            Log.i(TAG, "notificationRunner: userSetOff");
        }
    }

    //Setter and Getter
    public boolean isCaseToService() {
        return caseToService;
    }

    public void setCaseToService(boolean caseToService) {
        this.caseToService = caseToService;
    }

}
