package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.JYLBBean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Utils.CustomDialog;

import java.util.List;

public class WKListAdapter extends BaseAdapter{
    private Context context;
    private List<JYLBBean.ObjectBean> list;
    private int NN;

    private CustomDialog customDialog;
    public WKListAdapter(Context context, List<JYLBBean.ObjectBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.wklist_item, null);
            holder.A1 = (TextView) view.findViewById(R.id.tv1);
            holder.A2 = (TextView) view.findViewById(R.id.tv2);
            holder.A3 = (TextView) view.findViewById(R.id.tv3);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        JYLBBean.ObjectBean bean = list.get(i);
        holder.A1.setText(bean.getChange());
        holder.A2.setText(bean.getMemo());
        holder.A3.setText(bean.getCreate_time());
        return view;
    }


    class ViewHodler {
        TextView A1, A2, A3;


    }

}
