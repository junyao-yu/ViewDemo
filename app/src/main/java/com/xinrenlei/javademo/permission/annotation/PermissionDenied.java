package com.xinrenlei.javademo.permission.annotation;

/**
 * Auth：yujunyao
 * Since: 2020/10/4 10:20 PM
 * Email：yujunyao@xinrenlei.net
 */

public @interface PermissionDenied {

    int requestCode() default -1;

}
