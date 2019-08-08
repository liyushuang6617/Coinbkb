package com.sengmei.RetrofitHttps.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.Beans.ZhuanHuanBean;
import com.sengmei.kline.R;

import java.util.List;

public class ZhuanHuanAdapter extends BaseAdapter {
    private List<FaBiTiTleBean.ObjectBean> data;
    private Context context;
    private int selectedPosition = 0;// 选中的位置
    public ZhuanHuanAdapter(Context context, List<FaBiTiTleBean.ObjectBean> data) {
        this.context = context;
        this.data = data;
    }
    public void setSelectedPosition(int positi) {
        selectedPosition = positi;
        Log.e("selectedPosition="+selectedPosition,"position="+positi );
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
            convertView=View.inflate(context, R.layout.zhuanhuantitle_item,null);
            holder=new ZhuanHuanAdapter.ViewHolder();
            holder.title= (TextView) convertView.findViewById(R.id.index_title);
            holder.v2= (LinearLayout) convertView.findViewById(R.id.v2);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        FaBiTiTleBean.ObjectBean objectBean = data.get(position);
        String title = objectBean.getName();
        holder.title.setText(title);
       /* */
        Log.e("selectedPosition="+selectedPosition,"position="+position );
        if (selectedPosition == position) {
            holder.title.setTextColor(context.getResources().getColor(R.color.blue));
        } else {
            holder.title.setTextColor(context.getResources().getColor(R.color.black));
        }
        return convertView;
    }
    class ViewHolder{
        TextView title;
        LinearLayout v2;
    }
}
