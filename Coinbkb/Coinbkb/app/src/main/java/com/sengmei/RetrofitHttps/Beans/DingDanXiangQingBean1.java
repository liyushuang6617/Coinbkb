package com.sengmei.RetrofitHttps.Beans;

public class DingDanXiangQingBean1 {
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

    public static class ObjectBean{

        private String deal_money;//交易总额
        private String seller_name;//买家
        private String price;//单价
        private String number;//数量
        private String format_create_time;//时间
        private String id;//参号
        private String account_number;//电话
        private String is_sure;//判断
        private String type;//买卖家
        private String seller_phone;//电话
        private String user_id;//
        private String currency_name;//
        private String way_name;//
        private String hes_realname;//

        public String getHes_realname() {
            return hes_realname;
        }

        public void setHes_realname(String hes_realname) {
            this.hes_realname = hes_realname;
        }

        public String getWay_name() {
            return way_name;
        }

        public void setWay_name(String way_name) {
            this.way_name = way_name;
        }

        public String getCurrency_name() {
            return currency_name;
        }

        public void setCurrency_name(String currency_name) {
            this.currency_name = currency_name;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSeller_phone() {
            return seller_phone;
        }

        public void setSeller_phone(String seller_phone) {
            this.seller_phone = seller_phone;
        }

        public String getIs_sure() {
            return is_sure;
        }

        public void setIs_sure(String is_sure) {
            this.is_sure = is_sure;
        }

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }

        public String getDeal_money() {
            return deal_money;
        }

        public void setDeal_money(String deal_money) {
            this.deal_money = deal_money;
        }

        public String getSeller_name() {
            return seller_name;
        }

        public void setSeller_name(String seller_name) {
            this.seller_name = seller_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getFormat_create_time() {
            return format_create_time;
        }

        public void setFormat_create_time(String format_create_time) {
            this.format_create_time = format_create_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
