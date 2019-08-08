package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.sengmei.RetrofitHttps.Beans.FaBiTiTledizhiBean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.E_TiBiBean;

import java.util.List;

public class E_TiBiAdapter extends BaseAdapter {
    private Context context;
    private List<FaBiTiTledizhiBean.ObjectBean> list;

    public E_TiBiAdapter(Context context, List<FaBiTiTledizhiBean.ObjectBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.e_tibi_item, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.num = (TextView) view.findViewById(R.id.num);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        FaBiTiTledizhiBean.ObjectBean bean = list.get(i);
        holder.name.setText(bean.getName());
        holder.num.setText(bean.getHas_address_num());


        return view;
    }


    class ViewHodler {
        TextView name,num;


    }
}
