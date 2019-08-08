package com.sengmei.RetrofitHttps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.C2CTiTleBean;
import com.sengmei.RetrofitHttps.Beans.C2C_FaBuBean;
import com.sengmei.RetrofitHttps.Beans.FaBiGuanLiBean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.DingDanXiangQingActivity;
import com.sengmei.meililian.Activity.DingDanXiangQingActivity1;
import com.sengmei.meililian.Utils.CustomDialog;

import java.util.List;

public class C2C_FaBuAdapter extends BaseAdapter{
    private Context context;
    private List<C2CTiTleBean.ObjectBean> list;
    private int NN;

    private CustomDialog customDialog;
    public C2C_FaBuAdapter(Context context, List<C2CTiTleBean.ObjectBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.c2c_fabu_item, null);
            holder.name1 = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        C2CTiTleBean.ObjectBean bean = list.get(i);
        holder.name1.setText(bean.getName());
        return view;
    }


    class ViewHodler {
        TextView name1;

    }

}
