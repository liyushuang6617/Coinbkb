package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.sengmei.RetrofitHttps.Beans.DanShowBean;
import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.GongDanBean;
import com.sengmei.meililian.Utils.CustomDialog;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.List;

public class GongDanAdapter extends BaseAdapter{
    private Context context;
    private List<DanShowBean.ObjectBean> list;
    private int NN;

    private CustomDialog customDialog;
    public GongDanAdapter(Context context, List<DanShowBean.ObjectBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.gongdan_item, null);
            holder.A1 = (TextView) view.findViewById(R.id.name);
            holder.A2 = (TextView) view.findViewById(R.id.times);
            holder.A11 = (TextView) view.findViewById(R.id.name1);
            holder.A22 = (TextView) view.findViewById(R.id.times1);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        DanShowBean.ObjectBean bean = list.get(i);
        holder.A1.setText(bean.getContent());
        holder.A2.setText(bean.getCreate_time());
        String str=bean.getReply_content();
        String str1=bean.getReply_time();
        if (StringUtil.isBlank(str)){
            holder.A11.setText("回复：暂无");
        }else {
            holder.A11.setText("回复："+bean.getReply_content());
        }
        if (StringUtil.isBlank(str1)){

        }else {
            holder.A22.setText(bean.getReply_time());
        }
        return view;
    }


    class ViewHodler {
        TextView A1, A2,A11, A22;


    }

}
