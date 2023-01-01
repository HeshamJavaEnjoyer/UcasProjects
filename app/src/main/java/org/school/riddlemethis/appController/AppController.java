package org.school.riddlemethis.appController;
import android.app.Application;

import org.school.riddlemethis.appController.service.NotificationController;
import org.school.riddlemethis.source_data.MusicController;

public class AppController extends Application {

    private static AppController Instance;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Instance == null) {
            Instance = this;
        }
        MusicController.getInstance().musicRunner();

        NotificationController.getInstance().notificationRunner();

    }

    public static AppController getInstance() {
        return Instance;
    }
}