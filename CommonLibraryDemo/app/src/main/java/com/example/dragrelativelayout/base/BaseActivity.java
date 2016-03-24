package com.example.dragrelativelayout.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * @author: vision
 * @function: 所有Activity的基类，用来处理一些公共事件，如：数据统计
 * @date: 16/3/10
 */
public abstract class BaseActivity extends AppCompatActivity {

    public String mClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUmeng();
    }

    /**
     * 初始化友盟统计
     */
    private void initUmeng() {

        mClassName = getComponentName().getShortClassName();
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
    }

    /**
     * 初始化Butterknife注解框架，fragment也要bind才行。
     */
    protected void initButterknife() {

        ButterKnife.bind(this);
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
