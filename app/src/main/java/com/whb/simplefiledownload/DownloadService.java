package com.whb.simplefiledownload;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.whb.simplefiledownload.managedownload.SimpleDownloadManage;

import java.util.List;

public class DownloadService extends Service {

    private static SimpleDownloadManage manager;
    private static final String CHANNEL_ID = "bj_download_channel_id";
    private static Context mContext;
    private static boolean hasStartedService;
    private static DownloadService customDownloadService;

    public DownloadService() {
    }

    /**
     * start 方式开启服务，保存全局的下载管理对象
     */
    public static SimpleDownloadManage getDownloadManager(Context context) {
        if (DownloadService.manager == null) {
            DownloadService.manager = SimpleDownloadManage.getInstance(context);
        }
        mContext = context.getApplicationContext();
        startService();
        return manager;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static boolean isServiceRunning(Context context) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = null;
        if (activityManager != null) {
            serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        }
        if (serviceList == null || serviceList.size() == 0) return false;
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(DownloadService.class.getName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    //真正开启service
    public static void startService() {
        if (mContext != null && !hasStartedService) {
            if (!DownloadService.isServiceRunning(mContext)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mContext.startForegroundService(new Intent(mContext, DownloadService.class));
                } else {
                    mContext.startService(new Intent(mContext, DownloadService.class));
                }
            }
            hasStartedService = true;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        customDownloadService = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "下载", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
            Notification notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("下载")
                    .setContentText("文件正在下载中")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            startForeground(1001, notification);
            Log.d("download", "onCreate startForeground");
        }
    }

    //隐藏通知
    public static void cancelNotification() {
        NotificationManager notificationManager = ((NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE));
        if (notificationManager != null) {
            notificationManager.cancel(1001);
        }
        if (customDownloadService != null) {
            customDownloadService.stopSelf();
        }
        hasStartedService = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
