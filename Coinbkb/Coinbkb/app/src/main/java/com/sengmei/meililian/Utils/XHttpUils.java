package com.sengmei.meililian.Utils;


import com.lidroid.xutils.HttpUtils;

public class XHttpUils {
    public static HttpUtils httpUtils=null;
    //静态工厂方法
    public static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils(3000);
        }
        return httpUtils;
    }
}
