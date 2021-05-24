package com.xinrenlei.javademo.stylelayoutmanager;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Auth：yujunyao
 * Since: 2020/9/15 9:48 AM
 * Email：yujunyao@xinrenlei.net
 */

public class CustomLayoutManager extends RecyclerView.LayoutManager {

    //屏幕上最多显示几个item
    public static final int MAX_SHOW_COUNT = 4;
    public int translateYGap = 50;
    public float scaleGap = 0.05f;


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        //参考LinearLayoutManager的代码
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    //布局
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        //ViewHolder回收复用
        detachAndScrapAttachedViews(recycler);

        int bottomPosition;
        int itemCount = getItemCount();
        if (itemCount < MAX_SHOW_COUNT) {
            bottomPosition = 0;
        } else {
            bottomPosition = itemCount - MAX_SHOW_COUNT;
        }

        for (int i = bottomPosition; i < itemCount; i++) {
            //复用
            View view = recycler.getViewForPosition(i);
            addView(view);

            measureChildWithMargins(view, 0, 0);

            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);

            //布局 draw---onDraw--onDrawOver---onLayout
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));


            int level = itemCount - i - 1;
            if (level > 0) {
                if (level < MAX_SHOW_COUNT - 1) {
                    view.setTranslationY(translateYGap * level);
                    view.setScaleX(1 - scaleGap * level);
                    view.setScaleY(1 - scaleGap * level);
                } else {
                    view.setTranslationY(translateYGap * (level - 1));
                    view.setScaleX(1 - scaleGap * (level - 1));
                    view.setScaleY(1 - scaleGap * (level - 1));
                }
            }


        }
    }
}
