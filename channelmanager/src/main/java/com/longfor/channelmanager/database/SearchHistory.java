package com.longfor.channelmanager.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: tongzhenhua
 * @date: 2018/1/12
 * @function: 搜索记录实体类
 */
@Entity(nameInDb = "search_history")
public class SearchHistory {
    @Id(autoincrement = true)
    private Long id;
    private String roleType;
    private String history;
    public String getHistory() {
        return this.history;
    }
    public void setHistory(String history) {
        this.history = history;
    }
    public String getRoleType() {
        return this.roleType;
    }
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1048698203)
    public SearchHistory(Long id, String roleType, String history) {
        this.id = id;
        this.roleType = roleType;
        this.history = history;
    }
    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

}
