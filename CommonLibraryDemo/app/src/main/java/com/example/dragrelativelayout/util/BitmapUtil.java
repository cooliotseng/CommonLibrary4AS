package com.example.dragrelativelayout.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * @author: vision
 * @function: 对Bitmap的一些操作
 * @date: 16/4/15
 */
public class BitmapUtil {

    /**
     * 压缩图片像素值，根据给定的宽高，以节省内存
     *
     * @param path   图片文件路径
     * @param pixelW 显示宽度
     * @param pixelH 显示高度
     * @return 目标Bitmap
     */
    public static Bitmap ratio(String path, int pixelW, int pixelH) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        //进行了一次预加载
        Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);
        newOpts.inJustDecodeBounds = false;

        int originalW = newOpts.outWidth;
        int originalH = newOpts.outHeight;
        //获取采样率
        newOpts.inSampleSize = getSimpleSize(originalW, originalH, pixelW, pixelH);
        return BitmapFactory.decodeFile(path, newOpts);
    }

    /**
     * 根据图片实际宽高和显示宽高计算采样率
     *
     * @param originalW Bitmap实际宽度
     * @param originalH Bitmap实际高度
     * @param pixelW    显示宽度
     * @param pixelH    显示高度
     * @return
     */
    private static int getSimpleSize(int originalW, int originalH, int pixelW, int pixelH) {
        int simpleSize = 1;
        if (originalW > originalH && originalW > pixelW) {
            simpleSize = (int) (originalW / pixelW);
        } else if (originalW < originalH && originalH > pixelH) {
            simpleSize = (int) (originalH / pixelH);
        }

        if (simpleSize <= 0) {

            simpleSize = 1;
        }
        return simpleSize;
    }


    /**
     * @param array 要解析为Bitmap的字节数组
     * @return 解析后的Bitmap
     */
    public static Bitmap bytes2Bitmap(byte[] array) {

        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    /**
     * 将bitmap转化为字节数组
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {

        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
