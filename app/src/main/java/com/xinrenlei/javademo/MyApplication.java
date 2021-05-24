package com.xinrenlei.javademo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Auth：yujunyao
 * Since: 2020/9/7 4:47 PM
 * Email：yujunyao@xinrenlei.net
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        SkinManager.init(this);

        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        Log.e("memory--->", mActivityManager.getMemoryClass() + "");//以M为单位--红米note8 pro 384M
        Log.e("memoryLarge--->", mActivityManager.getLargeMemoryClass() + ""); //512M
    }
}
