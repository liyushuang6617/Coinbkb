package com.sengmei.RetrofitHttps.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.DingDanJiLuBean;
import com.sengmei.RetrofitHttps.Beans.FaBiGuanLiBean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Utils.CustomDialog;

import java.util.List;

public class DingDanJiLuAdapter extends BaseAdapter{
    private Context context;
    private List<DingDanJiLuBean.dataBean> list;
    private int NN;

    private CustomDialog customDialog;
    public DingDanJiLuAdapter(Context context, List<DingDanJiLuBean.dataBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.guanlilist_item, null);
            holder.name1 = (TextView) view.findViewById(R.id.name1);
            holder.name2 = (TextView) view.findViewById(R.id.name2);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.shijian = (TextView) view.findViewById(R.id.shijian);
            holder.shuliang = (TextView) view.findViewById(R.id.shuliang);
            holder.edu = (TextView) view.findViewById(R.id.edu);
            holder.a1 = (TextView) view.findViewById(R.id.a1);
            holder.a2 = (TextView) view.findViewById(R.id.a2);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        DingDanJiLuBean.dataBean bean = list.get(i);
        holder.name1.setText("("+bean.getCurrency_name()+")");
        holder.name2.setText("("+bean.getCurrency_name()+")");
        holder.name.setText(bean.getCurrency_name());
        if (bean.getType().equals("sell")){
            holder.a1.setText("购买");
            holder.a1.setTextColor(context.getResources().getColor(R.color.text_blue));
        }else {
            holder.a1.setText("出售");
            holder.a1.setTextColor(context.getResources().getColor(R.color.color_text_FireBrick));
        }
        if (bean.getIs_sure().equals("0")){
            holder.a2.setText("未完成");
            holder.a2.setTextColor(context.getResources().getColor(R.color.text_blue));
        }else if (bean.getIs_sure().equals("1")){
            holder.a2.setText("已完成");
            holder.a2.setTextColor(context.getResources().getColor(R.color.black));
        }else if (bean.getIs_sure().equals("2")){
            holder.a2.setText("已取消");
            holder.a2.setTextColor(context.getResources().getColor(R.color.black));
        }else if (bean.getIs_sure().equals("3")){
            holder.a2.setText("已付款");
            holder.a2.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.shijian.setText(bean.getFormat_create_time());
        holder.shuliang.setText(bean.getNumber());
        holder.edu.setText(bean.getDeal_money());

        Log.e("DingDanShowDingDanShow", i+"@@DingDanShow=" + bean.getNumber());
        return view;
    }


    class ViewHodler {
        TextView name1, name2, name,shijian,shuliang,edu,a1,a2;


    }

}
