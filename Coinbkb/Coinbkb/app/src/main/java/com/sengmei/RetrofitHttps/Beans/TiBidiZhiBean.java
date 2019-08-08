package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class TiBidiZhiBean {

    private String type;
    private List<ObjectBean> message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ObjectBean> getMessage() {
        return message;
    }

    public void setMessage(List<ObjectBean> message) {
        this.message = message;
    }
    public static class ObjectBean {

        private String id;
        private String notes;
        private String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
