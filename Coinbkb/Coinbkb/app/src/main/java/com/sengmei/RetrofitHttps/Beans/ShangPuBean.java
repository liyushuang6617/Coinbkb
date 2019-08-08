package com.sengmei.RetrofitHttps.Beans;


import java.util.Date;
import java.util.List;

public class ShangPuBean {

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
        private String name;//商家姓名
        private String create_time;//注册时间
        private String total;//总成单
        private String thirtyDays;//30单
        private String done;//完成单
        private String prove_email; //1认证 0未认证
        private String prove_level;
        private String prove_mobile;
        private String prove_real;
        private String currency_name;
        private String currency_id;
        private listsBean lists;

        public String getCurrency_id() {
            return currency_id;
        }

        public void setCurrency_id(String currency_id) {
            this.currency_id = currency_id;
        }

        public String getCurrency_name() {
            return currency_name;
        }

        public void setCurrency_name(String currency_name) {
            this.currency_name = currency_name;
        }

        public listsBean getLists() {
            return lists;
        }

        public void setLists(listsBean lists) {
            this.lists = lists;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getThirtyDays() {
            return thirtyDays;
        }

        public void setThirtyDays(String thirtyDays) {
            this.thirtyDays = thirtyDays;
        }

        public String getDone() {
            return done;
        }

        public void setDone(String done) {
            this.done = done;
        }

        public String getProve_email() {
            return prove_email;
        }

        public void setProve_email(String prove_email) {
            this.prove_email = prove_email;
        }

        public String getProve_level() {
            return prove_level;
        }

        public void setProve_level(String prove_level) {
            this.prove_level = prove_level;
        }

        public String getProve_mobile() {
            return prove_mobile;
        }

        public void setProve_mobile(String prove_mobile) {
            this.prove_mobile = prove_mobile;
        }

        public String getProve_real() {
            return prove_real;
        }

        public void setProve_real(String prove_real) {
            this.prove_real = prove_real;
        }
    }
    public static class listsBean {
        private List<DateBean> data;

        public List<DateBean> getData() {
            return data;
        }

        public void setData(List<DateBean> data) {
            this.data = data;
        }
    }
    public static class DateBean {

        private String currency_name;//
        private String total_number;//数量
        private limitationBean limitation;
        private String price;
        private String way;
        private String type;
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

        public String getTotal_number() {
            return total_number;
        }

        public void setTotal_number(String total_number) {
            this.total_number = total_number;
        }

        public limitationBean getLimitation() {
            return limitation;
        }

        public void setLimitation(limitationBean limitation) {
            this.limitation = limitation;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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
    }
    public static class limitationBean {
        private String min;
        private String max;

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }
    }

    }
