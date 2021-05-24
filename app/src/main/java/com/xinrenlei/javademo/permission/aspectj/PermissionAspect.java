package com.xinrenlei.javademo.permission.aspectj;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.xinrenlei.javademo.permission.PermissionActivity;
import com.xinrenlei.javademo.permission.annotation.Permission;
import com.xinrenlei.javademo.permission.annotation.PermissionCancel;
import com.xinrenlei.javademo.permission.annotation.PermissionDenied;
import com.xinrenlei.javademo.permission.interfaces.IPermission;
import com.xinrenlei.javademo.permission.utils.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Auth：yujunyao
 * Since: 2020/10/4 10:24 PM
 * Email：yujunyao@xinrenlei.net
 */

@Aspect
public class PermissionAspect {

    //切点
    @Pointcut("execution(@com.xinrenlei.javademo.permission.annotation.Permission * *(..)) && @annotation(permission)")
    public void pointActionMethod(Permission permission) {
    }

    //切面
    @Around("pointActionMethod(permission)")
    public void aProceedingJoinPoint(final ProceedingJoinPoint point, Permission permission) throws Throwable {

        Context context = null;

        final Object thisObject = point.getThis();

        if (thisObject instanceof Context) {
            context = (Context) thisObject;
        } else if (thisObject instanceof Fragment) {
            context = ((Fragment) thisObject).getActivity();
        }

        // 判断是否为null
        if (null == context || permission == null) {
            throw new IllegalAccessException("null == context || permission == null is null");
        }

        // 调用 空白的 Activity 申请权限
        PermissionActivity.requestPermissionAction(context, permission.value(), permission.requestCode(), new IPermission() {
            // 已经授权
            @Override
            public void agree() {
                // 申请成功
                try {
                    point.proceed(); // 被Permission 的函数，正常执行下去，不拦截
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void cancel() {
                // 被拒绝
                PermissionUtils.invokeAnnotion(thisObject, PermissionCancel.class);
            }

            @Override
            public void denied() {
                // 被拒绝（不再提醒的）
                PermissionUtils.invokeAnnotion(thisObject, PermissionDenied.class);
            }
        });
    }

}
