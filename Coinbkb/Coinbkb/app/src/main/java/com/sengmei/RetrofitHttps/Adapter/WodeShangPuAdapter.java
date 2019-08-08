package com.sengmei.RetrofitHttps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.ShangPuBean;
import com.sengmei.RetrofitHttps.Beans.WoDeShangPuBean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.E_ShangPuListActivity;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.List;

public class WodeShangPuAdapter extends BaseAdapter {
    private List<WoDeShangPuBean.DateBean> data;
    private Context context;
    public WodeShangPuAdapter(Context context, List<WoDeShangPuBean.DateBean> data) {
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.wodeshangpu_item,null);
            holder=new WodeShangPuAdapter.ViewHolder();
            holder.name1= (TextView) convertView.findViewById(R.id.name1);
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.times= (TextView) convertView.findViewById(R.id.times);
            holder.names= (TextView) convertView.findViewById(R.id.names);
            holder.yue= (TextView) convertView.findViewById(R.id.yue);
            holder.next= (TextView) convertView.findViewById(R.id.next);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        final WoDeShangPuBean.DateBean objectBean = data.get(position);
        if (StringUtil.isBlank(objectBean.getName())&objectBean.getName().length()>0){
            String str=objectBean.getName().substring(0, 1);
            holder.name1.setText(objectBean.getCurrency_name());
        }
        holder.name.setText(objectBean.getName());
        holder.times.setText("注册时间 "+objectBean.getCreate_time());
        holder.names.setText("所属法币 "+objectBean.getCurrency_name());
        holder.yue.setText(objectBean.getSeller_balance());
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,E_ShangPuListActivity.class).putExtra("Ids",objectBean.getId()));
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView name1,name,times,names,yue,next;
    }
}
