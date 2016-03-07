package com.example.dragrelativelayout.application;

import com.commonlibrary.asynchttpclient.CommonClient;
import com.commonlibrary.database.db.DBHelper;
import com.commonlibrary.sharesdk.ShareManager;
import com.example.dragrelativelayout.constants.Constants;
import com.loopj.android.http.PersistentCookieStore;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;


/**
 * *******************************************************
 *
 * @文件名称：CommonApplication.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年11月19日 下午10:38:25
 * @文件描述：App容器
 * @修改历史：2015年11月19日创建初始版本 ********************************************************
 */
public class CommonApplication extends Application {

    private static CommonApplication mApplication = null;

    //监测对象是否泄露的对象
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
        initCookieStore();
        initShareSDK();
        initJPush();
        initLeakCanary();
        initDataBase();
    }

    public static CommonApplication getInstance() {

        return mApplication;
    }

    public RefWatcher getRefWatcher() {

        return mRefWatcher;
    }

    /**
     * 提前为单例类初始化参数，不必每次都通过getInstance()去传
     */
    public void initDataBase() {

        DBHelper.setContext(this);
    }

    private void initJPush() {

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void initShareSDK() {

        ShareManager.initSDK(this);
    }

    /**
     * 初始化全局CookieStore
     */
    private void initCookieStore() {
        CommonClient.setCookieStore(new PersistentCookieStore(this));
    }

    /**
     * 初始化LeakCanary内存分析工具
     */
    private void initLeakCanary() {

        mRefWatcher = Constants.IS_DUBUG_MDOE ? LeakCanary.install(this) : null;
    }

}