package com.sengmei.RetrofitHttps.Beans;

public class KTitleBean {

    /**
     * message : {"change":"-1.75","cny_rate":"6.89","currencyMatchId":99,"high":"5138.14000","low":"0.00000","now_cny_price":"34739.17330","now_price":"5041.97000","volume":"7556.93668"}
     * type : ok
     */

    private MessageBean message;
    private String type;

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class MessageBean {
        /**
         * change : -1.75
         * cny_rate : 6.89
         * currencyMatchId : 99
         * high : 5138.14000
         * low : 0.00000
         * now_cny_price : 34739.17330
         * now_price : 5041.97000
         * volume : 7556.93668
         */

        private String change;
        private String cny_rate;
        private int currencyMatchId;
        private String high;
        private String low;
        private String now_cny_price;
        private String now_price;
        private String volume;

        @Override
        public String toString() {
            return "MessageBean{" +
                    "change='" + change + '\'' +
                    ", cny_rate='" + cny_rate + '\'' +
                    ", currencyMatchId=" + currencyMatchId +
                    ", high='" + high + '\'' +
                    ", low='" + low + '\'' +
                    ", now_cny_price='" + now_cny_price + '\'' +
                    ", now_price='" + now_price + '\'' +
                    ", volume='" + volume + '\'' +
                    '}';
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getCny_rate() {
            return cny_rate;
        }

        public void setCny_rate(String cny_rate) {
            this.cny_rate = cny_rate;
        }

        public int getCurrencyMatchId() {
            return currencyMatchId;
        }

        public void setCurrencyMatchId(int currencyMatchId) {
            this.currencyMatchId = currencyMatchId;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getNow_cny_price() {
            return now_cny_price;
        }

        public void setNow_cny_price(String now_cny_price) {
            this.now_cny_price = now_cny_price;
        }

        public String getNow_price() {
            return now_price;
        }

        public void setNow_price(String now_price) {
            this.now_price = now_price;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }
    }
}
/*
{
        "type": "ok",
        "message": {
        "high": "",
        "low": "",
        "24h": "",
        "change": "",
        "now_price": ""
        }
        }*/
