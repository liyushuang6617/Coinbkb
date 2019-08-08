package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class ZhangFuBean {

    private String type;

    private List<dataBean>  message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<dataBean> getMessage() {
        return message;
    }

    public void setMessage(List<dataBean> message) {
        this.message = message;
    }

    public static class dataBean{

        private String legal_id;
        private String currency_name;
        private String change;
        private String legal_name;
        private String now_price;
        private String currency_id;
        private String cny_price;
        private String volume;

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getCny_price() {
            return cny_price;
        }

        public void setCny_price(String cny_price) {
            this.cny_price = cny_price;
        }

        public String getLegal_id() {
            return legal_id;
        }

        public void setLegal_id(String legal_id) {
            this.legal_id = legal_id;
        }

        public String getCurrency_name() {
            return currency_name;
        }

        public void setCurrency_name(String currency_name) {
            this.currency_name = currency_name;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getLegal_name() {
            return legal_name;
        }

        public void setLegal_name(String legal_name) {
            this.legal_name = legal_name;
        }

        public String getNow_price() {
            return now_price;
        }

        public void setNow_price(String now_price) {
            this.now_price = now_price;
        }

        public String getCurrency_id() {
            return currency_id;
        }

        public void setCurrency_id(String currency_id) {
            this.currency_id = currency_id;
        }
    }

    /*{
            "legal_id": 2,
            "currency_name": "EOS",
            "change": "2.030456900",
            "legal_name": "USDT",
            "now_price": 4.6833,
            "currency_id": 5
        },*/
}