package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.MenuListBean;

import java.util.List;

public class MenuListAdapter extends BaseAdapter {
    private Context context;
    private List<MenuListBean> list;

    public MenuListAdapter(Context context, List<MenuListBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.menulist_item, null);
            holder.A1 = (TextView) view.findViewById(R.id.a1);
            holder.A2 = (TextView) view.findViewById(R.id.a2);
            holder.A3 = (TextView) view.findViewById(R.id.a3);
            holder.A4 = (TextView) view.findViewById(R.id.a4);
            holder.A5 = (TextView) view.findViewById(R.id.a5);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        MenuListBean bean = list.get(i);
        holder.A1.setText(bean.getNames());
        holder.A2.setText(bean.getNums1());
        holder.A3.setText(bean.getNums2());
        holder.A4.setText(bean.getNums3());
        String str=bean.getNums4().substring(0,1);

        if (str.equals("-")){holder.A5.setBackgroundResource(R.drawable.button_red);
            String str1=bean.getNums4().substring(0,str.length());
            Log.e("AASAS=", str1);
             double aA = Double.parseDouble(bean.getNums4());
             Log.e("AASAS=",""+aA);
           String ss=String.format("%.2f",aA);//保留小数
             holder.A5.setText(ss+"%");
        }else {
            holder.A5.setBackgroundResource(R.drawable.button_green);
            Log.e("AASAS=", bean.getNums4());
            String str1=bean.getNums4().substring(0,str.length());
            double aA = Double.parseDouble(bean.getNums4());
            Log.e("AASAS=", ""+aA);
            String ss=String.format("%.2f",aA);//保留小数
            holder.A5.setText("+"+ss+"%");
        }
/*
        double aA = Double.parseDouble(bean.getNums4());
        String ss=String.format("%.2f",aA);//保留小数
        holder.A5.setText(ss+"%");*/

        return view;
    }

    class ViewHodler {
        TextView A1,A2,A3,A4,A5;


    }
}
