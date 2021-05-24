package com.xinrenlei.javademo.stylelayoutmanager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.xinrenlei.javademo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth：yujunyao
 * Since: 2020/9/15 9:25 AM
 * Email：yujunyao@xinrenlei.net
 */

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        List<CardBean> list = new ArrayList<>();
        list.add(new CardBean());
        list.add(new CardBean());
        list.add(new CardBean());
        list.add(new CardBean());
        list.add(new CardBean());
        list.add(new CardBean());
        list.add(new CardBean());
        list.add(new CardBean());

        RecyclerView mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new CustomLayoutManager());
        CardAdapter mCardAdapter = new CardAdapter(list);
        mRecyclerView.setAdapter(mCardAdapter);

        SlideCallBack slideCallBack = new SlideCallBack(mRecyclerView, mCardAdapter, list);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(slideCallBack);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

}
