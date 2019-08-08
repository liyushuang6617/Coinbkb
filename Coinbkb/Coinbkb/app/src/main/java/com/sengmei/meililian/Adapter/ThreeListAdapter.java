package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.ThreeListBean;
import com.sengmei.meililian.Utils.CustomDialog;

import java.util.List;

public class ThreeListAdapter extends BaseAdapter {
    private Context context;
    private List<ThreeListBean> list;
    private int NN;

    private CustomDialog customDialog;
    public ThreeListAdapter(Context context, List<ThreeListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null){
            if (list.size()<10){
                return list.size();
            }else {
                return 10;
            }
        }


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
            view = LayoutInflater.from(context).inflate(R.layout.threelist_item, null);
            holder.A1 = (TextView) view.findViewById(R.id.tv1);
            holder.A2 = (TextView) view.findViewById(R.id.tv2);
            holder.A3 = (TextView) view.findViewById(R.id.tv3);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        ThreeListBean bean = list.get(i);
        holder.A1.setText(bean.getNames());
        holder.A2.setText(bean.getNums1());
        holder.A3.setText(bean.getNums2());
        return view;
    }


    class ViewHodler {
        TextView A1, A2, A3;


    }

}
