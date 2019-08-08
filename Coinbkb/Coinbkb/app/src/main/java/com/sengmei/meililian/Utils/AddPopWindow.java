package com.sengmei.meililian.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.sengmei.RetrofitHttps.Interfaces.FabuBack;
import com.sengmei.kline.R;

public class AddPopWindow extends PopupWindow implements View.OnClickListener {
    private View conentView;
    private Activity context;
    private FabuBack fabuBack;
    @SuppressLint("InflateParams")
    public AddPopWindow(final Activity context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popupwindow_add, null);

        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);
        conentView.findViewById(R.id.re_zhifu).setOnClickListener(this);
        conentView.findViewById(R.id.re_yinhang).setOnClickListener(this);
        conentView.findViewById(R.id.re_weixin).setOnClickListener(this);
    }

    public void setFabuBack(FabuBack fabuBack) {
        this.fabuBack = fabuBack;
    }

    public FabuBack getFabuBack() {
        return fabuBack;
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re_zhifu:
                fabuBack.Ways("ali_pay");
                AddPopWindow.this.dismiss();
                break;
            case R.id.re_yinhang:
                fabuBack.Ways("bank");
                AddPopWindow.this.dismiss();
                break;
            case R.id.re_weixin:
                fabuBack.Ways("we_chat");
                AddPopWindow.this.dismiss();
                break;
        }
    }
}
