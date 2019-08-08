package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.FaBiTiTledizhiBean;
import com.sengmei.kline.R;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public AddressAdapter(Context context, List<String> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.address_item, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        String bean = list.get(i);
        holder.name.setText(bean);


        return view;
    }


    class ViewHodler {
        TextView name,num;


    }
}
