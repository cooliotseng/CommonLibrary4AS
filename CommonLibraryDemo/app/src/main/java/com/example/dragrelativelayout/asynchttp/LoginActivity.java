package com.example.dragrelativelayout.asynchttp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonlibrary.asynchttpclient.DisposeDataHandle;
import com.example.dragrelativelayout.R;
import com.example.dragrelativelayout.base.BaseActivity;
import com.example.dragrelativelayout.constants.UmengEvent;
import com.example.dragrelativelayout.jpush.JPushTestActivity;
import com.example.dragrelativelayout.manager.UserManager;
import com.example.dragrelativelayout.module.PushMessage;
import com.example.dragrelativelayout.module.User;
import com.example.dragrelativelayout.util.DialogUtil;
import com.loopj.android.http.RequestHandle;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;

/**
 * *******************************************************
 *
 * @文件名称：LoginActivity.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年11月19日 下午11:21:54
 * @文件描述：测试用户登陆页面
 * @修改历史：2015年11月19日创建初始版本 ********************************************************
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    private RequestHandle loginRequest;
    /**
     * UI，使用了注解，要明白编译期注解的原理
     */
    @Bind(R.id.root_view)
    protected LinearLayout mRootView;
    @Bind(R.id.associate_email_input)
    protected EditText mUserNameView;
    @Bind(R.id.login_input_password)
    protected EditText mPasswordView;
    @Bind(R.id.login_button)
    protected TextView mLoginView;
    /**
     * data
     */
    private PushMessage mPushMessage; // 推送过来的消息
    private boolean fromPush; // 是否从推送到此页面

    LoginBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailassociate);
        initData();

        initButterknife();
        initView();

        receiver = new LoginBroadcastReceiver();
        registerBroadcast();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent.hasExtra("pushMessage")) {
            mPushMessage = (PushMessage) intent.getSerializableExtra("pushMessage");
        }
        fromPush = intent.getBooleanExtra("fromPush", false);
    }

    private void initView() {
        mLoginView.setOnClickListener(this);
    }

    private void requestLogin() {
        DialogUtil.getInstance().showProgressDialog(this, getString(R.string.loading));
        loginRequest = RequestCenter.requestLogin("18911230100", "999999q", new DisposeDataHandle() {

                @Override
                public void onRetry(int retryNo) {
                    super.onRetry(retryNo);
                }

                @Override
                public void onSuccess(Object responseObj) {
                    DialogUtil.getInstance().dismissProgressDialog();
                    /**
                     * 保存登陆信息
                     */
                    UserManager.getInstance().setUser((User) responseObj);

                    /**
                     *  发送一个登陆成功广播
                     */
                    sendLoginSuccessBroadcast();

                    if (fromPush) {
                        Intent intent = new Intent(LoginActivity.this, JPushTestActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("pushMessage", mPushMessage);
                        startActivity(intent);
                        finish();

                    } else {
                        /**
                         * 正常登陆流程
                         */
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("user", (User) responseObj);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Object reasonObj) {
                    DialogUtil.getInstance().dismissProgressDialog();
                }
            }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginRequest != null) {
            loginRequest.cancel(true);
        }
        unregisterBroadcast();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_button:
                /**
                 * 利用友盟统计登陆事件
                 */
                MobclickAgent.onEvent(this, UmengEvent.ONLOGIN.getValue());
                /**
                 * 可以弹出一个对话框阻止用户再用次操作
                 */
                requestLogin();
                break;
        }
    }

    private void sendLoginSuccessBroadcast() {
        Intent loginIntent = new Intent("com.example.dragreleative.login_success");
        LocalBroadcastManager.getInstance(this).sendBroadcast(loginIntent);
    }

    /**
     * 使用LocalBroadcastManager去处理应用内广播
     */
    private void registerBroadcast() {

        IntentFilter filter = new IntentFilter("com.example.dragreleative.login_success");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private void unregisterBroadcast() {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private class LoginBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("---------->", "yes");
        }
    }
}