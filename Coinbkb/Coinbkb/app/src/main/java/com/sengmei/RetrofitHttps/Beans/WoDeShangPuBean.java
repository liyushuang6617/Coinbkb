package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class WoDeShangPuBean {
    private String type;

    private ObjectBean message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ObjectBean getMessage() {
        return message;
    }

    public void setMessage(ObjectBean message) {
        this.message = message;
    }

    public static class ObjectBean {
        private List<DateBean> data;//商铺信息

        public List<DateBean> getData() {
            return data;
        }

        public void setData(List<DateBean> data) {
            this.data = data;
        }
    }

    public static class DateBean {

        private String name;//
        private String create_time;//
        private String currency_name;
        private String seller_balance;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCurrency_name() {
            return currency_name;
        }

        public void setCurrency_name(String currency_name) {
            this.currency_name = currency_name;
        }

        public String getSeller_balance() {
            return seller_balance;
        }

        public void setSeller_balance(String seller_balance) {
            this.seller_balance = seller_balance;
        }
    }
}
