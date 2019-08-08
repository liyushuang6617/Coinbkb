package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.HangQingBean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.FragmentTwoListBean;

import java.util.List;

public class FragmentTwoAdapter extends BaseAdapter {
    private Context context;
    private List<HangQingBean.quotationBean> list;

    public FragmentTwoAdapter(Context context, List<HangQingBean.quotationBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.fragmenttwolist1_item, null);
            holder.A1 = (TextView) view.findViewById(R.id.a1);
            holder.A11 = (TextView) view.findViewById(R.id.a11);
            holder.A2 = (TextView) view.findViewById(R.id.a2);
            holder.A3 = (TextView) view.findViewById(R.id.a3);
            holder.A4 = (TextView) view.findViewById(R.id.a4);
            holder.A5 = (TextView) view.findViewById(R.id.a5);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }

        HangQingBean.quotationBean bean = list.get(i);
        holder.A1.setText(bean.getCurrency_name());
        holder.A11.setText("/" + bean.getLegal_name());
        holder.A2.setText("24H量 " + bean.getVolume());
        holder.A3.setText(bean.getNow_price());
        holder.A4.setText("≈" + bean.getNow_cny_price() + "CNY");
        if (bean.getChange() != null) {
            if (bean.getChange().length() > 1) {
                String str = bean.getChange().substring(0, 1);
                double aA = Double.parseDouble(bean.getChange());
                String ss = String.format("%.2f", aA);//保留小数
                if (str.equals("-")) {
                    holder.A5.setBackgroundResource(R.drawable.button_red);
                    holder.A5.setText(ss + "%");
                } else {
                    holder.A5.setBackgroundResource(R.drawable.button_green);
                    holder.A5.setText("+" + ss + "%");
                }

            }
        } else {
            holder.A5.setBackgroundResource(R.drawable.button_green);
            holder.A5.setText("0%");
        }

        notifyDataSetChanged();

        return view;
    }


    class ViewHodler {
        TextView A1, A11, A2, A3, A4, A5;
    }
}
