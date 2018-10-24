package com.whb.simplefiledownload.managedownload;

import android.content.Context;
import android.text.TextUtils;

import com.whb.simplefiledownload.basedownload.DownloadListner;
import com.whb.simplefiledownload.basedownload.DownloadTask;
import com.whb.simplefiledownload.basedownload.FilePoint;
import com.whb.simplefiledownload.managedownload.greendao.GreenDaoManager;
import com.whb.simplefiledownload.managedownload.greendao.SimpleDownLoadInfoDao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by whb on 2018/10/16.
 * Email:18720982457@163.com
 */
public class SimpleDownloadManage {
    private Context mContext;
    private int MAX_DOWNLOAD = 3;
    private static SimpleDownloadManage mInstance;
    private ConcurrentHashMap<String, DownloadTask> mDownloadTasks = new ConcurrentHashMap<>();//正在下载的
    private ConcurrentHashMap<String, DownloadListner> mSimpleCallBacks = new ConcurrentHashMap<>();//监听回调
    private List<SimpleDownLoadInfo> downloadInfos = new ArrayList<>();//所有的下载信息
    private SimpleDownLoadInfoDao daoSession;

    public SimpleDownloadManage(Context context) {
        this.mContext = context;
        daoSession = GreenDaoManager.getInstance(mContext).getDaoSession().getSimpleDownLoadInfoDao();
        initHistory();
    }

