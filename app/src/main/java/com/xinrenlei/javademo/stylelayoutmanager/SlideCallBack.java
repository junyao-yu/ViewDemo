package com.xinrenlei.javademo.stylelayoutmanager;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Auth：yujunyao
 * Since: 2020/9/15 11:00 AM
 * Email：yujunyao@xinrenlei.net
 */

public class SlideCallBack extends ItemTouchHelper.SimpleCallback {
    /**
     * Creates a Callback for the given drag and swipe allowance. These values serve as
     * defaults
     * and if you want to customize behavior per ViewHolder, you can override
     * {@link #getSwipeDirs(RecyclerView, ViewHolder)}
     * and / or {@link #getDragDirs(RecyclerView, ViewHolder)}.
     *
     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     */
    public SlideCallBack(RecyclerView mRecyclerView, CardAdapter mCardAdapter, List<CardBean> list) {
        //swipeDirs = LEFT | TOP | RIHT | BOTTOM = 15
        super(0, 15);
        this.mRecyclerView = mRecyclerView;
        this.mCardAdapter = mCardAdapter;
        this.list = list;
    }

    private RecyclerView mRecyclerView;
    private CardAdapter mCardAdapter;
    private List<CardBean> list;

    //drag 拖拽--长按之后拖动
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    //滑动
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        //滑出删除，在添加到第0个
        CardBean cardBean = list.remove(viewHolder.getLayoutPosition());
        list.add(0, cardBean);
        mCardAdapter.notifyDataSetChanged();
    }

    //绘制
    public static final int MAX_SHOW_COUNT = 4;
    public int translateYGap = 50;
    public float scaleGap = 0.05f;
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        double maxDistance =    recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX * dX + dY * dY);
        double fraction = distance / maxDistance;

        if (fraction > 1) {
            fraction = 1;
        }

        //显示的个数4个
        int itemCount = recyclerView.getChildCount();

        for (int i = 0; i < itemCount; i++) {
            View view  = recyclerView.getChildAt(i);

            int level = itemCount - i - 1;

            if (level > 0) {
                if (level < MAX_SHOW_COUNT - 1) {
                    view.setTranslationY((float) (translateYGap * level - fraction * translateYGap));
                    view.setScaleY((float) (1 - scaleGap * level + fraction * scaleGap));
                    view.setScaleX((float) (1 - scaleGap * level + fraction * scaleGap));
                }
            }
        }
    }

    //这边可以设置动画时间
    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }
}
