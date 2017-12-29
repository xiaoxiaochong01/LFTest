package com.longfor.channelmanager.login;

import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.channelmanager.database.UserProfile;
import com.longfor.channelmanager.login.bean.LoginBean;
import com.longfor.core.app.AccountManager;
import com.longfor.core.utils.log.LogUtils;

import java.util.List;

import static com.longfor.channelmanager.database.DatabaseManager.getInstance;

/**
 * @author: tongzhenhua
 * @date: 2017/12/27
 * @function:
 */
public class LoginHandler {
    public static void loginSucess(LoginBean.DataBean dataBean) {
        if(dataBean == null) {
            LogUtils.e("LoginHandler", "databean=null");
            return;
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setEmployeeId(dataBean.getEmployeeId());
        userProfile.setMobilePhone(dataBean.getMobilePhone());
        userProfile.setRoleId(dataBean.getRoleId());
        userProfile.setSubRoleId(dataBean.getSubRoleId());
        if(dataBean.getProject() != null ) {
            userProfile.setProjectId(dataBean.getProject().getProjectId());
            userProfile.setProjectName(dataBean.getProject().getProjectName());
        }
        getInstance().getDao().update(userProfile);
        AccountManager.setSignState(true);
        List<UserProfile> userProfileList = DatabaseManager.getInstance().getDao().loadAll();
        for(UserProfile userProfile1 : userProfileList) {
            userProfile.toString();
        }
    }

    public static void logout() {
        getInstance().getDao().deleteAll();
        AccountManager.setSignState(false);
    }
}
