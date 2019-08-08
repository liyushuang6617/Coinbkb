package com.sengmei.RetrofitHttps.Beans;

import android.os.Message;

import java.util.List;

public class DanShowBean {
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

    public static class MessageBean{

        private List<ObjectBean> data;

        public List<ObjectBean> getData() {
            return data;
        }

        public void setData(List<ObjectBean> data) {
            this.data = data;
        }
    }
    public static class ObjectBean{

        private String content;
        private String create_time;
        private String reply_content;
        private String reply_time;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getReply_content() {
            return reply_content;
        }

        public void setReply_content(String reply_content) {
            this.reply_content = reply_content;
        }

        public String getReply_time() {
            return reply_time;
        }

        public void setReply_time(String reply_time) {
            this.reply_time = reply_time;
        }
    }

}
