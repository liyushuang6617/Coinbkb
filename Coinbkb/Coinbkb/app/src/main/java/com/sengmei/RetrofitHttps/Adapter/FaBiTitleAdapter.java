package com.sengmei.RetrofitHttps.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.kline.R;

import java.util.List;

public class FaBiTitleAdapter extends BaseAdapter {
    private List<FaBiTiTleBean.ObjectBean> data;
    private Context context;
    private int selectedPosition = 0;// 选中的位置
    public FaBiTitleAdapter(Context context, List<FaBiTiTleBean.ObjectBean> data) {
        this.context = context;
        this.data = data;
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
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
            convertView=View.inflate(context, R.layout.fabititle_item,null);
            holder=new FaBiTitleAdapter.ViewHolder();
            holder.title= (TextView) convertView.findViewById(R.id.index_title);
            holder.v2= (View) convertView.findViewById(R.id.v2);
            holder.title.setTextColor(context.getResources().getColor(R.color.black));
            holder.v2.setBackgroundResource(R.color.transparent);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        FaBiTiTleBean.ObjectBean objectBean = data.get(position);
        String title = objectBean.getName();
        holder.title.setText(title);
       /* */
        if (selectedPosition == position) {
            holder.title.setTextColor(context.getResources().getColor(R.color.blue));
            holder.v2.setBackgroundResource(R.color.blue);
        } else {

            holder.title.setTextColor(context.getResources().getColor(R.color.black));
            holder.v2.setBackgroundResource(R.color.transparent);
        }
        return convertView;
    }
    class ViewHolder{
        TextView title;
        View v2;
    }
}
