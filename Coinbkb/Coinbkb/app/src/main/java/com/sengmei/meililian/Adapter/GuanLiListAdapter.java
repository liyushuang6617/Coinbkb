package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.FaBiGuanLiBean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.DingDanXiangQingActivity;
import com.sengmei.meililian.Activity.DingDanXiangQingActivity1;
import com.sengmei.meililian.Utils.CustomDialog;

import java.util.List;

public class GuanLiListAdapter extends BaseAdapter{
    private Context context;
    private List<FaBiGuanLiBean.dataBean> list;
    private int NN;

    private CustomDialog customDialog;
    public GuanLiListAdapter(Context context, List<FaBiGuanLiBean.dataBean> list) {
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
            holder.ll = (LinearLayout) view.findViewById(R.id.ll);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        final FaBiGuanLiBean.dataBean bean = list.get(i);
        holder.name1.setText("("+bean.getCurrency_name()+")");
        holder.name2.setText("("+bean.getCurrency_name()+")");
        holder.name.setText(bean.getCurrency_name());
        if (bean.getType().equals("sell")){
            holder.a1.setText("购买");
            holder.a1.setTextColor(context.getResources().getColor(R.color.color_text_FireBrick));
        }else {
            holder.a1.setText("出售");
            holder.a1.setTextColor(context.getResources().getColor(R.color.blue));
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
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("AASAA",bean.getType()+"=##="+bean.getIs_sure()+"=@#@#="+bean.getId());
                if (bean.getIs_sure().equals("0")){
                   // holder.a2.setText("未完成");
                    if (bean.getType().equals("sell")){
                        context.startActivity(new Intent(context,DingDanXiangQingActivity1.class).putExtra("ids",bean.getId()));
                    }else {
                        context.startActivity(new Intent(context,DingDanXiangQingActivity.class)
                                .putExtra("ids",bean.getId()));
                    }
                }else if (bean.getIs_sure().equals("1")){
                   // holder.a2.setText("已完成");
                    context.startActivity(new Intent(context,DingDanXiangQingActivity.class).putExtra("ids",bean.getId()));
                }else if (bean.getIs_sure().equals("2")){
                   // holder.a2.setText("已取消");
                    context.startActivity(new Intent(context,DingDanXiangQingActivity.class).putExtra("ids",bean.getId()));
                }else if (bean.getIs_sure().equals("3")){
                   // holder.a2.setText("已付款");
                    context.startActivity(new Intent(context,DingDanXiangQingActivity.class).putExtra("ids",bean.getId()));
                }

            }
        });
        return view;
    }


    class ViewHodler {
        TextView name1, name2, name,shijian,shuliang,edu,a1,a2;
LinearLayout ll;

    }

}
