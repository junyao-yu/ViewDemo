package com.xinrenlei.javademo.utils.skin;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * Auth：yujunyao
 * Since: 2020/9/7 5:09 PM
 * Email：yujunyao@xinrenlei.net
 */

public class SkinResources {

    private String mSkinPackageName;
    private boolean isDefaultSkin = true;

    //宿主app的resource
    private Resources mHostResources;
    //皮肤包的resource
    private Resources mSkinResources;

    private SkinResources(Context context) {
        mHostResources = context.getResources();
    }

    private volatile static SkinResources instance;
    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }

    public void reset() {
        mSkinResources = null;
        mSkinPackageName = "";
        isDefaultSkin = true;
    }

    public void applySkin(Resources resources, String packageName) {
        mSkinResources = resources;
        mSkinPackageName = packageName;
        //是否使用默认皮肤
        isDefaultSkin = TextUtils.isEmpty(mSkinPackageName) || mSkinResources == null;
    }

    /**
     * 1.通过原始app中的resId(R.color.XX)获取到自己的 名字
     * 2.根据名字和类型获取皮肤包中的ID
     */
    public int getIdentifier(int resId) {
        if (isDefaultSkin) {
            return resId;
        }
        String resName = mHostResources.getResourceEntryName(resId);
        String resType = mHostResources.getResourceTypeName(resId);
        int skinId = mSkinResources.getIdentifier(resName, resType, mSkinPackageName);
        return skinId;
    }

    public int getColor(int resId) {
        if (isDefaultSkin) {
            return mHostResources.getColor(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mHostResources.getColor(resId);
        }
        return mSkinResources.getColor(skinId);
    }

    public Drawable getDrawable(int resId) {
        if (isDefaultSkin) {
            return mHostResources.getDrawable(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mHostResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }

    /**
     * 可能是color,可能是drawable
     * @param resId
     * @return
     */
    public Object getBackground(int resId) {
        String resourceTypeName = mHostResources.getResourceTypeName(resId);

        if ("color".equals(resourceTypeName)) {
            return getColor(resId);
        } else {
            return getDrawable(resId);
        }
    }

}
