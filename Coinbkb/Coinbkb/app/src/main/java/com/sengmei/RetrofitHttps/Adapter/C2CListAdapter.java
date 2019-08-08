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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.C2CListBean;
import com.sengmei.RetrofitHttps.Beans.GeRenZhongXinBean;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.C2C_XiangQing;
import com.sengmei.meililian.Activity.C2C_XiangQing1;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.StringUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class C2CListAdapter extends BaseAdapter {
    private Context context;
    private List<C2CListBean.dataBean> list;
    private TextView title, title1, danjia, xiane, nums, all_tv, next, jiaoyi, shuliang;
    private View va, va1;
    private EditText num;
    private String LeiXing = "number";
    private String DanJia, maxJia;
    private int point;
    private String MM="全部买入";
    private Dialog bottomDialog;
    private double Tota = 0;
    private String Nums,Pris;
    public C2CListAdapter(Context context, List<C2CListBean.dataBean> list) {
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

        final ViewHodler holder;
        if (view == null) {
            holder = new ViewHodler();
            view = LayoutInflater.from(context).inflate(R.layout.c2clist_item, null);
            holder.titl = (LinearLayout) view.findViewById(R.id.titl);
            holder.times = (TextView) view.findViewById(R.id.times);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.edu = (TextView) view.findViewById(R.id.edu);
            holder.name1 = (TextView) view.findViewById(R.id.name1);
            holder.num = (TextView) view.findViewById(R.id.num);
            holder.danjia = (TextView) view.findViewById(R.id.danjia);
            holder.bt = (Button) view.findViewById(R.id.bt);
            holder.tu = (ImageView) view.findViewById(R.id.tu);
            view.setTag(holder);
        } else {

            holder = (ViewHodler) view.getTag();
        }


        C2CListBean.dataBean bean = list.get(i);
        holder.name.setText(bean.getReal_name());
        if (bean.getReal_name().length() > 1) {
            String str = bean.getReal_name();
            String b = str.substring(0, 1);
            holder.name1.setText(b);
        }
        holder.bt.setText(UserBean.C2CMM);
        Log.e("FBETHShowCqC", "s1=" +bean.getReal_name());
        holder.num.setText("数量  "+bean.getMin_number()+"-" + bean.getTotal_number());
        holder.danjia.setText("￥  " + bean.getPrice());
        holder.times.setText(bean.getCreate_time());
        if (!StringUtil.isBlank(bean.getTotal_number())){

            Tota =Double.valueOf(bean.getTotal_number());
        }

        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                point = i;
                double a=0,b=0;
                double aa=0,bb=0;
                if (StringUtil.isBlank(list.get(point).getMin_number())){
                    a=0;
                }else {
                    a = Double.parseDouble(list.get(point).getMin_number());
                }
                if (StringUtil.isBlank(list.get(point).getNumber())){
                   b=0;
                }else {
                    b = Double.parseDouble(list.get(point).getNumber());
                }
                if (StringUtil.isBlank(list.get(point).getPrice())){
                    aa=0;
                    bb=0;
                }else {
                    aa= Double.parseDouble(list.get(point).getPrice())*a;
                    bb=Double.parseDouble(list.get(point).getPrice())*b;
                }
                double c = a * b;
                Nums="数量在"+a+"-"+b+"之间";
                Pris="价格在"+aa+"-"+bb+"之间";
                showDialog1(list.get(i).getId());


            }
        });
        return view;
    }


    class ViewHodler {
        TextView name1, name, num, danjia,edu,times;
        LinearLayout titl;
        ImageView tu;
        Button bt;
    }

    private void showDialog1(final String ids) {
        bottomDialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.c_button, null);
        TextView quxiao = (TextView) contentView.findViewById(R.id.quxiao);
        title = (TextView) contentView.findViewById(R.id.title);
        title1 = (TextView) contentView.findViewById(R.id.title1);
        danjia = (TextView) contentView.findViewById(R.id.danjia);
        xiane = (TextView) contentView.findViewById(R.id.xiane);
        jiaoyi = (TextView) contentView.findViewById(R.id.jiaoyi);
        shuliang = (TextView) contentView.findViewById(R.id.shuliang);
        nums = (TextView) contentView.findViewById(R.id.nums);
        all_tv = (TextView) contentView.findViewById(R.id.all_tv);
        next = (TextView) contentView.findViewById(R.id.next);
        next.setText(UserBean.C2CMM);
        va = (View) contentView.findViewById(R.id.va);
        va1 = (View) contentView.findViewById(R.id.va1);
        num = (EditText) contentView.findViewById(R.id.num);
        num.setHint("请输入要"+UserBean.C2CMM+"数量");
        bottomDialog.setContentView(contentView);
        xiane.setText(Nums);
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
                    bottomDialog.cancel();
                    GouMaioutShow("" + ids);
                    Log.e("AAASAS", "IDS=" + list.get(point).getId());
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
                if (LeiXing.equals("money")) {
                    num.setText(maxJia);
                } else {
                    double a=0,aa;
                    if (StringUtil.isBlank(maxJia)){
                        a = 0;
                    }else {
                        a = Double.parseDouble(maxJia);
                    }
                    if (StringUtil.isBlank(DanJia)){
                        aa = 0;
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
                num.setHint("请输入要购买价格");
                LeiXing = "money";
                nums.setText("0");
                xiane.setText(Pris);
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
                num.setHint("请输入要购买数量");
                nums.setText("0");
                xiane.setText(Nums);
                LeiXing = "number";
                jiaoyi.setTextColor(context.getResources().getColor(R.color.black));
                va.setBackgroundResource(R.color.transparent);
                shuliang.setTextColor(context.getResources().getColor(R.color.blue));
                va1.setBackgroundResource(R.color.blue);
            }
        });
    }



    //out
    private void GouMaioutShow(final String ss) {
        Log.e("AAAAAss", ss+"=ss="+num.getText().toString().trim() + "=LeiXing=" + LeiXing);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getURLC2CGouMai(ss,  num.getText().toString().trim(),LeiXing);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {
                    return;
                }
                Log.e("AAAAAsGouMaioutShows", "@@11=" + response.body().getMessage());
                notifyDataSetChanged();
                if (response.body().getType().equals("ok")){
                    Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show();
                    if (UserBean.C2CMM.equals("购买")){
                        context.startActivity(new Intent(context,C2C_XiangQing.class).putExtra("Ids", response.body().getMessage())
                                .putExtra("typs","buy"));

                    }else {
                        context.startActivity(new Intent(context,C2C_XiangQing1.class).putExtra("Ids", response.body().getMessage())
                                .putExtra("typs","sell"));

                    }
                }else {

                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getMessage().equals("请先进行安全设置")){
                        //context.startActivity(new Intent(context,ShenFen_Activity1.class));
                        StringUtil.ToastShow1(context,"请先进行实名认证在下单");
                    }
                }


            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + call.toString() + "@@##" + t.getMessage().toString());
            }
        });
    }

}