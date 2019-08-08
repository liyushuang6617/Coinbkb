package com.sengmei.meililian.Bean;

import java.util.List;

public class Suo1Bean {

    /**
     * message : [{"comment":"test","id":2,"rate":0.01,"totalDay":30}]
     * type : ok
     */

    private String type;
    private List<MessageBean> message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * comment : test
         * id : 2
         * rate : 0.01
         * totalDay : 30
         */

        private String comment;
        private int id;
        private double rate;
        private int totalDay;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public int getTotalDay() {
            return totalDay;
        }

        public void setTotalDay(int totalDay) {
            this.totalDay = totalDay;
        }
    }
}
