package com.sengmei.RetrofitHttps.Beans;

import java.io.Serializable;

/**
 * Created by chengwenlong on 2016/10/21.
 */

public class WoDeTangGuoBean implements Serializable {


    private String type;
    private ObjectBean message;

    public ObjectBean getMessage() {
        return message;
    }

    public void setMessage(ObjectBean message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public static class ObjectBean {
        private String number;
        private String invite;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getInvite() {
            return invite;
        }

        public void setInvite(String invite) {
            this.invite = invite;
        }
    }
}
