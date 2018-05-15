package com.example.jinkejinfuapplication.base;

/**
 * Created by naihe on 2017/12/15.
 */

public class AppStatusManager {
    public int appStatus= AppStatusConstant.STATUS_FORCE_KILLED; //APP状态初始值为没启动不在前台状态
    public static AppStatusManager appStatusManager;
    private AppStatusManager() {
    }
    public static AppStatusManager getInstance() {
        if(appStatusManager==null){
            appStatusManager=new AppStatusManager();
        }
        return appStatusManager;
    }
    public int getAppStatus() {
        return appStatus;
    }
    public void setAppStatus(int appStatus) {
        this.appStatus= appStatus;
    }

}
