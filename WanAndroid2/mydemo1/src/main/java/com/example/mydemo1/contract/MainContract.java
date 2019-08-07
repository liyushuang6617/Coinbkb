package com.example.mydemo1.contract;

import com.example.mydemo1.bean.ListData;

import java.util.List;

//契约类
public interface MainContract {

    //V层
    interface MainView {

        //展示成功的数据
        void showSuccess(List<ListData> listData);

        //展示失败的数据
        void showFail(String msg);
    }

    //P 层
    interface MainPresenter {
        //处理网络
        void http();
    }

    //M层
    interface MainModel {
        //接口回调
        interface CallBack {

            //展示成功的数据
            void showSuccess(List<ListData> listData);

            //展示失败的数据
            void showFail(String msg);
        }

        //处理
        void getData(CallBack callBack);
    }
}
