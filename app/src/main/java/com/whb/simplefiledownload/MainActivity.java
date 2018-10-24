package com.whb.simplefiledownload;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.whb.simplefiledownload.basedownload.DownloadListner;
import com.whb.simplefiledownload.managedownload.SimpleDownloadManage;
import com.whb.simplefiledownload.ui.BatchActivity;
import com.whb.simplefiledownload.ui.BatchAdapter;

public class MainActivity extends AppCompatActivity {


    SimpleDownloadManage mDownloadManager;
    String wechatUrl = "http://dldir1.qq.com/weixin/android/weixin657android1040.apk";
    String qqUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDownloads();
    }


    private void initDownloads() {

    }

    /**
     * 初始化View控件
     */
    private void initViews() {


    }



    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        if (!checkPermission(permission)) {//针对android6.0动态检测申请权限
            if (shouldShowRationale(permission)) {
                showMessage("需要权限跑demo哦...");
            }
            ActivityCompat.requestPermissions(this, new String[]{permission}, 1000);
        }
    }

    /**
     * 显示提示消息
     *
     * @param msg
     */
    private void showMessage(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 检测用户权限
     *
     * @param permission
     * @return
     */
    protected boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 是否需要显示请求权限的理由
     *
     * @param permission
     * @return
     */
    protected boolean shouldShowRationale(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
    }

    public void onSinkClick(View view) {

    }

    public void onBatchClick(View view) {
        startActivity(new Intent(this, BatchActivity.class));
    }
}
