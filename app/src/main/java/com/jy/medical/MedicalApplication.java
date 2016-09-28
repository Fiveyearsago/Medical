package com.jy.medical;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;

/**
 * Description
 * Created by songran on 16/9/22.
 */
public class MedicalApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);
    }
}
