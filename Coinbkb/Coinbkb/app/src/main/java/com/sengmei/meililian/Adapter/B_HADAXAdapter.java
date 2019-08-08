package com.sengmei.meililian.Adapter;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sengmei.RetrofitHttps.Beans.FaBiGouMaiBean;
import com.sengmei.RetrofitHttps.Beans.FormBTCBean;
import com.sengmei.RetrofitHttps.Beans.GeRenZhongXinBean;
import com.sengmei.RetrofitHttps.Beans.GetMaiBean;
import com.sengmei.RetrofitHttps.Beans.PanDuan;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.DingDanXiangQingActivity1;
import com.sengmei.meililian.Activity.E_FaBiGuanLi;
import com.sengmei.meililian.Activity.E_ShouKuanFangShi;
import com.sengmei.meililian.Activity.FaBiMiMaActivity;
import com.sengmei.meililian.Activity.LoginActivity;
import com.sengmei.meililian.Activity.ShangPuActivity;
import com.sengmei.meililian.Activity.ShenFen_Activity;
import com.sengmei.meililian.Activity.ShenFen_Activity2;
import com.sengmei.meililian.MyApplication;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.StringUtil;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class B_HADAXAdapter extends BaseAdapter {
    private Context context;
    private List<FormBTCBean.dataBean> list;
    private TextView title, title1, danjia, xiane, nums, all_tv, next, jiaoyi, shuliang,names;
    private View va, va1;
    private EditText num;
    private String LeiXing = "money";
    private String DanJia, maxJia, Titles,Title1s,Danjias,Xianes;
    private int point;
    private String MM="全部买入";
    private Dialog bottomDialog;
    public B_HADAXAdapter(Context context, List<FormBTCBean.dataBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.b_hadax_item, null);
            holder.titl = (RelativeLayout) view.findViewById(R.id.titl);
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


        FormBTCBean.dataBean bean = list.get(i);
        holder.name.setText(bean.getSeller_name());
        if (bean.getSeller_name().length() > 1) {
            String str = bean.getSeller_name();
            String b = str.substring(0, 1);
            holder.name1.setText(b);
        }
        if (bean.getType().equals("buy")) {
            //holder.bt.setBackgroundResource(R.color.blue);
            holder.bt.setText("出售");
            MM="全部卖出";
        } else {
            //holder.bt.setBackgroundResource(R.color.blue);
            holder.bt.setText("购买");
            MM="全部买入";
        }
        holder.num.setText("数量  " + bean.getSurplus_number() + bean.getCurrency_name());
        holder.danjia.setText("￥  " + bean.getPrice());
        holder.edu.setText("限额￥" + bean.getLimitation().getMin() + "-" + bean.getLimitation().getMax());

        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.Authori.equals("")) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    return;
                }
                point = i;
                YinSiShow("" + list.get(i).getId());

            }
        });
        holder.titl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (MyApplication.Authori.equals("")) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(context, ShangPuActivity.class);
                intent.putExtra("id", "" + list.get(i).getSeller_id());
                context.startActivity(intent);*/
            }
        });
        return view;
    }


    class ViewHodler {
        TextView name1, name, num, danjia,edu;
        RelativeLayout titl;
        ImageView tu;
        Button bt;
    }

    private void showDialog1() {
        bottomDialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(R.layout.b_button, null);
        TextView quxiao = (TextView) contentView.findViewById(R.id.quxiao);
        names = (TextView) contentView.findViewById(R.id.names);
        names.setText(UserBean.Fabitit);
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
        num.setHint("请输入要"+UserBean.Fabitit+"法币金额");
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
                    GouMaioutShow("" + list.get(point).getId());
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
                Log.e("LeiXingLeiXing","LeiXing"+LeiXing );
                if (LeiXing.equals("money")) {
                    num.setText(maxJia);
                } else {
                    BigDecimal a=new BigDecimal(0);
                    BigDecimal aa=new BigDecimal(0);
                    if (StringUtil.isBlank(maxJia)){
                        a =new BigDecimal(0);
                    }else {
                        a = new BigDecimal(maxJia);
                    } if (StringUtil.isBlank(DanJia)){
                        aa =new BigDecimal(0);
                    }else {
                        aa =new BigDecimal(DanJia);
                    }
                    num.setText(a.divide(aa)+"");
                }
            }
        });
        jiaoyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText("");
                num.setHint("请输入要"+UserBean.Fabitit+"法币金额");
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
                num.setHint("请输入要"+UserBean.Fabitit+"数量");
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
                BigDecimal a=new BigDecimal(0);
                BigDecimal aa=new BigDecimal(0);

                if (StringUtil.isBlank(str)){
                    a =new BigDecimal(0);
                }else {
                    a =new BigDecimal(str);
                } if (StringUtil.isBlank(DanJia)){
                    aa =new BigDecimal(0);
                }else {
                    aa =new BigDecimal(DanJia);
                }
                if (LeiXing.equals("number")) {

                    nums.setText(aa.multiply(a)+"");
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
                    Toast.makeText(context, "暂无数据" + ss, Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("AAAAAss", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    Toast.makeText(context, "下单成功，请联系商家", Toast.LENGTH_SHORT).show();
                    bottomDialog.cancel();
                    context.startActivity(new Intent(context, E_FaBiGuanLi.class));
                   // GouMaioutShow2(ss);
                } else if (response.body().getType().equals("998")) {
                    bottomDialog.cancel();
                    Toast.makeText(context, "请先设置法币密码", Toast.LENGTH_SHORT).show();
                    //GouMaioutShow1(ss);
                } else {
                    //Toast.makeText(context, "下单失败", Toast.LENGTH_SHORT).show();
                    ZhongXinShow(ss);
                }
            }

            @Override
            public void onFailure(Call<PanDuan> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + call.toString() + "@@##" + t.getMessage().toString());
            }
        });
    }

    private void GouMaioutShow1(final String ss) {
        Log.e("AAAAAss", num.getText().toString().trim() + "=LeiXing=" + LeiXing);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getMaiout1(ss, LeiXing, num.getText().toString().trim());
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                if (response.body() == null) {

                    return;
                }
                Log.e("AAAAAss", "@@11=" + response.body().getType());
                Log.e("AAAAAss", "@@11=" + response.body().getMessage());
                if (response.body().getType().equals("998")) {
                    StringUtil.ToastShow1(context,"请先进行密码设置");
                    context.startActivity(new Intent(context, FaBiMiMaActivity.class).putExtra("titles", "0"));
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getMessage().equals("请先进行安全设置")){
                        StringUtil.ToastShow1(context,"请先进行安全设置");
                        context.startActivity(new Intent(context, E_ShouKuanFangShi.class));
                    }
                }
                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + call.toString() + "@@##" + t.getMessage().toString());
            }
        });
    }

    //判断认证状态
    private void ZhongXinShow(final String ss) {
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<GeRenZhongXinBean> indexdata = mFromServerInterface.gernzx();
        indexdata.enqueue(new Callback<GeRenZhongXinBean>() {

            @Override
            public void onResponse(Call<GeRenZhongXinBean> call, Response<GeRenZhongXinBean> response) {

                if (response.body() == null) {
                    return;
                }
                if (response.body().getType().equals("ok")) {
                    String s= response.body().getMessage().getReview_status();
                    if (s.equals("2")){
                        GouMaioutShow1(ss);
                    }else {
                        StringUtil.ToastShow1(context,"请先进行实名认证在下单");
                        context.startActivity(new Intent(context, ShenFen_Activity2.class));
                    }
                }

            }

            @Override
            public void onFailure(Call<GeRenZhongXinBean> call, Throwable t) {
                Log.e("AAAAA", "@@11=" + t.getMessage());
                StringUtil.ToastShow1(context,"请先登录");
            }
        });
    }
    private void GouMaioutShow2(final String ss) {
        Log.e("AAAAAss", num.getText().toString().trim() + "=LeiXing=" + LeiXing);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(context).getService();
        Call<GetMaiBean> indexdata = mFromServerInterface.getMaiout2(ss, LeiXing, num.getText().toString().trim());
        indexdata.enqueue(new Callback<GetMaiBean>() {

            @Override
            public void onResponse(Call<GetMaiBean> call, Response<GetMaiBean> response) {
                if (response.body() == null) {
                    Toast.makeText(context, "暂无数据" + ss, Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("AAAAAss", "@@11=" + response.body().getType());
                Log.e("AAAAAss", "@@11=" + response.body().getMessage());
                if (response.body().getType().equals("ok")) {
                    Log.e("AAAAAss", "@@11=" + response.body().getMessage());
                    Toast.makeText(context, response.body().getMessage().getMsg(), Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, DingDanXiangQingActivity1.class)
                            .putExtra("ids", response.body().getMessage().getData().getId()));
                }
            }

            @Override
            public void onFailure(Call<GetMaiBean> call, Throwable t) {
                Log.e("AAAAAss", "@@11=" + call.toString() + "@@##" + t.getMessage().toString());
            }
        });
    }
}