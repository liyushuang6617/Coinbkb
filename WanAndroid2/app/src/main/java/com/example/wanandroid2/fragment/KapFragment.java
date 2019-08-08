package com.example.wanandroid2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanandroid2.R;
import com.example.wanandroid2.api.MyService;
import com.example.wanandroid2.bean.LoginDataBean;
import com.example.wanandroid2.http.HttpManager;
import com.example.wanandroid2.utils.RxUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class KapFragment extends Fragment implements View.OnClickListener {


    private View view;
    /**
     * UserName
     */
    private EditText mLoginEtName;
    /**
     * Password
     */
    private EditText mLoginEtPwd;
    /**
     * Login
     */
    private Button mLoginBtn;
    /**
     * No account, Register
     */
    private TextView mLoginTvNot;
    private String name;
    private String pwd;

    public KapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_kap, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        name = mLoginEtName.getText().toString();
        pwd = mLoginEtPwd.getText().toString();
        HttpManager.getInstance().getApiService(MyService.class).getLoginData("user/login", name, pwd)
                .compose(RxUtils.rxScheduleThread())
                .compose(RxUtils.changeResult())
                .subscribe(new Observer<LoginDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginDataBean loginDataBean) {
                        if (loginDataBean.getErrorCode() == 0) {
                            if(loginDataBean!=null){

                            }else {
                                Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), loginDataBean.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView(View inflate) {
        mLoginEtName = (EditText) inflate.findViewById(R.id.login_et_name);
        mLoginEtPwd = (EditText) inflate.findViewById(R.id.login_et_pwd);
        mLoginBtn = (Button) inflate.findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(this);
        mLoginTvNot = (TextView) inflate.findViewById(R.id.login_tv_not);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_btn:
                break;
        }
    }
}
