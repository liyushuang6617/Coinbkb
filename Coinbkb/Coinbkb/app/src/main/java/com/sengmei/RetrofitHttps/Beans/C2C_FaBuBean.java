package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class C2C_FaBuBean {

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

        private List<ObjectBean> currency;

        public List<ObjectBean> getCurrency() {
            return currency;
        }

        public void setCurrency(List<ObjectBean> currency) {
            this.currency = currency;
        }
    }
    public static class ObjectBean {

        private String name;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
