package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class JYLBBean {
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
        private List<ObjectBean> list;

        public List<ObjectBean> getList() {
            return list;
        }

        public void setList(List<ObjectBean> list) {
            this.list = list;
        }
    }
    public static class ObjectBean{

        private String change;
        private String memo;
        private String create_time;

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

}
