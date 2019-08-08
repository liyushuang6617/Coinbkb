package com.sengmei.RetrofitHttps.Beans;


import java.util.List;

public class C2CTiTleBean {

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
        private String max;
        private String min;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
