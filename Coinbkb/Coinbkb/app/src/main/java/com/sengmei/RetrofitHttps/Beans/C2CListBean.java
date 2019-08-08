package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class C2CListBean {

    private String type;

    private List<dataBean> message;

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

        private String id;
        private String real_name;
        private String total_number;
        private String  min_number;
        private String  number;
        private String create_time;
        private String price;
        private String way;

        public String getMin_number() {
            return min_number;
        }

        public void setMin_number(String min_number) {
            this.min_number = min_number;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getTotal_number() {
            return total_number;
        }

        public void setTotal_number(String total_number) {
            this.total_number = total_number;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
        }
    }
}