package com.xinrenlei.javademo.permission;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.xinrenlei.javademo.permission.interfaces.IPermission;
import com.xinrenlei.javademo.permission.utils.PermissionUtils;

/**
 * Auth：yujunyao
 * Since: 2020/10/4 3:19 PM
 * Email：yujunyao@xinrenlei.net
 */

//专门权限处理的空白activity
public class PermissionActivity extends AppCompatActivity {

    // 定义权限处理的标记， ---- 接收用户传递进来的
    private final static String PARAM_PERMSSION = "param_permission";
    private final static String PARAM_PERMSSION_CODE = "param_permission_code";
    public final static int PARAM_PERMSSION_CODE_DEFAULT = -1;

    // 真正接收存储的变量
    private String[] permissions;
    private int requestCode;
    // 方便回调的监听  告诉外交 已授权，被拒绝，被取消
    private static IPermission iPermissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 我们就接收到了
        permissions = getIntent().getStringArrayExtra(PARAM_PERMSSION); // READ_EXTERNAL_STORAGE
        requestCode = getIntent().getIntExtra(PARAM_PERMSSION_CODE, PARAM_PERMSSION_CODE_DEFAULT);

        if (permissions == null && requestCode < 0 && iPermissionListener == null) {
            this.finish();
            return;
        }

        // 能够走到这里，就开始去检查，是否已经授权了
        boolean permissionRequest = PermissionUtils.hasPermissionRequest(this, permissions);
        if (permissionRequest) {
            // 通过监听接口，告诉外交，已经授权了
            iPermissionListener.agree();

            this.finish();
            return;
        }

        // 能够走到这里，就证明，还需要去申请权限
        ActivityCompat.requestPermissions(this, permissions, requestCode);

//        //检查权限
//        int result = ActivityCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE);
//
//        if (result != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission_group.STORAGE)) {
//                //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。如果用户在过去拒绝了权限请求，并在 权限请求系统对话框中选择了 Don't ask again 选项，此方法将返回 false。
//            }
//
//            //申请权限
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission_group.STORAGE}, 10);
//        }
    }

//    //回调
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    // 让此Activity不要有任何动画
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    // TODO 此权限申请专用的Activity ，对外暴露， static
    public static void requestPermissionAction(Context context, String[] permissions,
                                               int requestCode, IPermission iPermissionListener) {
        PermissionActivity.iPermissionListener = iPermissionListener;

        Intent intent = new Intent(context, PermissionActivity.class);

        // 效果
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Bundle bundle = new Bundle();
        bundle.putInt(PARAM_PERMSSION_CODE, requestCode);
        bundle.putStringArray(PARAM_PERMSSION, permissions);

        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
