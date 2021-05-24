package com.xinrenlei.javademo.hook;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xinrenlei.javademo.R;

/**
 * Auth：yujunyao
 * Since: 2020/10/12 5:13 PM
 * Email：yujunyao@xinrenlei.net
 * 没有在Manifest里面注册的activity
 */

public class HookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
    }
}
