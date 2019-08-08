package com.sengmei.RetrofitHttps.Beans;

import java.io.Serializable;


public class SuoCBean implements Serializable {

/*{"type":"ok","message":{"number":"20000.00000","total":"20000.00000","last_time":null,"time":"2019-01-19 11:48:05","id":20}}*/
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
        private String total;
        private String last_time;
        private String time;
        private String id;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
