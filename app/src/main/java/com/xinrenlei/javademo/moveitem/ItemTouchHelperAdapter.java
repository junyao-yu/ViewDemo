package com.xinrenlei.javademo.moveitem;

/**
 * Auth：yujunyao
 * Since: 2020/9/15 4:48 PM
 * Email：yujunyao@xinrenlei.net
 */

public interface ItemTouchHelperAdapter {

    //拖动item的回调
    void onItemMove(int fromPosition, int toPosition);

    //滑动item后删除的回调
    void onItemDismiss(int position);

}
