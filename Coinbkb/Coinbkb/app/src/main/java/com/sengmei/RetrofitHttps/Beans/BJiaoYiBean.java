package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class BJiaoYiBean {

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

        private List<ObjectBean> in;
        private List<ObjectBean> out;
        private List<ObjectBean> complete;
        private String last_price;

        public String getLast_price() {
            return last_price;
        }

        public void setLast_price(String last_price) {
            this.last_price = last_price;
        }

        public List<ObjectBean> getComplete() {
            return complete;
        }

        public void setComplete(List<ObjectBean> complete) {
            this.complete = complete;
        }

        public List<ObjectBean> getIn() {
            return in;
        }

        public void setIn(List<ObjectBean> in) {
            this.in = in;
        }

        public List<ObjectBean> getOut() {
            return out;
        }

        public void setOut(List<ObjectBean> out) {
            this.out = out;
        }
    }
    public static class ObjectBean {

        private String price;
        private String number;
        private String time;
        private String percentage;

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
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
    }
}
