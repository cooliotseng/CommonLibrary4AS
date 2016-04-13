package com.example.dragrelativelayout.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.ViewGroup;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by renzhiqiang on 16/3/25.
 */
public class DialogUtil {

    private DialogUtil() {
    }

    public static DialogUtil getInstance() {
        return utils;
    }

    public void showProgressDialog(Context context, String msg) {
        showProgressDialog(context, null, msg);
    }

    public void showProgressDialog(Context context, String title, String msg) {
        showProgressDialog(context, title, msg, false, null);
    }

    public void showProgressDialog(Context context, String title, String msg, boolean isCancelable, android.content.DialogInterface.OnDismissListener dismissListener) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(isCancelable);
        if (dismissListener != null)
            progressDialog.setOnDismissListener(dismissListener);

        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private ProgressDialog progressDialog;
    private static DialogUtil utils = new DialogUtil();
}
