package com.xinrenlei.javademo.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Auth：yujunyao
 * Since: 2020/9/16 2:33 PM
 * Email：yujunyao@xinrenlei.net
 */

public class ScrollerBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    public ScrollerBehavior() {
    }

    public ScrollerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        ViewCompat.offsetTopAndBottom(child, (dependency.getBottom() - child.getTop()));
        return false;
    }
}
