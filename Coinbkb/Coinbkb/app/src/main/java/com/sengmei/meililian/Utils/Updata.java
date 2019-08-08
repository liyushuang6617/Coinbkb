package com.sengmei.meililian.Utils;

import android.os.Environment;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.List;

public class Updata {
    private static com.lidroid.xutils.HttpUtils httpUtils ;

    // 上传文件到服务器
    public static void upload(String path, String url, RequestCallBack<Object> requestCallBack) {
        httpUtils = new HttpUtils(10000);
        File file = new File(path);
        File file3 = new File(Environment.getExternalStorageDirectory(), "upload.png");
        //保存图片压缩上传服务器
        File file1 = BitmapUtil.saveBitmapToSD(BitmapUtil.getBitmapFromFile(file.getPath(), 100, 100), file3);
        RequestParams params = new RequestParams();
        params.addBodyParameter(file1.getPath().replace("/", ""), file1);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params,requestCallBack);
    }
    public static void uploads(List<String> list, String url, RequestCallBack<Object> requestCallBack) {
        httpUtils = new HttpUtils(10000);
        for (String s:list){
            File file = new File(s);
            File file3 = new File(Environment.getExternalStorageDirectory(), "upload.png");
            //保存图片压缩上传服务器
            File file1 = BitmapUtil.saveBitmapToSD(BitmapUtil.getBitmapFromFile(file.getPath(), 100, 100), file3);
            RequestParams params = new RequestParams();
            params.addBodyParameter(file1.getPath().replace("/", ""), file1);
            httpUtils.send(HttpRequest.HttpMethod.POST, url, params, requestCallBack);
        }

    }
    public static void dissHttpu(){
        if(httpUtils != null ){
            httpUtils = null;
        }
    }
}
