package com.example.dragrelativelayout.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.commonlibrary.disklrucache.DiskLruCacheHelper;
import com.commonlibrary.disklrucache.Utils;
import com.commonlibrary.okhttp.CommonOkHttpClient;
import com.commonlibrary.okhttp.listener.DisposeDataHandle;
import com.commonlibrary.okhttp.listener.DisposeDownloadListener;
import com.commonlibrary.okhttp.request.CommonRequest;
import com.example.dragrelativelayout.application.CommonApplication;

import java.io.File;

/**
 * @author: vision
 * @function: 用来加载网络图片，并缓存图片到本地和内存
 * @date: 16/4/13
 */
public class SimpleImageLoader {

    private static SimpleImageLoader mInstance = null;

    //内存缓存图片
    private LruCache<String, Bitmap> mLruCache;
    private DiskLruCacheHelper mDiskHelper;

    private SimpleImageLoader() {

        //init the memory cache
        int maxSize = (int) Runtime.getRuntime().maxMemory() / 8;
        mLruCache = new LruCache<String, Bitmap>(maxSize) {

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes();
            }
        };

        try {
            mDiskHelper = new DiskLruCacheHelper(CommonApplication.getInstance());
        } catch (Exception e) {

        }

        Log.e("TAG", mDiskHelper.toString());
    }

    public static SimpleImageLoader getInstance() {

        if (mInstance == null) {
            synchronized (SimpleImageLoader.class) {

                if (mInstance == null) {

                    mInstance = new SimpleImageLoader();
                }
            }
        }
        return mInstance;
    }

    public void displayImage(ImageView imageView, String url) {

        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap != null) {
            Log.e("TAG", "displayImage:" + bitmap.toString());
            imageView.setImageBitmap(bitmap); //要加一个Optation的对bitmap的处理
        } else {
            downloadImage(imageView, url);
        }
    }

    private Bitmap getBitmapFromCache(String url) {

        if (url != null) {

            if (mLruCache.get(url) != null) {
                Log.e("TAG", "from lru cache");
                return mLruCache.get(url);
            }

            if (mDiskHelper.getAsBitmap(url) != null) {
                Log.e("TAG", "from disk cache");
                return mDiskHelper.getAsBitmap(url);
            }
        }
        return null;
    }

    private void putBitmapToCache(String url, Bitmap bitmap) {

        if (bitmap != null) {

            mLruCache.put(url, bitmap);
            mDiskHelper.put(Utils.hashKeyForDisk(url), bitmap);
        }
    }

    /**
     * 下载图片，并添加到缓存中去
     *
     * @param url
     */
    private void downloadImage(final ImageView imageView, final String url) {

        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(url, null),
                new DisposeDataHandle(new DisposeDownloadListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        Bitmap bitmap = BitmapFactory.decodeFile(((File) responseObj).getAbsolutePath());

                        imageView.setImageBitmap(bitmap);
                        putBitmapToCache(url, bitmap);
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                    }

                    @Override
                    public void onProgress(int progrss) {
                        // 监听下载进度，更新UI
                        Log.e("--------->当前进度为:", progrss + "");
                    }
                }, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg"));
    }
}