package com.sengmei.RetrofitHttps.Beans;


import java.util.List;

public class FaBiTiTleBean {

    private String type;

    private List<ObjectBean> message;

    @Override
    public String toString() {
        return "FaBiTiTleBean{" +
                "type='" + type + '\'' +
                ", message=" + message +
                '}';
    }

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
        private int id;
        private String name;

        @Override
        public String toString() {
            return "ObjectBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
