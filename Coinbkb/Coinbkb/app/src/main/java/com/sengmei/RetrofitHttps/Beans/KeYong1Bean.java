package com.sengmei.RetrofitHttps.Beans;

public class KeYong1Bean {
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
        private String legal_balance;

        public String getLegal_balance() {
            return legal_balance;
        }

        public void setLegal_balance(String legal_balance) {
            this.legal_balance = legal_balance;
        }
    }

}
