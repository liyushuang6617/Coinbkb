package com.sengmei.RetrofitHttps.Beans;

public class KeYongBean {
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
        private String change_balance;
        public String getChange_balance() {
            return change_balance;
        }

        public void setChange_balance(String change_balance) {
            this.change_balance = change_balance;
        }
    }

}
