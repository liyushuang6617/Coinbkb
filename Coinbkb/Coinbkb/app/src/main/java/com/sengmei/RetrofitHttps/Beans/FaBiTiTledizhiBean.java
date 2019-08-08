package com.sengmei.RetrofitHttps.Beans;


import java.util.List;

public class FaBiTiTledizhiBean {

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
        private String name;
        private String has_address_num;

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

        public String getHas_address_num() {
            return has_address_num;
        }

        public void setHas_address_num(String has_address_num) {
            this.has_address_num = has_address_num;
        }
    }
}
