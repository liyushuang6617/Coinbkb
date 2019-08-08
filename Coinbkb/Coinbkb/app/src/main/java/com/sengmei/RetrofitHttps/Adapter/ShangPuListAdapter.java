package com.sengmei.RetrofitHttps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.ShangPuBean;
import com.sengmei.RetrofitHttps.Beans.WoDeShangPuListBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.DingDanJiLu;
import com.sengmei.meililian.Activity.DingDanJiLuShangPu;
import com.sengmei.meililian.Activity.E_ShangPuListActivity;
import com.sengmei.meililian.InterFaces.ShangPuYiChangBack;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShangPuListAdapter extends BaseAdapter {
    private List<WoDeShangPuListBean.DateBean> data;
    private Context context;
    private int selectedPosition = 0;
    private boolean bb;
    private ShangPuYiChangBack yiChangBack1;

    public ShangPuListAdapter(Context context, List<WoDeShangPuListBean.DateBean> data) {
        this.context = context;
        this.data = data;
    }

    public void setYiChangBack(ShangPuYiChangBack yiChangBack) {
        this.yiChangBack1 = yiChangBack;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
    public void setZhangTai(boolean bbs) {
        bb = bbs;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.shangpulist_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.shuliang = (TextView) convertView.findViewById(R.id.shuliang);
            holder.edu = (TextView) convertView.findViewById(R.id.edu);
            holder.danjia = (TextView) convertView.findViewById(R.id.danjia);
            holder.yichang = (TextView) convertView.findViewById(R.id.yichang);
            holder.chehui = (TextView) convertView.findViewById(R.id.chehui);
            holder.chakan = (TextView) convertView.findViewById(R.id.chakan);
            holder.tu = (ImageView) convertView.findViewById(R.id.tu);

            if (bb==false){
                holder.yichang.setVisibility(View.GONE);
                holder.chehui.setVisibility(View.GONE);
            }else {
                holder.yichang.setVisibility(View.VISIBLE);
                holder.chehui.setVisibility(View.VISIBLE);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WoDeShangPuListBean.DateBean objectBean = data.get(position);
        holder.title.setText(objectBean.getCurrency_name());
        holder.shuliang.setText("数量 " + objectBean.getSurplus_number() + "--" + objectBean.getCurrency_name());
        holder.edu.setText("￥" + objectBean.getLimitation_min() + "-￥" + objectBean.getLimitation_max());
        holder.danjia.setText(objectBean.getPrice());

        holder.yichang.setOnClickListener(onClickListener);
        holder.chehui.setOnClickListener(onClickListener);
        holder.chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DingDanJiLuShangPu.class).putExtra("ids", data.get(position).getId()));

            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView title, shuliang, edu, danjia, yichang, chehui, chakan;
        ImageView tu;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.yichang:
                    YiChangShow();
                    break;
                case R.id.chehui:
                    CheHuiShow();
                    break;
                default:
                    break;
            }
        }
    };

    //异常
    private void YiChangShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.URLerror(data.get(selectedPosition).getId());
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("AAAAA", "@@11=" + response.body().getType());
                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
            }
        });
    }

    //撤回
    private void CheHuiShow() {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.gettrade(data.get(selectedPosition).getId());
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("YiChangBack", "@@YiChangBack=" +response.body().getType());
                yiChangBack1.YiChangBack(response.body().getType());
                Log.e("YiChangBack", "@@YiChangBack=" +response.body().getType());
                Log.e("AAAAA", "@@11=" + response.body().getType());
                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
            }
        });
    }
}
