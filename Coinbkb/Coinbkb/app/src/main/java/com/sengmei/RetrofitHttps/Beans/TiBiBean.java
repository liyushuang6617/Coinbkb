package com.sengmei.RetrofitHttps.Beans;

import java.io.Serializable;

public class TiBiBean implements Serializable {


    private String type;
    private ObjectBean message;

    public ObjectBean getMessage() {
        return message;
    }

    public void setMessage( ObjectBean message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class ObjectBean {
        private String rate;
        private String change_balance;
        private String min_number;
        private String name;

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getChange_balance() {
            return change_balance;
        }

        public void setChange_balance(String change_balance) {
            this.change_balance = change_balance;
        }

        public String getMin_number() {
            return min_number;
        }

        public void setMin_number(String min_number) {
            this.min_number = min_number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
