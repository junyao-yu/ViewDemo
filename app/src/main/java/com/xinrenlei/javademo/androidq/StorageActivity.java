package com.xinrenlei.javademo.androidq;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

/**
 * Auth：yujunyao
 * Since: 2020/9/18 3:34 PM
 * Email：yujunyao@xinrenlei.net
 */

public class StorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File apkFile = getExternalFilesDir("apk");//如果没有文件夹，系统会创建
        // /storage/emulated/0/Android/data/com.xinrenlei.javademo/files/apk
        System.out.println("path ---> " + apkFile.getAbsolutePath());



    }
}
