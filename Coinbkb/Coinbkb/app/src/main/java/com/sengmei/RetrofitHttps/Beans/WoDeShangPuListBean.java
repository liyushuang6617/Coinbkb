package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class WoDeShangPuListBean {
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

        private String seller_name;//
        private String id;//
        private String currency_name;//
        private String limitation_max;
        private String limitation_min;
        private String type;
        private String price;
        private String way;
        private String surplus_number;
        private String currency_id;

        public String getCurrency_id() {
            return currency_id;
        }

        public void setCurrency_id(String currency_id) {
            this.currency_id = currency_id;
        }

        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
        }

        public String getSurplus_number() {
            return surplus_number;
        }

        public void setSurplus_number(String surplus_number) {
            this.surplus_number = surplus_number;
        }

        public String getSeller_name() {
            return seller_name;
        }

        public void setSeller_name(String seller_name) {
            this.seller_name = seller_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCurrency_name() {
            return currency_name;
        }

        public void setCurrency_name(String currency_name) {
            this.currency_name = currency_name;
        }

        public String getLimitation_max() {
            return limitation_max;
        }

        public void setLimitation_max(String limitation_max) {
            this.limitation_max = limitation_max;
        }

        public String getLimitation_min() {
            return limitation_min;
        }

        public void setLimitation_min(String limitation_min) {
            this.limitation_min = limitation_min;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
