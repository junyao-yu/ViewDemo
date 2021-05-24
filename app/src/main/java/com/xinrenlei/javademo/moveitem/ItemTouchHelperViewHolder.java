package com.xinrenlei.javademo.moveitem;

/**
 * Auth：yujunyao
 * Since: 2020/9/15 4:53 PM
 * Email：yujunyao@xinrenlei.net
 */

public interface ItemTouchHelperViewHolder {

    //item选中时候的回调
    void onItemSelected();

    //item放开时候的回调
    void onItemClear();

}
