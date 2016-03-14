package com.example.dragrelativelayout.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * @author: vision
 * @function: 所有Activity的基类，用来处理一些公共事件，如：数据统计,公共UI的逻辑处理
 * @date: 16/3/10
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 统计Activity跳转的名字
     */
    public String mClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUmeng();
    }

    /**
     * 为所有页面添加友盟统计
     */
    private void initUmeng() {

        mClassName = getComponentName().getShortClassName();
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mClassName);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mClassName);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
