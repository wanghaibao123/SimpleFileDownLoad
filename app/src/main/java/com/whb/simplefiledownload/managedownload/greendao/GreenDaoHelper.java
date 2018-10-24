package com.whb.simplefiledownload.managedownload.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * 封装Greendao数据库
 */

public class GreenDaoHelper {

    Context context;
    SqliteDevOpenHelper helper;
    SQLiteDatabase db;
    DaoMaster daoMaster;
    DaoSession daoSession;


    public GreenDaoHelper(Context context) {
        this.context = context;
    }

    public DaoSession initDao(){
        helper = new SqliteDevOpenHelper(context, "download.db", null);
        db= helper.getWritableDatabase();
        daoMaster= new DaoMaster(db);
        daoSession= daoMaster.newSession();
        return daoSession;
    }
}
