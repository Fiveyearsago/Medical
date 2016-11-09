package com.jy.medical;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;

import com.baidu.mapapi.SDKInitializer;
import com.jy.medical.greendao.manager.DaoManager;
import com.jy.medical.greendao.gen.DaoSession;
import com.jy.medical.greendao.util.DBUtils;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.LocalImageHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.pgyersdk.crash.PgyCrashManager;

import org.xutils.x;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 * Created by songran on 16/9/22.
 */
public class MedicalApplication extends Application {
    private List<AppCompatActivity> activityList = new LinkedList<>();
    private static MedicalApplication instance;
    private Display display;
//    private MedicalApplication(){ }
    //单例模式中获取唯一的MyApplication实例
    public static MedicalApplication getInstance() {
        if(null == instance) {
            instance = new MedicalApplication();
        }
        return instance;
    }
    //添加Activity到容器中
    public void addActivity(AppCompatActivity activity)  {
        activityList.add(activity);
    }
    public void finishActivity(Class<?> cls){
        for (AppCompatActivity activity : activityList) {
            if(activity.getClass().equals(cls) ){
                finishActivity(activity);
            }
        }
    }
    public void finishActivity(AppCompatActivity activity)  {
        if(activity!=null){
            activityList.remove(activity);
            activity.finish();
        }
    }

    //遍历所有Activity并finish
    public void exit(){
        for(AppCompatActivity activity:activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);
        SDKInitializer.initialize(this);
        instance=this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        DaoUtils.init(getApplicationContext());
        DaoManager.init(getApplicationContext());
        DaoManager daoMaster = DaoManager.getInstance();
        DaoSession session = daoMaster.getDaoSession();
        DBUtils.getInstance().setDaoSession(session);

        initImageLoader(this);
        //本地图片辅助类初始化
        LocalImageHelper.init(instance);
        if (display == null) {
            WindowManager windowManager = (WindowManager)
                    getSystemService(Context.WINDOW_SERVICE);
            display = windowManager.getDefaultDisplay();
        }

    }
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 4);
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(100 * 1024 * 1024); // 100 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //修改连接超时时间5秒，下载超时时间5秒
        config.imageDownloader(new BaseImageDownloader(instance, 5 * 1000, 5 * 1000));
        //		config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
    public String getCachePath() {
        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = getExternalCacheDir();
        else
            cacheDir = getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir.getAbsolutePath();
    }
    /**
     * @return
     * @Description： 获取当前屏幕1/4宽度
     */
    public int getQuarterWidth() {
        return display.getWidth() / 4;
    }
}
