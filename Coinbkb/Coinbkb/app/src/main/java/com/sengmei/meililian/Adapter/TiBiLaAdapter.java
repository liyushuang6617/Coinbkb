package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.TiBiXiaLaBean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Utils.CustomDialog;

import java.util.List;

public class TiBiLaAdapter extends BaseAdapter{
    private Context context;
    private List<TiBiXiaLaBean.MessageBean> list;
    private int NN;

    private CustomDialog customDialog;
    public TiBiLaAdapter(Context context, List<TiBiXiaLaBean.MessageBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.tibila_item, null);
            holder.A1 = (TextView) view.findViewById(R.id.tv1);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        TiBiXiaLaBean.MessageBean bean = list.get(i);
        holder.A1.setText(bean.getAddress());
        return view;
    }


    class ViewHodler {
        TextView A1;


    }

}
