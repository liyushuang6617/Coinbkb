package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class HangQingBean {
    private String type;
    private List<MessageBean> message;

    @Override
    public String toString() {
        return "HangQingBean{" +
                "type='" + type + '\'' +
                ", message=" + message +
                '}';
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class MessageBean{
        private String name;
        private int id;
        private String cny_rate;
        private int legal_id;
        private List<quotationBean>  quotation;

        @Override
        public String toString() {
            return "MessageBean{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", cny_rate='" + cny_rate + '\'' +
                    ", legal_id=" + legal_id +
                    ", quotation=" + quotation +
                    '}';
        }

        public int getLegal_id() {
            return legal_id;
        }

        public void setLegal_id(int legal_id) {
            this.legal_id = legal_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCny_rate() {
            return cny_rate;
        }

        public void setCny_rate(String cny_rate) {
            this.cny_rate = cny_rate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<quotationBean> getQuotation() {
            return quotation;
        }

        public void setQuotation(List<quotationBean> quotation) {
            this.quotation = quotation;
        }
    }
    public static class quotationBean{
        private String legal_id;
        private String currency_name;
        private String change;
        private String now_price;
        private String now_cny_price;
        private String legal_name;
        private String volume;
        private String currency_id;
        private String currency_match_id;

        @Override
        public String toString() {
            return "quotationBean{" +
                    "legal_id='" + legal_id + '\'' +
                    ", currency_name='" + currency_name + '\'' +
                    ", change='" + change + '\'' +
                    ", now_price='" + now_price + '\'' +
                    ", now_cny_price='" + now_cny_price + '\'' +
                    ", legal_name='" + legal_name + '\'' +
                    ", volume='" + volume + '\'' +
                    ", currency_id='" + currency_id + '\'' +
                    ", currency_match_id='" + currency_match_id + '\'' +
                    '}';
        }

        public String getCurrency_match_id() {
            return currency_match_id;
        }

        public void setCurrency_match_id(String currency_match_id) {
            this.currency_match_id = currency_match_id;
        }

        public String getNow_cny_price() {
            return now_cny_price;
        }

        public void setNow_cny_price(String now_cny_price) {
            this.now_cny_price = now_cny_price;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
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

        public String getNow_price() {
            return now_price;
        }

        public void setNow_price(String now_price) {
            this.now_price = now_price;
        }

        public String getLegal_name() {
            return legal_name;
        }

        public void setLegal_name(String legal_name) {
            this.legal_name = legal_name;
        }

        public String getCurrency_id() {
            return currency_id;
        }

        public void setCurrency_id(String currency_id) {
            this.currency_id = currency_id;
        }
    }
}
