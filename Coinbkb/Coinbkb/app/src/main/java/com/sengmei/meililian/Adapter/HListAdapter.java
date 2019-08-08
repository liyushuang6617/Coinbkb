package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Beans.HangQingBean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.HListBean;
import com.sengmei.meililian.Fragment.A_FragmentShouYe;
import com.sengmei.meililian.Utils.CustomDialog;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.List;

public class HListAdapter extends BaseAdapter {
    private Context context;
    private List<HangQingBean.quotationBean> list;
    private int NN;

    private CustomDialog customDialog;

    public HListAdapter(Context context, List<HangQingBean.quotationBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.hlist_item, null);
            holder.A1 = (TextView) view.findViewById(R.id.tv1);
            holder.A2 = (TextView) view.findViewById(R.id.tv2);
            holder.A3 = (TextView) view.findViewById(R.id.tv3);
            holder.A4 = (TextView) view.findViewById(R.id.tv4);
            view.setTag(holder);
        } else {
            holder = (ViewHodler) view.getTag();
        }
        HangQingBean.quotationBean bean = list.get(i);
        String last_price = bean.getNow_price();
        String proportion = bean.getChange();
        holder.A1.setText(bean.getCurrency_name() + "/" + bean.getLegal_name());
        if (StringUtil.isBlank(last_price)) {
            last_price = "+0.0000";
        }
        if (StringUtil.isBlank(proportion)) {
            proportion = "+0.00";
        }
        String str = proportion.substring(0, 1);
        double aA = Double.parseDouble(proportion);
        String ss = String.format("%.2f", aA);//保留小数
        if (str.equals("-")) {
            holder.A2.setText(last_price);
            holder.A2.setTextColor(context.getResources().getColor(R.color.fabi_line));
            holder.A3.setTextColor(context.getResources().getColor(R.color.fabi_line));
            holder.A3.setText(ss + "%");
        } else {
            holder.A2.setText(last_price);
            holder.A2.setTextColor(context.getResources().getColor(R.color.green));
            holder.A3.setTextColor(context.getResources().getColor(R.color.green));
            holder.A3.setText("+" + ss + "%");
        }
        holder.A4.setText("≈" + bean.getNow_cny_price() + "CNY");
        return view;
    }

    class ViewHodler {
        TextView A1, A2, A3, A4;
    }

}
