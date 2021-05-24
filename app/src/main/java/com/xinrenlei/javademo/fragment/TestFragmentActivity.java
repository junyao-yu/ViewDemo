package com.xinrenlei.javademo.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xinrenlei.javademo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth：yujunyao
 * Since: 2020/9/18 11:20 AM
 * Email：yujunyao@xinrenlei.net
 */

public class TestFragmentActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);

        mViewPager = findViewById(R.id.mViewPager);
        findViewById(R.id.tab1).setOnClickListener(this);
        findViewById(R.id.tab2).setOnClickListener(this);
        findViewById(R.id.tab3).setOnClickListener(this);
        findViewById(R.id.tab4).setOnClickListener(this);
        findViewById(R.id.tab5).setOnClickListener(this);

        List<Fragment> list = new ArrayList<>();
        list.add(MyFragment.newInstance(1));
        list.add(MyFragment.newInstance(2));
        list.add(MyFragment.newInstance(3));
        list.add(MyFragment.newInstance(4));
        list.add(MyFragment.newInstance(5));

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.addOnPageChangeListener(changeListener);
    }

    private ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mViewPager.setCurrentItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tab2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tab3:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.tab4:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.tab5:
                mViewPager.setCurrentItem(4);
                break;
        }
    }
}
