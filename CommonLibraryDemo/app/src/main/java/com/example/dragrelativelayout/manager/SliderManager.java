package com.example.dragrelativelayout.manager;

import android.content.Context;
import android.util.TypedValue;

import com.example.dragrelativelayout.R;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * @author: vision
 * @function:
 * @date: 16/3/24
 */
public class SliderManager {

    /**
     * 定义基本的滑动关闭样式
     * @param context
     * @return
     */
    public static SlidrConfig getNormalSlidrConfig(Context context) {

        SlidrConfig config = new SlidrConfig.Builder()
                .primaryColor(context.getResources().getColor(R.color.primaryDark))
                .secondaryColor(context.getResources().getColor(R.color.primaryDark))
                .position(SlidrPosition.LEFT)
                .velocityThreshold(2400)
                .distanceThreshold(0.25f)
                .edge(true).touchSize(dpToPx(context, 32))
                .build();
        return config;
    }

    public static int dpToPx(Context ctx, float dpSize) {
        return (int) TypedValue.applyDimension(1, dpSize, ctx.getResources().getDisplayMetrics());
    }
}
