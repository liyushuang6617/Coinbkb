package com.example.wanandroid2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wanandroid2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigFragment extends Fragment implements View.OnClickListener {


    private View view;
    /**
     * UserName
     */
    private EditText mAccountEtName;
    /**
     * Password
     */
    private EditText mAccountEtPwd;
    /**
     * Register
     */
    private EditText mAccountEtRepwd;
    /**
     * Have account, Login
     */
    private Button mAccountBtn;
    /**
     * Have account, Login
     */
    private TextView mAccountTvNot;

    public NavigFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_navig, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mAccountEtName = (EditText) inflate.findViewById(R.id.account_et_name);
        mAccountEtPwd = (EditText) inflate.findViewById(R.id.account_et_pwd);
        mAccountEtRepwd = (EditText) inflate.findViewById(R.id.account_et_repwd);
        mAccountBtn = (Button) inflate.findViewById(R.id.account_btn);
        mAccountBtn.setOnClickListener(this);
        mAccountTvNot = (TextView) inflate.findViewById(R.id.account_tv_not);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.account_btn:
                break;
        }
    }
}
