package com.sengmei.meililian.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.ZiChanBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_ZCAA extends Fragment {
    private View view;
    private TextView titles, yue;
    private ImageView yan;
    private boolean Yans = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.e_zcaa, null);
        initView(view);
        GouMaioutShow();
        return view;
    }

    private void initView(View view) {
        titles = (TextView) view.findViewById(R.id.titles);
        yue = (TextView) view.findViewById(R.id.yue);
        yan = view.findViewById(R.id.yan);
        yan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Yans) {
                    Yans = false;
                    //选择状态 显示明文--设置为可见的密码
                    titles.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    yue.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    yan.setImageResource(R.mipmap.cb_checked);
                } else {
                    Yans = true;
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    titles.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    yue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    yan.setImageResource(R.mipmap.cb_normaled);
                }
            }
        });
    }

    //我的资产2
    private void GouMaioutShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(getActivity()).getService();
        Call<ZiChanBean> indexdata = mFromServerInterface.getZiChan();
        indexdata.enqueue(new Callback<ZiChanBean>() {

            @Override
            public void onResponse(Call<ZiChanBean> call, Response<ZiChanBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    Log.e("AAAAA", "@@11=" + response.body().getType());

                    titles.setText(response.body().getMessage().getChange_wallet().getTotal());
                    yue.setText(response.body().getMessage().getChange_wallet().getTotalCNY() + " CNY");
                }
            }

            @Override
            public void onFailure(Call<ZiChanBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());


            }
        });
    }
}
