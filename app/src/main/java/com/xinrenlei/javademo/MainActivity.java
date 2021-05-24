package com.xinrenlei.javademo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xinrenlei.javademo.androidq.StorageActivity;
import com.xinrenlei.javademo.behavior.BehaviorActivity;
import com.xinrenlei.javademo.fragment.TestFragmentActivity;
import com.xinrenlei.javademo.hook.HookActivity;
import com.xinrenlei.javademo.hook.HookHelper2;
import com.xinrenlei.javademo.moveitem.MoveItemActivity;
import com.xinrenlei.javademo.permission.annotation.Permission;
import com.xinrenlei.javademo.permission.annotation.PermissionCancel;
import com.xinrenlei.javademo.permission.annotation.PermissionDenied;
import com.xinrenlei.javademo.stylelayoutmanager.CardActivity;
import com.xinrenlei.javademo.utils.skin.SkinManager;

public class MainActivity extends AppCompatActivity {

    private MyInstallReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //小米手机监听不到卸载回调，不知道google原生手机是否能监听的到
        receiver = new MyInstallReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme("package");
        this.registerReceiver(receiver, filter);

        try {
            getPackageManager().getPackageInfo("", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void changeSkin(View view) {
        SkinManager.getInstance().loadSkin("/data/data/com.xinrenlei.javademo/skin/skin-debug.apk");
    }

    public void restore(View view) {
        SkinManager.getInstance().loadSkin(null);
    }

    public void cardGesture(View view) {
        startActivity(new Intent(this, CardActivity.class));
    }

    public void moveItem(View view) {
        startActivity(new Intent(this, MoveItemActivity.class));
    }

    public void behavior(View view) {
        startActivity(new Intent(this, BehaviorActivity.class));
    }

    public void fragmentLife(View view) {
        startActivity(new Intent(this, TestFragmentActivity.class));
    }

    public void storage(View view) {
        startActivity(new Intent(this, StorageActivity.class));
    }

    public void applyPermissions(View view) {
        testRequest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    public void startNoStartActivity(View view) {
        HookHelper2.hookAMSAidl();
        HookHelper2.hookHandler();
        Intent intent = new Intent(this, HookActivity.class);
        startActivity(intent);
    }

    private class MyInstallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {// install
                String packageName = intent.getDataString();
                Toast.makeText(context, "安装" + packageName, Toast.LENGTH_LONG).show();
            }
            if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) { // uninstall
                String packageName = intent.getDataString();
                Toast.makeText(context, "卸载" + packageName, Toast.LENGTH_LONG).show();
            }
        }
    }

    // 申请权限  函数名可以随意些
    @Permission(value = Manifest.permission.READ_EXTERNAL_STORAGE, requestCode = 200)
    public void testRequest() {
        Toast.makeText(this, "权限申请成功...", Toast.LENGTH_SHORT).show();
        // 100 行
    }

    // 权限被取消  函数名可以随意些
    @PermissionCancel
    public void testCancel() {
        Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
    }

    // 多次拒绝，还勾选了“不再提示”
    @PermissionDenied
    public void testDenied() {
        Toast.makeText(this, "权限被拒绝(用户勾选了 不再提示)，注意：你必须要去设置中打开此权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }
}