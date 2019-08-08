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

import com.sengmei.RetrofitHttps.Beans.WDJY1Bean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.Bean.E_WoDeJiaoYiBean1;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.CustomDialog;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class E_WoDeJiaoYiAdapter1 extends BaseAdapter {
    private Context context;
    private List<WDJY1Bean.ObjectBean> list;
    private int NN;

    private CustomDialog customDialog;
    public E_WoDeJiaoYiAdapter1(Context context, List<WDJY1Bean.ObjectBean> list) {
        this.context = context;
        this.list = list;
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
            view = LayoutInflater.from(context).inflate(R.layout.e_wodejiaoyi_item1, null);
            holder.A1 = (TextView) view.findViewById(R.id.tv1);
            holder.A2 = (TextView) view.findViewById(R.id.tv2);
            holder.A3 = (TextView) view.findViewById(R.id.tv3);
            holder.A4 = (TextView) view.findViewById(R.id.tv4);
            holder.A5 = (TextView) view.findViewById(R.id.tv5);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }

        WDJY1Bean.ObjectBean bean = list.get(i);
        holder.A1.setText(bean.getCurrency_name()+ "/" +bean.getLegal_name());
        holder.A2.setText(bean.getPrice());
        holder.A3.setText(bean.getNumber());
        holder.A4.setText(bean.getTime());
        if (bean.getType().equals("out")){

            holder.A5.setTextColor(context.getResources().getColor(R.color.color_text_FireBrick));
            holder.A5.setText("卖出");
        }else {
            holder.A5.setTextColor(context.getResources().getColor(R.color.blue));
            holder.A5.setText("买入");
        }
        notifyDataSetChanged();

        return view;
    }



    class ViewHodler {
        TextView A1, A2, A3, A4, A5;


    }

}
