package com.sengmei.RetrofitHttps.Beans;

import java.io.Serializable;

/**
 * Created by chengwenlong on 2016/10/21.
 */

public class HuoQuShouKuanBean implements Serializable {


    private String type;
    private ObjectBean message;

    public ObjectBean getMessage() {
        return message;
    }

    public void setMessage(ObjectBean message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




    public static class ObjectBean {
        private String real_name;
        private int id;
        private String bank_name;
        private String bank_account;
        private String alipay_account;
        private String wechat_nickname;
        private String wechat_account;

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getAlipay_account() {
            return alipay_account;
        }

        public void setAlipay_account(String alipay_account) {
            this.alipay_account = alipay_account;
        }

        public String getWechat_nickname() {
            return wechat_nickname;
        }

        public void setWechat_nickname(String wechat_nickname) {
            this.wechat_nickname = wechat_nickname;
        }

        public String getWechat_account() {
            return wechat_account;
        }

        public void setWechat_account(String wechat_account) {
            this.wechat_account = wechat_account;
        }
    }
}
