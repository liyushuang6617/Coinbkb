package com.sengmei.meililian.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sengmei.kline.R;

/**
 * 提示弹窗
 */
public class CustomDialog extends Dialog {
    int layoutRes;//布局文件
    private Context context;
    private CallBack callBack;
    //    private OnOperItemClickL onOperItemClickL;
    public CustomDialog(Context context) {
        super(context);
        this.context = context;
    }
    /**
     * 自定义布局的构造方法
     * @param context
     * @param resLayout
     */
    public CustomDialog(Context context, int resLayout ){
        super(context);
        this.context = context;
        this.layoutRes=resLayout;
    }
    /**
     * 自定义主题及布局的构造方法
     * @param context
     * @param theme
     * @param resLayout
     */
    public CustomDialog(Context context, int theme, int resLayout, CallBack callBack){
        super(context, theme);
        this.context = context;
        this.callBack = callBack;
        this.layoutRes=resLayout;
    }
    public CustomDialog(Context context, int theme, int resLayout ){
        super(context, theme);
        this.context = context;
        this.layoutRes=resLayout;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layoutRes);
    }

    /** dismiss without anim(无动画dismiss) */
    public void superDismiss() {
        super.dismiss();
    }

    /** dialog anim by styles(动画弹出对话框,style动画资源) */
    public void show(int animStyle) {
        Window window = getWindow();
        window.setWindowAnimations(animStyle);
        show();
    }

//    public void setOnOperItemClickL(OnOperItemClickL onOperItemClickL) {
//        this.onOperItemClickL = onOperItemClickL;
//    }

    public void getDialog(View view, final CustomDialog dialog, Object title, final boolean isContent) {
        LayoutInflater in = LayoutInflater.from(context);
        view = in.inflate(R.layout.dialog_item, null);
        dialog.setContentView(view);

        TextView tv_doalogTitle = (TextView) view.findViewById(R.id.tv_doalogTitle);
        LinearLayout ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
        final TextView et_dialogContent = (TextView) view.findViewById(R.id.et_dialogContent);
        TextView queren = (TextView) view.findViewById(R.id.queren);
        TextView quxiao = (TextView) view.findViewById(R.id.quxiao);

        if (title instanceof String){
            tv_doalogTitle.setText((String)title);
        } else if (title instanceof Integer){
            tv_doalogTitle.setText((Integer)title);
        }

        if (isContent){
            ll_content.setVisibility(View.VISIBLE);
        } else {
            ll_content.setVisibility(View.GONE);
        }

        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=et_dialogContent.getText().toString();
                if(isContent){
                    callBack.queren(content);
                    dismiss();
                } else {
                    callBack.queren(content);
                    dismiss();
                }
            }
        });

        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.quxiao();
                dismiss();
            }
        });
    }

    public interface CallBack {
        void queren(String content);
        void quxiao();
    }
}