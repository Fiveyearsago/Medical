package com.jy.medical.util;

import com.google.gson.Gson;
import com.jy.ah.bus.data.Request;

import org.xutils.common.Callback;

/**
 * Created by songran on 16/10/27.
 */

public class ServerApiUtils {
    public static void sendToServer(String data,String requestCode,String url,Callback.CommonCallback<String> callback){
        Request request = new Request();
        request.setRequestCode(requestCode);
        request.setData(data);
        Gson gson = new Gson();
        NetOperaterUtil.getResponse(url,gson.toJson(request),callback);

    }
}
