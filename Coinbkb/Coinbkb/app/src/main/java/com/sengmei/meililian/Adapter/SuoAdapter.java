package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.SuoBean;
import com.sengmei.meililian.MainActivity111;

import java.util.List;

public class SuoAdapter extends BaseAdapter {
    private Context context;
    private List<SuoBean.MessageBean> list;

    public SuoAdapter(Context context, List<SuoBean.MessageBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_suo, null);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.startime = (TextView) view.findViewById(R.id.startime);
            holder.endtime = (TextView) view.findViewById(R.id.endtime);
            holder.img = (ImageView) view.findViewById(R.id.img);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        SuoBean.MessageBean bean = list.get(i);
        holder.title.setText(bean.getCurrencyName());
        holder.name.setText(bean.getNumber());
        holder.startime.setText(bean.getTime());
        holder.endtime.setText(bean.getLast_time());
        Glide.with(context).load(bean.getLogoUrl()).into(holder.img);

        return view;
    }


    class ViewHodler {
        TextView title,name,startime,endtime;
        private ImageView img;


    }
}
