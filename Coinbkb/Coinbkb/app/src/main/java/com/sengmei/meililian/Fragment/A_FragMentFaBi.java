package com.sengmei.meililian.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.E_FaBiGuanLi;
import com.sengmei.meililian.UserBean;

public class A_FragMentFaBi extends Fragment implements View.OnClickListener {
    private B_HBG bHbg;
    private B_HADAX bHadax;
    private TextView[] ButtonText;
    private Fragment[] fragments;
    private TextView goumai, chushou;
    private int index;
    private int currentTabIndex;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.jiaoyifabi, null);
        intView(view);
        bHbg = new B_HBG();
        bHadax = new B_HADAX();
        fragments = new Fragment[]{bHbg, bHadax};

        getChildFragmentManager().beginTransaction().add(R.id.content, bHbg)
                .add(R.id.content, bHadax).hide(bHadax)
                .show(bHbg)
                .commit();
        return view;
    }

    private void intView(View view) {
        ImageView back = (ImageView) view.findViewById(R.id.back);
        back.setVisibility(View.GONE);
        view.findViewById(R.id.jilu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), E_FaBiGuanLi.class));
            }
        });
        goumai = (TextView) view.findViewById(R.id.goumai);
        goumai.setOnClickListener(this);
        chushou = (TextView) view.findViewById(R.id.chushou);
        chushou.setOnClickListener(this);
        ButtonText = new TextView[2];
        ButtonText[0] = (TextView) view.findViewById(R.id.goumai);
        ButtonText[1] = (TextView) view.findViewById(R.id.chushou);
        ButtonText[0].setSelected(true);
    }

    private void StarColor() {
        goumai.setBackgroundResource(R.color.transparent);
        chushou.setBackgroundResource(R.color.transparent);
    }

    @Override
    public void onClick(View view) {
        StarColor();
        switch (view.getId()) {
            case R.id.goumai:
                UserBean.Fabitit="购买";
                index = 0;
                break;
            case R.id.chushou:
                UserBean.Fabitit="出售";
                index = 1;
                break;
            default:
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getChildFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.content, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        ButtonText[currentTabIndex].setSelected(false);
        // set current tab selected
        ButtonText[index].setSelected(true);
        Log.e("index=", index + "");
        ButtonText[index].setBackgroundResource(R.drawable.fabi_top1);
        currentTabIndex = index;
    }


}
