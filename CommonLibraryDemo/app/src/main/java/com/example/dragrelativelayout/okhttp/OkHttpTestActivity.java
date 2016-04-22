package com.example.dragrelativelayout.okhttp;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonlibrary.okhttp.CommonOkHttpClient;
import com.commonlibrary.okhttp.listener.DisposeDataHandle;
import com.commonlibrary.okhttp.listener.DisposeDataListener;
import com.commonlibrary.okhttp.listener.DisposeHandleCookieListener;
import com.commonlibrary.okhttp.request.CommonRequest;
import com.commonlibrary.okhttp.request.RequestParams;
import com.example.dragrelativelayout.R;
import com.example.dragrelativelayout.base.BaseActivity;
import com.example.dragrelativelayout.constants.Constants;
import com.example.dragrelativelayout.constants.UrlConstants;
import com.example.dragrelativelayout.imageloader.SimpleImageLoader;
import com.example.dragrelativelayout.module.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.CookieManager;
import java.util.ArrayList;

/**
 * @author vision
 * @function OkHttpClient网络请求测试页面
 */
public class OkHttpTestActivity extends BaseActivity implements DisposeHandleCookieListener, OnClickListener {
    private ImageView mImageView;
    private ImageView mSecondView;
    private Button mLoginView;
    private Button mCookieView;
    private Button mFileDownloadView;
    private TextView mCookieTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_test);
        initView();

        // mCookieManger = new CookieManager(new
        // PersistentCookieStore(getApplicationContext()),
        // CookiePolicy.ACCEPT_ALL);
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.four_view);
        mSecondView = (ImageView) findViewById(R.id.second_image_view);
        mLoginView = (Button) findViewById(R.id.login_view);
        mCookieView = (Button) findViewById(R.id.get_cookie_view);
        mFileDownloadView = (Button) findViewById(R.id.down_load_file);
        mCookieTextView = (TextView) findViewById(R.id.cookie_show_view);
        mLoginView.setOnClickListener(this);
        mCookieView.setOnClickListener(this);
        mFileDownloadView.setOnClickListener(this);
    }

    private void getRequest() {
        RequestParams params = new RequestParams();
        params.put("type", "1");
        params.put("name", "renzhiqaing");

        CommonOkHttpClient.get(CommonRequest.createGetRequest("https://publicobject.com/helloworld.txt", params),
            new DisposeDataHandle(new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                }

                @Override
                public void onFailure(Object reasonObj) {
                }
            }));
    }

    /**
     * 发送post请求,经过封装后使用方式式与AsyncHttpClient完全一样
     */
    private void postRequest() {
        /**
         * 这里在实际工程中还要再封装一层才好
         */
        RequestParams params = new RequestParams();
        params.put("mb", "18911230100");
        params.put("pwd", "999999q");
        CommonOkHttpClient.post(CommonRequest.createPostRequest(UrlConstants.USER_LOGIN, params),
            new DisposeDataHandle(this, User.class));
    }

    /**
     * 此处以下载图片做为文件下载的demo
     */
    private void downloadFile() {

        if (hasPermission(Constants.WRITE_READ_EXTERNAL_PERMISSION)) {

            doSDCardPermission();
        } else {
            requestPermission(Constants.WRITE_READ_EXTERNAL_CODE, Constants.WRITE_READ_EXTERNAL_PERMISSION);
        }
    }

    private void uploadFile() throws FileNotFoundException {

        RequestParams params = new RequestParams();
        params.put("test", new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test2.jpg"));

        CommonOkHttpClient.post(CommonRequest.createMultiPostRequest("https://api.imgur.com/3/image", params),
            new DisposeDataHandle(new DisposeDataListener() {

                @Override
                public void onSuccess(Object responseObj) {

                }

                @Override
                public void onFailure(Object reasonObj) {

                }
            }));
    }

    /**
     * 服务器返回数据
     *
     * @param responseObj
     */
    @Override
    public void onSuccess(Object responseObj) {

        /**
         * 这是一个需要Cookie的请求，说明Okhttp帮我们存储了Cookie，可以获取到数据用户数据
         */
        CommonOkHttpClient.post(CommonRequest.createPostRequest(UrlConstants.PUSH_LIST, null),
            new DisposeDataHandle(new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    mCookieTextView.setText(responseObj.toString());
                    // Log.e("push_list", responseObj.toString());
                }

                @Override
                public void onFailure(Object reasonObj) {
                }
            }));
    }

    @Override
    public void onFailure(Object reasonObj) {
    }

    @Override
    public void onCookie(ArrayList<String> cookieStrLists) {
        // 自己处理Cookie回调，返回的是cookie字符串，如果想要cookie对象，可以使用HttpCookie解析为对象类型。
        mCookieTextView.setText(cookieStrLists.get(0));
    }

    CookieManager cookieManager;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_view:
                postRequest();
                break;
            case R.id.get_cookie_view:
                try {
                    uploadFile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.down_load_file:
                downloadFile();
                break;
        }
    }


    @Override
    public void doSDCardPermission() {

        SimpleImageLoader.getInstance().displayImage(mImageView, "http://images.csdn.net/20150817/1.jpg");
        SimpleImageLoader.getInstance().displayImage(mSecondView, "http://banbao.chazidian.com/uploadfile/2016-01-25/s145368924044608.jpg");

    }
}