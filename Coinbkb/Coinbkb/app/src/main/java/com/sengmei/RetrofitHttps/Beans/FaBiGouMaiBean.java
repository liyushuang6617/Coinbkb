package com.sengmei.RetrofitHttps.Beans;

import java.io.Serializable;

/**
 * Created by chengwenlong on 2016/10/21.
 */

public class FaBiGouMaiBean implements Serializable {


    private String type;
    private ObjectBean message;

    public ObjectBean getMessage() {
        return message;
    }

    public void setMessage(ObjectBean message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public static class ObjectBean {
        private String  currency_name;
        private String price;
        private String surplus_number;
        private String prove_email;
        private String prove_mobile;
        private String prove_level;
         private String prove_real;
        private limiBean limitation;

        public String getProve_email() {
            return prove_email;
        }

        public void setProve_email(String prove_email) {
            this.prove_email = prove_email;
        }

        public String getProve_mobile() {
            return prove_mobile;
        }

        public void setProve_mobile(String prove_mobile) {
            this.prove_mobile = prove_mobile;
        }

        public String getProve_level() {
            return prove_level;
        }

        public void setProve_level(String prove_level) {
            this.prove_level = prove_level;
        }

        public String getProve_real() {
            return prove_real;
        }

        public void setProve_real(String prove_real) {
            this.prove_real = prove_real;
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

        public limiBean getLimitation() {
            return limitation;
        }

        public void setLimitation(limiBean limitation) {
            this.limitation = limitation;
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
