package com.whb.simplefiledownload.managedownload;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.util.UUID;

/**
 * Created by whb
 */

public class DownParameter {
    private String fileName;//文件名
    private String url;//下载地址
    private String filePath;//下载目录

    public DownParameter(@NonNull String url) {
        this.url = url;
    }

    public DownParameter(@NonNull String filePath, String url) {
        this.filePath = filePath;
        this.url = url;
    }

    public DownParameter(@NonNull String url, String filePath, String fileName) {
        this.url = url;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public String getFileName() {

        return UUID.randomUUID().toString()+ (fileName == null ? getFileName(url) : fileName);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath == null ? getDefaultDirectory() : filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 默认下载目录
     *
     * @return
     */
    private String getDefaultDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "whb" + File.separator;

    }

    // 获取下载文件的名称
    public String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

}
