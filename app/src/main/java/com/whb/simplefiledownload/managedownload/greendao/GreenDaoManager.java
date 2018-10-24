package com.whb.simplefiledownload.managedownload.greendao;

import android.content.Context;

public class GreenDaoManager {
    private static GreenDaoManager mInstance;
    Context context;
    DaoSession daoSession;
    //是否已经初始化
    private boolean isInit = false;
    private GreenDaoManager(Context context) {
        this.context = context;
    }
    public static synchronized GreenDaoManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }
    public DaoSession getDaoSession(){
        //如果没有初始化，进行初始化
        if (!isInit){
            GreenDaoHelper helper = new GreenDaoHelper(context.getApplicationContext());
            daoSession = helper.initDao();
        }
        return daoSession;
    }
}
