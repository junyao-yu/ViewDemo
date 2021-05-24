package com.xinrenlei.javademo.moveitem;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinrenlei.javademo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth：yujunyao
 * Since: 2020/9/15 4:22 PM
 * Email：yujunyao@xinrenlei.net
 */

public class MoveItemActivity extends AppCompatActivity implements OnStartDragListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_item);

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");

        RecyclerView mRecyclerView = findViewById(R.id.mRecyclerView);
        MoveItemAdapter moveItemAdapter = new MoveItemAdapter(list, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(moveItemAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(moveItemAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    private ItemTouchHelper itemTouchHelper;

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
