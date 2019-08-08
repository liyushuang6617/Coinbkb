package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class AnQuanBean {
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
        private String email;
        private String gesture_password;
        private String phone;
        private String paypassword;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPaypassword() {
            return paypassword;
        }

        public void setPaypassword(String paypassword) {
            this.paypassword = paypassword;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGesture_password() {
            return gesture_password;
        }

        public void setGesture_password(String gesture_password) {
            this.gesture_password = gesture_password;
        }

    }

}
