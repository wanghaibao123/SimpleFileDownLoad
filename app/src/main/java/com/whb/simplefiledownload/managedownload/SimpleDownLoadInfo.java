package com.whb.simplefiledownload.managedownload;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import org.greenrobot.greendao.annotation.Entity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by whb on 2018/10/16.
 * Email:18720982457@163.com
 */
@Entity
public class SimpleDownLoadInfo {
    public static final int WAITE = 1;
    public static final int DOWNLOADING = 2;
    public static final int PAUSE = 3;
    public static final int FINISH = 4;
    public static final int CANCEL = 5;
    public static final int ERROR = 6;
    @Id(autoincrement = true)
    private Long id;
    private String primaryKey;
    private String url;
    private String title;
    private float progress;
    private String progressText;
    private String savePath;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({WAITE, DOWNLOADING, PAUSE, FINISH, CANCEL, ERROR})
    public @interface DownloadTypeDef {
    }

    @DownloadTypeDef
    private int state;

    public SimpleDownLoadInfo(String url, String title, String savePath) {
        primaryKey = UUID.randomUUID().toString();
        this.url = url;
        this.title = title;
        this.savePath = savePath;
    }

    public SimpleDownLoadInfo(String primaryKey, String url, String title, String savePath) {
        this.primaryKey = primaryKey;
        this.url = url;
        this.title = title;
        this.savePath = savePath;
    }

    @Generated(hash = 637963368)
    public SimpleDownLoadInfo(Long id, String primaryKey, String url, String title,
            float progress, String progressText, String savePath, int state) {
        this.id = id;
        this.primaryKey = primaryKey;
        this.url = url;
        this.title = title;
        this.progress = progress;
        this.progressText = progressText;
        this.savePath = savePath;
        this.state = state;
    }

    @Generated(hash = 944218572)
    public SimpleDownLoadInfo() {
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProgressText() {
        return progressText == null ? "" : progressText;
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }

    public String getSavePath() {
        return savePath == null ? "" : savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }


    public int getState() {
        return state;
    }

    public void setState(@DownloadTypeDef int state) {
        this.state = state;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getPrimaryKey() {
        return primaryKey == null ? "" : primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
