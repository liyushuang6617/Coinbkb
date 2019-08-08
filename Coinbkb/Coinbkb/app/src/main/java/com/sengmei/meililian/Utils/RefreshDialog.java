package com.sengmei.meililian.Utils;

import android.app.Dialog;
import android.content.Context;

import com.sengmei.kline.R;

public class RefreshDialog {
    private Dialog dialog;
    private Context context;

    public RefreshDialog(Context context) {
        this.context = context;
    }

    public void showLoading(){
        if(dialog==null){
            dialog=new Dialog(context, R.style.Translucent_NoTitle);
            dialog.setContentView(R.layout.loading);
        }
        if(!dialog.isShowing()){
            dialog.show();
        }
    }
    public void hideLoading(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
