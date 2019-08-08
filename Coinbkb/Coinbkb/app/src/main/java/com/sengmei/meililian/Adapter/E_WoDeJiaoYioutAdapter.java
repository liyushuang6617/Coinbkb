package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.WDJYBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.InterFaces.PiLiangSanChu;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.CustomDialog;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_WoDeJiaoYioutAdapter extends BaseAdapter{
    private Context context;
    private List<WDJYBean.ObjectBean> list;
    private int NN;
    private PiLiangSanChu piLiangSanChu;

    private CustomDialog customDialog;
    public E_WoDeJiaoYioutAdapter(Context context, List<WDJYBean.ObjectBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setPiLiangSanChu(PiLiangSanChu piLiangSanChu) {
        this.piLiangSanChu = piLiangSanChu;
    }
    @Override
    public int getCount() {
        if (list != null)

            return list.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHodler holder;
        if (view == null) {
            holder = new ViewHodler();
            view = LayoutInflater.from(context).inflate(R.layout.e_wodejiaoyi_item, null);
            holder.A1 = (TextView) view.findViewById(R.id.tv1);
            holder.A2 = (TextView) view.findViewById(R.id.tv2);
            holder.A3 = (TextView) view.findViewById(R.id.tv3);
            holder.A4 = (TextView) view.findViewById(R.id.tv4);
            holder.A5 = (TextView) view.findViewById(R.id.tv5);
            holder.A55 = (TextView) view.findViewById(R.id.tv55);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        WDJYBean.ObjectBean bean = list.get(i);
        holder.A1.setText(bean.getCurrency_name()+ "/" +bean.getLegal_name());
        holder.A2.setText(bean.getPrice());
        holder.A3.setText(bean.getNumber());
        holder.A4.setText(bean.getCreate_time());

        notifyDataSetChanged();
        holder.A5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NN=i;
                GouMaioutShow(list.get(i).getId());
            }
        });
        holder.A55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NN=i;
                GouMaioutShow1(list.get(i).getId());
            }
        });
        return view;
    }


    class ViewHodler {
        TextView A1, A2, A3, A4, A5, A55;


    }


    //out
    private void GouMaioutShow(final String ss) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getdels(ss,"out");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                if (response.body().getType().equals("ok")) {
                    list.remove(NN);
                    notifyDataSetChanged();
                    Log.e("AAAAAss", "@@11=" + response.body().getMessage());
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + t.getMessage().toString());
            }
        });
    }
    //out
    private void GouMaioutShow1(final String ss) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getdels1(ss,"out");
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                if (response.body().getType().equals("ok")) {
                    piLiangSanChu.IsClick("0",true);
                    Log.e("AAAAAss", "@@11=" + response.body().getMessage());
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + t.getMessage().toString());
            }
        });
    }
}
