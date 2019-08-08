package com.sengmei.RetrofitHttps.Beans;

public class YinSiBean {

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
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
