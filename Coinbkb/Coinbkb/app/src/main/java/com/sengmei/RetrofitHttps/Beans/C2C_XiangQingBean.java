package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class C2C_XiangQingBean {

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

    public static class MessageBean {

        private String create_time;
        private String currency_name;
        private String price;
        private String number;
        private String total_price;
        private String seller_phone;
        private String other_phone;
        private String status;
        private DataBean currency_uci;
        private cnyDataBean cny_uci;

        public cnyDataBean getCny_uci() {
            return cny_uci;
        }

        public void setCny_uci(cnyDataBean cny_uci) {
            this.cny_uci = cny_uci;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOther_phone() {
            return other_phone;
        }

        public void setOther_phone(String other_phone) {
            this.other_phone = other_phone;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
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

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getSeller_phone() {
            return seller_phone;
        }

        public void setSeller_phone(String seller_phone) {
            this.seller_phone = seller_phone;
        }

        public DataBean getCurrency_uci() {
            return currency_uci;
        }

        public void setCurrency_uci(DataBean currency_uci) {
            this.currency_uci = currency_uci;
        }
    }
    public static class DataBean{
        private String ali_pay_account;
        private String bank_account;
        private String bank_account_name;
        private String real_name;
        private String we_chat_account;
        private String we_chat_account_name;

        public String getAli_pay_account() {
            return ali_pay_account;
        }

        public void setAli_pay_account(String ali_pay_account) {
            this.ali_pay_account = ali_pay_account;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getBank_account_name() {
            return bank_account_name;
        }

        public void setBank_account_name(String bank_account_name) {
            this.bank_account_name = bank_account_name;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getWe_chat_account() {
            return we_chat_account;
        }

        public void setWe_chat_account(String we_chat_account) {
            this.we_chat_account = we_chat_account;
        }

        public String getWe_chat_account_name() {
            return we_chat_account_name;
        }

        public void setWe_chat_account_name(String we_chat_account_name) {
            this.we_chat_account_name = we_chat_account_name;
        }
    }
    public static class cnyDataBean{
        private String ali_pay_account;
        private String bank_account;
        private String bank_account_name;
        private String real_name;
        private String we_chat_account;
        private String we_chat_account_name;

        public String getAli_pay_account() {
            return ali_pay_account;
        }

        public void setAli_pay_account(String ali_pay_account) {
            this.ali_pay_account = ali_pay_account;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getBank_account_name() {
            return bank_account_name;
        }

        public void setBank_account_name(String bank_account_name) {
            this.bank_account_name = bank_account_name;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getWe_chat_account() {
            return we_chat_account;
        }

        public void setWe_chat_account(String we_chat_account) {
            this.we_chat_account = we_chat_account;
        }

        public String getWe_chat_account_name() {
            return we_chat_account_name;
        }

        public void setWe_chat_account_name(String we_chat_account_name) {
            this.we_chat_account_name = we_chat_account_name;
        }
    }
}
