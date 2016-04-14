package com.example.dragrelativelayout.constants;

import android.Manifest;

/**
 * Created by renzhiqiang on 16/3/6.
 *
 * @function 整个工程中的一些全局常量
 */
public class Constants {

    public static final boolean IS_DUBUG_MDOE = true;

    /**
     * 权限常量相关
     */
    public static final int WRITE_READ_EXTERNAL_CODE = 0x01;
    public static final String[] WRITE_READ_EXTERNAL_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
}
