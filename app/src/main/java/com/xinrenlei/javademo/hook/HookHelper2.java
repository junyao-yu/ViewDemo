package com.xinrenlei.javademo.hook;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Auth：yujunyao
 * Since: 2020/10/12 5:20 PM
 * Email：yujunyao@xinrenlei.net
 */

public class HookHelper2 {

    private static final String TAG = "HookHelper";

    private static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    public static void hookAMSAidl() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            hookIActivityTaskManager();
        } else {
            hookIActivityManager();
        }
    }


    private static void hookIActivityTaskManager() {
        Field singletonField = null;
        try {
            Class<?> activityManager = Class.forName("android.app.ActivityTaskManager");
            singletonField = activityManager.getDeclaredField("IActivityTaskManagerSingleton");
            singletonField.setAccessible(true);
            Object singleton = singletonField.get(null);
            //拿IActivityTaskManager对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            //原始的IActivityTaskManager
            final Object IActivityTaskManager = mInstanceField.get(singleton);

            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
            , new Class[]{Class.forName("android.app.IActivityTaskManager")}
            , new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            //偷梁换柱
                            Intent raw = null;
                            int index = -1;
                            if ("startActivity".equals(method.getName())) {
                                Log.i(TAG, "invoke: startActivity 启动准备");
                                for (int i = 0; i < args.length; i++) {
                                    if (args[i] instanceof Intent) {
                                        raw = (Intent) args[i];
                                        index = i;
                                    }
                                }
                                Log.i(TAG, "invoke: raw: " + raw);
                                Intent newIntent = new Intent();
                                newIntent.setComponent(new ComponentName("com.xinrenlei.javademo", StubActivity.class.getName()));
                                newIntent.putExtra(EXTRA_TARGET_INTENT, raw);
                                args[index] = newIntent;//这里欺骗系统，验证暂位Activity
                            }


                            return method.invoke(IActivityTaskManager, args);
                        }
                    });

            mInstanceField.set(singleton, proxy);

        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void hookIActivityManager() {
        Field singletonField = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Class<?> activityManager = Class.forName("android.app.ActivityManager");
                singletonField = activityManager.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class<?> actvityManager = Class.forName("android.app.ActivityManagerNative");
                singletonField = actvityManager.getDeclaredField("gDefault");
            }
            singletonField.setAccessible(true);
            Object singleton = singletonField.get(null);
            //拿IActivityManager对象
            Class<?> activityManager = Class.forName("android.util.Singleton");
            Field mInstanceField = activityManager.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            //原始的IActivityManager
            final Object rawIActivityManager = mInstanceField.get(singleton);
            //创建一个IActivityManager代理对象

            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                    , new Class[]{Class.forName("android.app.IActivityManager")}
                    , new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                                //偷梁换柱
                                //真正要启动的Activity目标
                                Intent raw = null;
                                int index = -1;
                                if ("startActivity".equals(method.getName())) {
                                    Log.i(TAG, "invoke: startActivity 启动准备");
                                    for (int i = 0; i < args.length; i++) {
                                        if (args[i] instanceof Intent) {
                                            raw = (Intent) args[i];
                                            index = i;
                                        }
                                    }
                                    Log.i(TAG, "invoke: raw: " + raw);
                                    Intent newIntent = new Intent();
                                    newIntent.setComponent(new ComponentName("com.xinrenlei.javademo", StubActivity.class.getName()));
                                    newIntent.putExtra(EXTRA_TARGET_INTENT, raw);
                                    args[index] = newIntent;//这里欺骗系统，验证暂位Activity
                                }

                                return method.invoke(rawIActivityManager, args);
                            }
                        });

            mInstanceField.set(singleton, proxy);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void hookHandler() {
        try {
            Class<?> atClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = atClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object sCurrentActivityThread = sCurrentActivityThreadField.get(null);
            //ActivityTread一个app进程只有一个获取他的mH
            Field mHField = atClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            final Handler mH = (Handler) mHField.get(sCurrentActivityThread);

            //获取mCallback
            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);

            mCallbackField.set(mH, new Handler.Callback() {

                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case 100://低版本api28以下是这个
                            break;
                        case 159:
                            Object obj = msg.obj;
                            Log.i(TAG, "handleMessage: obj=" + obj);
                            //android.app.servertransaction.ClientTransaction
                            try {
                                Field mActivityCallbackField = obj.getClass().getDeclaredField("mActivityCallbacks");
                                mActivityCallbackField.setAccessible(true);
                                List mActivityCallbacks = (List) mActivityCallbackField.get(obj);
                                Log.i(TAG, "handleMessage: mActivityCallbacks= " + mActivityCallbacks);
                                if (mActivityCallbacks.size() > 0) {
                                    String className = "android.app.servertransaction.LaunchActivityItem";
                                    if (mActivityCallbacks.get(0).getClass().getCanonicalName().equals(className)) {
                                        Object object = mActivityCallbacks.get(0);
                                        Field intentField = object.getClass().getDeclaredField("mIntent");
                                        intentField.setAccessible(true);
                                        Intent intent = (Intent) intentField.get(object);
                                        Intent targetIntent = intent.getParcelableExtra(EXTRA_TARGET_INTENT);
                                        intent.setComponent(targetIntent.getComponent());//这里把替换的再给换回来
                                    }
                                }

                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    mH.handleMessage(msg);
                    return true;
                }
            });

        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