    public static SimpleDownloadManage getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SimpleDownloadManage.class) {
                if (mInstance == null) {
                    mInstance = new SimpleDownloadManage(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private void initHistory() {
        List<SimpleDownLoadInfo> loadInfos = daoSession.loadAll();
        for (SimpleDownLoadInfo loadInfo : loadInfos) {
            if (loadInfo.getState() == SimpleDownLoadInfo.DOWNLOADING) {
                loadInfo.setState(SimpleDownLoadInfo.PAUSE);
            }

        }
        if (loadInfos != null && loadInfos.size() > 0) {
            downloadInfos.addAll(loadInfos);
        }
    }

    /**
     * 添加任务
     *
     * @param filePoints
     */
    public void addDownload(List<DownParameter> filePoints) {
        for (DownParameter parameter : filePoints) {
            addDownloadSimple(new SimpleDownLoadInfo(parameter.getUrl(), parameter.getFileName(), parameter.getFilePath()));
        }
        createDownLoadTask();
    }

    /**
     * 添加任务
     *
     * @param parameter
     */
    public void addDownload(DownParameter parameter) {
        addDownloadSimple(new SimpleDownLoadInfo(parameter.getUrl(), parameter.getFileName(), parameter.getFilePath()));
        createDownLoadTask();
    }

    /**
     * 添加任务到队列中
     *
     * @param simpleDownLoadInfo
     */
    public void addDownloadSimple(SimpleDownLoadInfo simpleDownLoadInfo) {
        downloadInfos.add(simpleDownLoadInfo);
        daoSession.insert(simpleDownLoadInfo);
    }

    /**
     * 全部开始
     */
    public void startAll() {
        for (SimpleDownLoadInfo downLoadInfo : downloadInfos) {
            if (downLoadInfo.getState() != SimpleDownLoadInfo.FINISH) {
                downLoadInfo.setState(SimpleDownLoadInfo.WAITE);
            }
        }
        createDownLoadTask();
    }

    /**
     * 全部暂停
     */
    public void pauseAll() {
        for (SimpleDownLoadInfo downLoadInfo : downloadInfos) {
            if(downLoadInfo.getState() != SimpleDownLoadInfo.FINISH && downLoadInfo.getState() != SimpleDownLoadInfo.ERROR) {
                downLoadInfo.setState(SimpleDownLoadInfo.PAUSE);
            }
        }
        for (String key : mDownloadTasks.keySet()) {
            mDownloadTasks.get(key).pause();
        }
    }

    /**
     * 创建下载任务
     */
    public synchronized void createDownLoadTask() {
        if (mDownloadTasks.size() > MAX_DOWNLOAD) {
            return;
        }
        int count = MAX_DOWNLOAD - mDownloadTasks.size();
        for (int i = 0; i < count; i++) {
            for (SimpleDownLoadInfo downLoadInfo : downloadInfos) {
                if (downLoadInfo.getState() != SimpleDownLoadInfo.DOWNLOADING && downLoadInfo.getState() != SimpleDownLoadInfo.FINISH && downLoadInfo.getState() != SimpleDownLoadInfo.PAUSE) {
                    DownloadTask task = new DownloadTask(new FilePoint(downLoadInfo.getUrl(), downLoadInfo.getSavePath(), downLoadInfo.getTitle()), new SimpleCallBack(downLoadInfo.getPrimaryKey()));
                    downLoadInfo.setState(SimpleDownLoadInfo.DOWNLOADING);
                    task.start();
                    mDownloadTasks.put(downLoadInfo.getPrimaryKey(), task);
                    break;
                }
            }
        }
    }

    /**
     * 开始下载
     *
     * @param downLoadInfo
     */
    public void start(SimpleDownLoadInfo downLoadInfo) {
        if (mDownloadTasks.size() >= MAX_DOWNLOAD) {
            for (String key : mDownloadTasks.keySet()) {
                DownloadTask task = mDownloadTasks.get(key);
                if (task != null) {
                    task.pause();
                }
                break;
            }
        }
        DownloadTask task = new DownloadTask(new FilePoint(downLoadInfo.getUrl(), downLoadInfo.getSavePath(), downLoadInfo.getTitle()), new SimpleCallBack(downLoadInfo.getPrimaryKey()));
        downLoadInfo.setState(SimpleDownLoadInfo.DOWNLOADING);
        task.start();
        mDownloadTasks.put(downLoadInfo.getPrimaryKey(), task);
    }

    /**
     * 暂停下载
     *
     * @param
     */
    public void pause(SimpleDownLoadInfo downLoadInfo) {
        DownloadTask task = mDownloadTasks.get(downLoadInfo.getPrimaryKey());
        if (task != null) {
            task.pause();
        }
    }

    /**
     * 取消下载
     *
     * @param key
     */
    public void cancel(String key) {
        DownloadTask task = mDownloadTasks.get(key);
        if (task != null) {
            task.cancel();
            mDownloadTasks.remove(key);
        }
    }


    /**
     * 移除 任务
     * 并且开启下一个任务
     */
    public void removeDownloadTask(String key) {
        mDownloadTasks.remove(key);
        createDownLoadTask();
    }

    /**
     * 删除下载
     */
    public synchronized void delete(SimpleDownLoadInfo downLoadInfo) {
        cancel(downLoadInfo.getPrimaryKey());
        downloadInfos.remove(downLoadInfo);
        daoSession.delete(downLoadInfo);
        //TODO 文件删除
        createDownLoadTask();
    }

    /**
     * 删除全部
     */
    public void deleteAll() {
        Iterator<SimpleDownLoadInfo> it = downloadInfos.iterator();
        while (it.hasNext()) {
            SimpleDownLoadInfo x = it.next();
            cancel(x.getPrimaryKey());
            it.remove();
            daoSession.delete(x);
            //TODO 文件删除

        }
    }

    /**
     * 获取所有的下载任务
     *
     * @return
     */
    public List<SimpleDownLoadInfo> getDownloadInfos() {
        if (downloadInfos == null) {
            return new ArrayList<>();
        }
        return downloadInfos;
    }

    /**
     * 注册监听
     *
     * @param key
     * @param callBack
     */
    public void registerlisten(String key, DownloadListner callBack) {
        mSimpleCallBacks.put(key, callBack);
    }

    /**
     * 移除监听
     */
    public void removelisten(String key) {
        mSimpleCallBacks.remove(key);
    }


    /**
     * 监听回调
     */
    class SimpleCallBack implements DownloadListner {
        private String key;
        private long lasttime = 0;

        public SimpleCallBack(String key) {
            this.key = key;
        }

        @Override
        public void onFinished() {
            for (String back : mSimpleCallBacks.keySet()) {
                if (TextUtils.equals(key, back)) {
                    mSimpleCallBacks.get(back).onFinished();
                }
            }

            for (SimpleDownLoadInfo downLoadInfo : downloadInfos) {
                if (TextUtils.equals(downLoadInfo.getPrimaryKey(), key)) {
                    downLoadInfo.setState(SimpleDownLoadInfo.FINISH);
                    downLoadInfo.setProgress(1);
                    removeDownloadTask(key);
                    daoSession.update(downLoadInfo);
                }
            }

            for (String back : mSimpleCallBacks.keySet()) {
                if (TextUtils.equals(key, back)) {
                    mSimpleCallBacks.get(back).onProgress(1);
                }
            }

        }

        @Override
        public void onProgress(float progress) {
            long nowtime = System.currentTimeMillis();
            if ((nowtime - lasttime) < 800) { //配置回调时间
                return;
            }
            lasttime = nowtime;
            for (String back : mSimpleCallBacks.keySet()) {
                if (TextUtils.equals(key, back)) {
                    mSimpleCallBacks.get(back).onProgress(progress);
                }
            }

            for (SimpleDownLoadInfo downLoadInfo : downloadInfos) {
                if (TextUtils.equals(downLoadInfo.getPrimaryKey(), key)) {
                    downLoadInfo.setProgress(progress);
                    downLoadInfo.setState(SimpleDownLoadInfo.DOWNLOADING);
                    daoSession.update(downLoadInfo);
                }
            }
        }

        @Override
        public void onPause() {
            for (String back : mSimpleCallBacks.keySet()) {
                if (TextUtils.equals(key, back)) {
                    mSimpleCallBacks.get(back).onPause();
                }
            }

            for (SimpleDownLoadInfo downLoadInfo : downloadInfos) {
                if (TextUtils.equals(downLoadInfo.getPrimaryKey(), key)) {
                    downLoadInfo.setState(SimpleDownLoadInfo.PAUSE);
                    removeDownloadTask(key);
                    daoSession.update(downLoadInfo);
                }
            }
        }

        @Override
        public void onCancel() {
            for (String back : mSimpleCallBacks.keySet()) {
                if (TextUtils.equals(key, back)) {
                    mSimpleCallBacks.get(back).onCancel();
                }
            }
        }
    }


}

