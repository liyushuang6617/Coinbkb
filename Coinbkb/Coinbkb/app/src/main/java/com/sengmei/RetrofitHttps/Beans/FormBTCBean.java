package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class FormBTCBean {

    private String type;

    private MessageBean message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean{
        private List<dataBean>  data;

        public List<dataBean> getData() {
            return data;
        }

        public void setData(List<dataBean> data) {
            this.data = data;
        }
    }
    public static class dataBean{

        private String seller_name;
        private String surplus_number;
        private String currency_name;
        private String price;
        private int seller_id;
        private String way_name;
        private String id;
        private String type;
        private String way;
        private limiBean limitation;

        public limiBean getLimitation() {
            return limitation;
        }

        public void setLimitation(limiBean limitation) {
            this.limitation = limitation;
        }

        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSeller_name() {
            return seller_name;
        }

        public void setSeller_name(String seller_name) {
            this.seller_name = seller_name;
        }

        public String getSurplus_number() {
            return surplus_number;
        }

        public void setSurplus_number(String surplus_number) {
            this.surplus_number = surplus_number;
        }

        public String getCurrency_name() {
            return currency_name;
        }

        public void setCurrency_name(String currency_name) {
            this.currency_name = currency_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(int seller_id) {
            this.seller_id = seller_id;
        }

        public String getWay_name() {
            return way_name;
        }

        public void setWay_name(String way_name) {
            this.way_name = way_name;
        }
    }

    public static class limiBean{
        private String max;
        private String min;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }
    }
}