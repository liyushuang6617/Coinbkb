package com.sengmei.kline;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.KLineBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service {
    public static List<KLineBean.ObjectBean> list = new ArrayList<>();
    public static JSONArray jsonArray = new JSONArray();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KLineShow(MyService.this,"1");
    }

    //获取K1分线信息
    public static void KLineShow(final Context context, String s1) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<KLineBean> indexdata = mFromServerInterface.getK(s1, "1min");
        indexdata.enqueue(new Callback<KLineBean>() {

            @Override
            public void onResponse(Call<KLineBean> call, Response<KLineBean> response) {
                if (response.body() == null) {
                    Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                list = response.body().getData();

                for (int i = 0; i < list.size(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("Close", list.get(i).getClose());
                        jsonObject.put("Date", StringUtil.date(list.get(i).getTime()));
                        jsonObject.put("High", list.get(i).getHigh());
                        jsonObject.put("Low", list.get(i).getLow());
                        jsonObject.put("Open", list.get(i).getOpen());
                        jsonObject.put("Volume", list.get(i).getVolume());
                        jsonArray.put(jsonObject);Log.e("jsonArray@#", "jsonArray=: " + StringUtil.date(list.get(i).getTime()));
                        jsonObject = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("jsonArray", "jsonArray=: " + jsonArray);
            }
            @Override
            public void onFailure(Call<KLineBean> call, Throwable t) {
                Log.e("HuoQuShow", "@@11=" + t.getMessage());
            }
        });
    }
}
