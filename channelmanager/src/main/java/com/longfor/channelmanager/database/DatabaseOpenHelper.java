package com.longfor.channelmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author: tongzhenhua
 * @date: 2017/12/27
 * @function:
 */
public class DatabaseOpenHelper extends DaoMaster.OpenHelper {
    public DatabaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
}
