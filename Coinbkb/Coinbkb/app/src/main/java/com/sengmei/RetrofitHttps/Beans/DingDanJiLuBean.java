package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class DingDanJiLuBean {

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
    public static class dataBean {

        private String currency_name;
        private String format_create_time;
        private String type;
        private String number;
        private String deal_money;
        private String is_sure;
        private String id;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFormat_create_time() {
            return format_create_time;
        }

        public void setFormat_create_time(String format_create_time) {
            this.format_create_time = format_create_time;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDeal_money() {
            return deal_money;
        }

        public void setDeal_money(String deal_money) {
            this.deal_money = deal_money;
        }

        public String getIs_sure() {
            return is_sure;
        }

        public void setIs_sure(String is_sure) {
            this.is_sure = is_sure;
        }
    }
}