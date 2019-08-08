package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.ZiChanBean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.E_ZCBBean;

import java.util.List;

public class E_ZCBAdapter extends BaseAdapter {
    private Context context;
    private List<ZiChanBean.legalbalanceBean> list;
    private boolean yan;

    public E_ZCBAdapter(Context context, List<ZiChanBean.legalbalanceBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setSelectedClick(boolean b) {
        yan = b;
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
            view = LayoutInflater.from(context).inflate(R.layout.e_zca_item, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.keyong = (TextView) view.findViewById(R.id.keyong);
            holder.dongjie = (TextView) view.findViewById(R.id.dongjie);
            holder.zhehe = (TextView) view.findViewById(R.id.zhehe);
            holder.ll = (LinearLayout) view.findViewById(R.id.ll);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        ZiChanBean.legalbalanceBean bean = list.get(i);
        holder.name.setText(bean.getCurrency_name());
        holder.keyong.setText(bean.getLegal_balance());
        holder.dongjie.setText(bean.getLock_legal_balance());
        holder.zhehe.setText(bean.getCny_price());

        if (yan&Double.valueOf(bean.getLegal_balance())==0) {
            holder.ll.setVisibility(View.GONE);
        } else {
            holder.ll.setVisibility(View.VISIBLE);
        }
        return view;
    }


    class ViewHodler {
        TextView name,keyong,dongjie,zhehe;
        LinearLayout ll;


    }
}
