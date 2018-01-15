package com.longfor.channelmanager.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2017/12/27
 * @function:
 */
public class DatabaseManager {
    DaoSession mDaoSession = null;
    UserProfileDao mDao = null;
    SearchHistoryDao mSearchHistoryDao = null;


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
        mSearchHistoryDao = mDaoSession.getSearchHistoryDao();
//        mSearchHistoryDao.createTable(mDaoSession.getDatabase(), true);
    }

    public final UserProfileDao getDao() {
        return mDao;
    }
    public final SearchHistoryDao getmSearchHistoryDao() {
        return mSearchHistoryDao;
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
    public static String getProjectId() { return getUserProfile().getProjectId()+"";}
    public static void updateProject(String projectId, String projectName) {
        UserProfile userProfile = getUserProfile();
        userProfile.setProjectId(projectId);
        userProfile.setProjectName(projectName);
        getInstance().getDao().update(userProfile);
    }

    /**
     * 获取查询记录
     * @param roleType 职位类型
     * @return
     */
    public static List<String> getSearchHistorys(String roleType) {
        List<String> list = new ArrayList<>();
        List<SearchHistory> lists = getInstance().getmSearchHistoryDao().queryBuilder().where(SearchHistoryDao.Properties.RoleType.eq(roleType)).list();
        if(lists != null) {
            for(SearchHistory history : lists) {
                list.add(history.getHistory());
            }
        }
        return list;
    }

    /**
     * 更新查询记录
     * @param roleType
     * @param history
     */
    public static void updateHistory(String roleType, String history) {

        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setRoleType(roleType);
        searchHistory.setHistory(history);
        SearchHistoryDao dao = getInstance().getmSearchHistoryDao();
        SearchHistory searchHistory1 = dao.queryBuilder().where(SearchHistoryDao.Properties.RoleType.eq(roleType),SearchHistoryDao.Properties.History.eq(history)).unique();

        if(searchHistory1 != null) {
            dao.delete(searchHistory1);
        }
        else {
            List<SearchHistory> lists = dao.queryBuilder().where(SearchHistoryDao.Properties.RoleType.eq(roleType)).list();
            if(lists != null &&lists.size() > 10) {
                dao.delete(lists.get(0));
            }
        }
        dao.insert(searchHistory);
    }

    /**
     * 清除该职位下的所有数据
     * @param roleType
     */
    public static void clearHistory(String roleType) {
        SearchHistoryDao dao = getInstance().getmSearchHistoryDao();
        List<SearchHistory> histories = dao.queryBuilder().where(SearchHistoryDao.Properties.RoleType.eq(roleType)).list();
        if(histories != null) {
            dao.deleteInTx(histories);
        }
    }
}
