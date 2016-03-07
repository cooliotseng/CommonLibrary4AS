package com.example.dragrelativelayout.asynchttp;

import com.commonlibrary.asynchttpclient.CommonClient;
import com.commonlibrary.asynchttpclient.CommonJsonResponseHandler;
import com.commonlibrary.asynchttpclient.DisposeDataHandle;
import com.example.dragrelativelayout.constants.UrlConstants;
import com.example.dragrelativelayout.module.User;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

public class RequestCenter
{

	public static RequestHandle requestLogin(String username, String password, DisposeDataHandle dataHandler)
	{
		RequestParams params = new RequestParams();
		params.put("mb", username);
		params.put("pwd", password);
		return CommonClient.post(UrlConstants.USER_LOGIN, params, new CommonJsonResponseHandler(dataHandler, User.class));
	}
}
