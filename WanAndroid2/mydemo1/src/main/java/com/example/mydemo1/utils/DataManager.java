package com.example.mydemo1.utils;

public class DataManager implements PreferenceHelper{

    private PreferenceHelper mPreferenceHelper;
    @Override
    public void setLoginStatus(boolean isLogin) {

    }

    @Override
    public boolean getLoginStatus() {
        return false;
    }

    @Override
    public void setLoginAccount(String account) {

    }

    @Override
    public String getLoginAccount() {
        return null;
    }

    @Override
    public void setNightMode(boolean isNightMode) {
        mPreferenceHelper.setNightMode(isNightMode);
    }

    @Override
    public boolean isNightMode() {
        return mPreferenceHelper.isNightMode();
    }
}
