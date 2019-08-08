package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sengmei.kline.R;
import com.sengmei.meililian.Bean.Suo1Bean;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.List;

public class suo1Adapter extends BaseAdapter {
    private Context context;
    private List<Suo1Bean.MessageBean> list;
    private int pont=-1;

    public suo1Adapter(Context context, List<Suo1Bean.MessageBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setPont(int pont) {
        this.pont = pont;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_suo1, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.img = (ImageView) view.findViewById(R.id.img);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        Suo1Bean.MessageBean bean = list.get(i);
        holder.name.setText("定存"+bean.getTotalDay()+"天日利率"+StringUtil.mul(""+bean.getRate(),"100")+"%");
        if (pont==i){
            holder.img.setVisibility(View.VISIBLE);
        }else {
            holder.img.setVisibility(View.GONE);
        }

        return view;
    }


    class ViewHodler {
        TextView name;
        ImageView img;


    }
}
