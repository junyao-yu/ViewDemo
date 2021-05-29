package com.xinrenlei.javademo.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Auth：yujunyao
 * Since: 2020/9/16 11:04 AM
 * Email：yujunyao@xinrenlei.net
 */

public class CustomBehavior extends CoordinatorLayout.Behavior<TextView> {

    public CustomBehavior() {
    }

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mLayoutTop;
    private int mOffsetTopAndBottom;

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull TextView child, int layoutDirection) {

        parent.onLayoutChild(child, layoutDirection);

        mLayoutTop = child.getTop();

        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {


        if (target instanceof RecyclerView) {
            RecyclerView mRecyclerView = (RecyclerView) target;
            LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();

            int firstPosition = layoutManager.findFirstCompletelyVisibleItemPosition();

            if (firstPosition == 0) {//如果不判断的话，往下滑动的时候头部会先滑动出来，不过主要还是要看需要实现的效果
                int consumeDy;
                int offset = mOffsetTopAndBottom - dy;
                int minOffset = -getChildScrollRange(child);
                int maxOffset = 0;

                if (offset < minOffset) {
                    offset = minOffset;
                } else {
                    if (offset > maxOffset) {
                        offset = maxOffset;
                    }
                }

                System.out.println("offset--->" + offset);
                System.out.println("child.getTop--->" + child.getTop());

                ViewCompat.offsetTopAndBottom(child, offset - (child.getTop() - mLayoutTop));

                consumeDy = mOffsetTopAndBottom - offset;

                mOffsetTopAndBottom = offset;

                consumed[1] = consumeDy;//把移动的距离传给父类
            }
        }



    }

    //获取childView最大滑动距离
    private int getChildScrollRange(View childView) {
        if (childView == null) {
            return 0;
        }
        return childView.getHeight();
    }
}
