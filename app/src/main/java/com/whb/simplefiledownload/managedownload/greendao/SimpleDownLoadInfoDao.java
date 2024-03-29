package com.whb.simplefiledownload.managedownload.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.whb.simplefiledownload.managedownload.SimpleDownLoadInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SIMPLE_DOWN_LOAD_INFO".
*/
public class SimpleDownLoadInfoDao extends AbstractDao<SimpleDownLoadInfo, Long> {

    public static final String TABLENAME = "SIMPLE_DOWN_LOAD_INFO";

    /**
     * Properties of entity SimpleDownLoadInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PrimaryKey = new Property(1, String.class, "primaryKey", false, "PRIMARY_KEY");
        public final static Property Url = new Property(2, String.class, "url", false, "URL");
        public final static Property Title = new Property(3, String.class, "title", false, "TITLE");
        public final static Property Progress = new Property(4, float.class, "progress", false, "PROGRESS");
        public final static Property ProgressText = new Property(5, String.class, "progressText", false, "PROGRESS_TEXT");
        public final static Property SavePath = new Property(6, String.class, "savePath", false, "SAVE_PATH");
        public final static Property State = new Property(7, int.class, "state", false, "STATE");
    }


    public SimpleDownLoadInfoDao(DaoConfig config) {
        super(config);
    }
    
    public SimpleDownLoadInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SIMPLE_DOWN_LOAD_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PRIMARY_KEY\" TEXT," + // 1: primaryKey
                "\"URL\" TEXT," + // 2: url
                "\"TITLE\" TEXT," + // 3: title
                "\"PROGRESS\" REAL NOT NULL ," + // 4: progress
                "\"PROGRESS_TEXT\" TEXT," + // 5: progressText
                "\"SAVE_PATH\" TEXT," + // 6: savePath
                "\"STATE\" INTEGER NOT NULL );"); // 7: state
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SIMPLE_DOWN_LOAD_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SimpleDownLoadInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String primaryKey = entity.getPrimaryKey();
        if (primaryKey != null) {
            stmt.bindString(2, primaryKey);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(3, url);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
        stmt.bindDouble(5, entity.getProgress());
 
        String progressText = entity.getProgressText();
        if (progressText != null) {
            stmt.bindString(6, progressText);
        }
 
        String savePath = entity.getSavePath();
        if (savePath != null) {
            stmt.bindString(7, savePath);
        }
        stmt.bindLong(8, entity.getState());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SimpleDownLoadInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String primaryKey = entity.getPrimaryKey();
        if (primaryKey != null) {
            stmt.bindString(2, primaryKey);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(3, url);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
        stmt.bindDouble(5, entity.getProgress());
 
        String progressText = entity.getProgressText();
        if (progressText != null) {
            stmt.bindString(6, progressText);
        }
 
        String savePath = entity.getSavePath();
        if (savePath != null) {
            stmt.bindString(7, savePath);
        }
        stmt.bindLong(8, entity.getState());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SimpleDownLoadInfo readEntity(Cursor cursor, int offset) {
        SimpleDownLoadInfo entity = new SimpleDownLoadInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // primaryKey
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // url
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // title
            cursor.getFloat(offset + 4), // progress
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // progressText
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // savePath
            cursor.getInt(offset + 7) // state
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SimpleDownLoadInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPrimaryKey(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTitle(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setProgress(cursor.getFloat(offset + 4));
        entity.setProgressText(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSavePath(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setState(cursor.getInt(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SimpleDownLoadInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SimpleDownLoadInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SimpleDownLoadInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
