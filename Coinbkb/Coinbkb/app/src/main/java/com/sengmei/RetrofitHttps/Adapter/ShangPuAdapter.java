package com.sengmei.RetrofitHttps.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.FaBiGouMaiBean;
import com.sengmei.RetrofitHttps.Beans.FaBiTiTleBean;
import com.sengmei.RetrofitHttps.Beans.GetMaiBean;
import com.sengmei.RetrofitHttps.Beans.PanDuan;
import com.sengmei.RetrofitHttps.Beans.ShangPuBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShangPuAdapter extends BaseAdapter {
    private TextView title, title1, danjia, xiane, nums, all_tv, next, jiaoyi, shuliang,names;
    private View va, va1;
    private String LeiXing = "money";
    private String DanJia, maxJia;
    private String  Titles,Title1s,Danjias,Xianes;
    private EditText num;
    private List<ShangPuBean.DateBean> data;
    private Context context;
    private int selectedPosition = 0;// 选中的位置
    private String MM="全部买入";
    private Dialog bottomDialog;

    public ShangPuAdapter(Context context, List<ShangPuBean.DateBean> data) {
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
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.shangpu_item, null);
            holder = new ShangPuAdapter.ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.s1 = (TextView) convertView.findViewById(R.id.s1);
            holder.s2 = (TextView) convertView.findViewById(R.id.s2);
            holder.s3 = (TextView) convertView.findViewById(R.id.s3);
            holder.s4 = (TextView) convertView.findViewById(R.id.s4);
            holder.s_iv = (ImageView) convertView.findViewById(R.id.s_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ShangPuBean.DateBean objectBean = data.get(position);
        holder.title.setText(objectBean.getCurrency_name());
        holder.s1.setText(objectBean.getTotal_number() + objectBean.getCurrency_name());
        holder.s2.setText("￥" + objectBean.getLimitation().getMin() + "-￥" + objectBean.getLimitation().getMax());
        holder.s3.setText(objectBean.getPrice());
     /*   if (objectBean.getWay().equals("ali_pay")) {
            holder.s_iv.setImageResource(R.mipmap.zhi);
        } else if (objectBean.getWay().equals("we_chat")){
            holder.s_iv.setImageResource(R.mipmap.wechat);
        }else if (objectBean.getWay().equals("bank")){
            holder.s_iv.setImageResource(R.mipmap.card);
        } else {
            holder.s_iv.setVisibility(View.GONE);
        }*/
        if (objectBean.getType().equals("sell")) {
            holder.s4.setText("立即购买");
            MM="全部买入";
            holder.s4.setBackgroundResource(R.drawable.button_blue);
        } else {
            holder.s4.setText("立即出售");
            holder.s4.setBackgroundResource(R.drawable.button_blue1);
            MM="全部卖出";
        }

        holder.s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YinSiShow("" + data.get(selectedPosition).getId());

            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView title, s1, s2, s3, s4;
        ImageView s_iv;
    }

    private void showDialog1() {
        bottomDialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.b_button, null);
        TextView quxiao = (TextView) contentView.findViewById(R.id.quxiao);
        names = (TextView) contentView.findViewById(R.id.names);
        if (MM.equals("全部买入")){
            names.setText("购买");
        }else {
            names.setText("出售");
        }
        title = (TextView) contentView.findViewById(R.id.title);
        title1 = (TextView) contentView.findViewById(R.id.title1);
        danjia = (TextView) contentView.findViewById(R.id.danjia);
        xiane = (TextView) contentView.findViewById(R.id.xiane);
        jiaoyi = (TextView) contentView.findViewById(R.id.jiaoyi);
        shuliang = (TextView) contentView.findViewById(R.id.shuliang);
        nums = (TextView) contentView.findViewById(R.id.nums);
        all_tv = (TextView) contentView.findViewById(R.id.all_tv);
        next = (TextView) contentView.findViewById(R.id.next);
        va = (View) contentView.findViewById(R.id.va);
        va1 = (View) contentView.findViewById(R.id.va1);
        num = (EditText) contentView.findViewById(R.id.num);
        num.addTextChangedListener(textWatcher);
        title.setText(Titles);
        title1.setText("CNY");
        num.setHint("请输入要"+UserBean.Fabititsp+"法币金额");
        danjia.setText(Danjias);
        xiane.setText(Xianes);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        all_tv.setText(MM);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("AAASAS", num.getText().toString().trim() + "&&&" + num.getText().toString().trim().length());
                if (num.getText().toString().trim().length() > 0) {
                    GouMaioutShow("" + data.get(selectedPosition).getId());
                    Log.e("AAASAS", "IDS=" + data.get(selectedPosition).getId());
                } else {
                    Toast.makeText(context, "数据不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.cancel();
            }
        });
        all_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("LeiXingLeiXing","LeiXing"+LeiXing );
                if (LeiXing.equals("money")) {
                    num.setText(maxJia);
                } else {
                    double a=0,aa=0;
                    if (StringUtil.isBlank(maxJia)){
                        a =0;
                    }else {
                        a = Double.parseDouble(maxJia);
                    } if (StringUtil.isBlank(DanJia)){
                        aa =0;
                    }else {
                        aa = Double.parseDouble(DanJia);
                    }
                    num.setText("" + a / aa);
                }
            }
        });
        jiaoyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText("");
                num.setHint("请输入要"+UserBean.Fabititsp+"法币金额");
                title1.setText("CNY");
                LeiXing = "money";
                nums.setText("0");
                jiaoyi.setTextColor(context.getResources().getColor(R.color.blue));
                va.setBackgroundResource(R.color.blue);
                shuliang.setTextColor(context.getResources().getColor(R.color.black));
                va1.setBackgroundResource(R.color.transparent);
            }
        });
        shuliang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText("");
                num.setHint("请输入要"+UserBean.Fabititsp+"数量");
                nums.setText("0");
                LeiXing = "number";
                title1.setText(Title1s);
                jiaoyi.setTextColor(context.getResources().getColor(R.color.black));
                va.setBackgroundResource(R.color.transparent);
                shuliang.setTextColor(context.getResources().getColor(R.color.blue));
                va1.setBackgroundResource(R.color.blue);
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = num.getText().toString().trim();
            if (str.length() > 0) {
                double a=0,aa=0;
                if (StringUtil.isBlank(str)){
                    a =0;
                }else {
                    a = Double.parseDouble(str);
                } if (StringUtil.isBlank(DanJia)){
                    aa =0;
                }else {
                    aa = Double.parseDouble(DanJia);
                }
                if (LeiXing.equals("number")) {

                    nums.setText("" + aa * a);
                } else {
                    nums.setText(str);
                }
            }

        }
    };

    //int
    private void YinSiShow(final String ss) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<FaBiGouMaiBean> indexdata = mFromServerInterface.getMaiinfo(ss);
        indexdata.enqueue(new Callback<FaBiGouMaiBean>() {

            @Override
            public void onResponse(Call<FaBiGouMaiBean> call, Response<FaBiGouMaiBean> response) {
                if (response.body() == null) {

                    return;
                }
                if (response.body().getType().equals("ok")) {
                    Log.e("AAAAA", "@@11=" + response.body().getMessage());
                    Titles=response.body().getMessage().getCurrency_name();
                    Title1s=response.body().getMessage().getCurrency_name();
                    Danjias="" + response.body().getMessage().getPrice();
                    Xianes="限额￥" + response.body().getMessage().getLimitation().getMin() + "-" + response.body().getMessage().getLimitation().getMax();

                    DanJia = response.body().getMessage().getPrice();
                    maxJia = response.body().getMessage().getLimitation().getMax();
                    showDialog1();
                }
            }

            @Override
            public void onFailure(Call<FaBiGouMaiBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage().toString());
            }
        });
    }
    //out
    private void GouMaioutShow(final String ss) {
        Log.e("AAAAAss", num.getText().toString().trim() + "=LeiXing=" + LeiXing);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<PanDuan> indexdata = mFromServerInterface.getMaiout(ss, LeiXing, num.getText().toString().trim());
        indexdata.enqueue(new Callback<PanDuan>() {

            @Override
            public void onResponse(Call<PanDuan> call, Response<PanDuan> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAAss", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    bottomDialog.cancel();
                    Toast.makeText(context, "下单成功，请联系商家", Toast.LENGTH_SHORT).show();
                    //GouMaioutShow2(ss);
                } else if (response.body().getType().equals("998")) {
                  //  GouMaioutShow1(ss);
                    bottomDialog.cancel();
                    Toast.makeText(context, "请先设置法币密码", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "下单失败", Toast.LENGTH_SHORT).show();
                   // GouMaioutShow1(ss);
                }

            }

            @Override
            public void onFailure(Call<PanDuan> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + call.toString() + "@@##" + t.getMessage().toString());
            }
        });
    }


}
