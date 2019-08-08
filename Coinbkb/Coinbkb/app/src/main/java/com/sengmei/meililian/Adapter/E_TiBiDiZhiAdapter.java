package com.sengmei.meililian.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.RetrofitHttps.IndexData;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.E_TiBiTianJiaActivity;
import com.sengmei.meililian.Bean.E_TiBiDiZhiBean;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_TiBiDiZhiAdapter extends BaseAdapter {
    private Context context;
    private List<E_TiBiDiZhiBean> list;
    private int point;

    public E_TiBiDiZhiAdapter(Context context, List<E_TiBiDiZhiBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.e_tibidizhi_item, null);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.addrss = (TextView) view.findViewById(R.id.addrss);
            holder.delet = (TextView) view.findViewById(R.id.delet);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }
        E_TiBiDiZhiBean bean = list.get(i);
        holder.name.setText(bean.getNames());
        holder.addrss.setText(bean.getNums1());
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point=i;
                TianJiaShow(list.get(i).getNums2());
            }
        });

        return view;
    }


    class ViewHodler {
        TextView name,addrss,delet;
    }

    //添加地址
    private void TianJiaShow(final String str){
        GetDataFromServerInterface mFromServerInterface= GetDataFromServer.getInstance(context).getService();
        Call<IndexData> indexdata=mFromServerInterface.getTBdzd(str);
        indexdata.enqueue(new Callback<IndexData>() {

            @Override
            public void onResponse(Call<IndexData> call, Response<IndexData> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAA","@@11="+str+"##"+response.body().getMessage());
                if (response.body().getType().equals("ok")) {
                    StringUtil.ToastShow1(context, response.body().getMessage());
                    list.remove(point);
                    notifyDataSetChanged();

                } else {
                    StringUtil.ToastShow1(context,  response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<IndexData> call, Throwable t) {
                Log.e("AAAAA","@@11="+t.getMessage() );
            }
        });
    }
}
