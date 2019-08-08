package com.sengmei.meililian.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class BangDanAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private FragmentManager fm;
    public BangDanAdapter(FragmentManager fm, List<Fragment>list) {
        super(fm);
        this.fm=fm;
        this.list=list;
    }
    public BangDanAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
        this.fm=fm;
    }
    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(list!=null)
            return list.size();
        return 0;
    }

}
