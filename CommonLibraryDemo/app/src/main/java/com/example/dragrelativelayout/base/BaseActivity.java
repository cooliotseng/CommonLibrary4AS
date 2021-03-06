package com.example.dragrelativelayout.base;

import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.dragrelativelayout.application.CommonApplication;
import com.example.dragrelativelayout.asynchttp.LoginActivity;
import com.example.dragrelativelayout.constants.Constants;
import com.example.dragrelativelayout.manager.SliderManager;
import com.r0adkll.slidr.Slidr;
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
        //过滤掉不需要滑动关闭的Activity
        if (!(this instanceof LoginActivity)) {
            /**
             * 增加滑动关闭Actiivty,可能会和页面内滑动事件产生冲突。
             */
            Slidr.attach(this, SliderManager.getNormalSlidrConfig(CommonApplication.getInstance()));
        }
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

    /**
     * 申请指定的权限.
     */
    public void requestPermission(int code, String... permissions) {

        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 判断是否有指定的权限
     */
    public boolean hasPermission(String... permissions) {

        for (String permisson : permissions) {
            if (ContextCompat.checkSelfPermission(this, permisson)
                != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case Constants.WRITE_READ_EXTERNAL_CODE:
                if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    doSDCardPermission();
                }
                break;
        }
    }

    /**
     * 处理整个应用用中的SDCard业务
     */
    public void doSDCardPermission() {

    }
}
