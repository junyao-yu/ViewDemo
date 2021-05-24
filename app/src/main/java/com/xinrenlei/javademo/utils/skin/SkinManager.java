package com.xinrenlei.javademo.utils.skin;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;

/**
 * Auth：yujunyao
 * Since: 2020/9/7 5:02 PM
 * Email：yujunyao@xinrenlei.net
 */

public class SkinManager extends Observable {

    private volatile static SkinManager instance;
    private Application mContext;
    private ApplicationActivityLifecycle applicationActivityLifecycle;

    public static void init(Application application) {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager(application);
                }
            }
        }
    }

    public static SkinManager getInstance() {
        return instance;
    }

    private SkinManager(Application application) {
        mContext = application;

        SkinPreference.init(application);
        SkinResources.init(application);

        applicationActivityLifecycle = new ApplicationActivityLifecycle(this);
        application.registerActivityLifecycleCallbacks(applicationActivityLifecycle);
        loadSkin(SkinPreference.getInstance().getSkin());
    }

    public void loadSkin(String skinPath) {
        if (TextUtils.isEmpty(skinPath)) {
            SkinPreference.getInstance().reset();
            SkinResources.getInstance().reset();
        } else {
            try {
                //宿主app的resources
                Resources hostResources = mContext.getResources();

                //反射创建AssetManager和Resource
                AssetManager assetManager = AssetManager.class.newInstance();
                //资源路径设置
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(assetManager, skinPath);

                //根据当前设备显示器信息 与配置 创建Resources
                Resources skinResources = new Resources(assetManager, hostResources.getDisplayMetrics(), hostResources.getConfiguration());

                //获取外部apk包名
                PackageManager mPm = mContext.getPackageManager();
                PackageInfo packageInfo = mPm.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
                String packageName = packageInfo.packageName;
                SkinResources.getInstance().applySkin(skinResources, packageName);

                SkinPreference.getInstance().setSkin(skinPath);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        setChanged();
        notifyObservers(null);
    }

}
