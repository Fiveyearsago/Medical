package com.jy.medical.util;

import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by songran on 16/10/26.
 */

public  class NetOperaterUtil {
    public static  void getResponse1(String url,String json){

    }


    public static void getResponse(String url,String json,Callback.CommonCallback<String> callback){
        RequestParams requestParams=new RequestParams(url);
        requestParams.setCharset("GBK");
        requestParams.setConnectTimeout(30000);
        requestParams.setAsJsonContent(true);
        requestParams.setBodyContent(json);
        x.http().post(requestParams, callback);
    }
}
