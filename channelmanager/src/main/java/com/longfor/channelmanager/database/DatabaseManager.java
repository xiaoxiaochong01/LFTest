package com.longfor.channelmanager.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2017/12/27
 * @function:
 */
public class DatabaseManager {
    DaoSession mDaoSession = null;
    UserProfileDao mDao = null;


    private DatabaseManager() {

    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    private void initDao(Context context) {
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(context, "channel_manager_ec.db");
        final Database db = openHelper.getWritableDb();

        mDaoSession = new DaoMaster(db).newSession();
        mDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao() {
        return mDao;
    }

    public static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    /**
     * 获取DataManager单例
     * @return
     */
    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    public static UserProfile getUserProfile() {

        List<UserProfile> list = getInstance().getDao().loadAll();
        UserProfile userProfile = null;
        if(list != null && list.size() > 0) {
            userProfile = list.get(list.size() - 1);
        }
        else {
            throw new NullPointerException("用户信息未保存异常");
        }

        return userProfile;
    }

    public static String getEmployeeId() {
        return getUserProfile().getEmployeeId()+"";
    }
}
