package com.sengmei.RetrofitHttps.Beans;

public class GetMaiBean {

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
        private String msg;
        private dataBean data;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public dataBean getData() {
            return data;
        }

        public void setData(dataBean data) {
            this.data = data;
        }
    }
    public static class dataBean {
        private String id;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
