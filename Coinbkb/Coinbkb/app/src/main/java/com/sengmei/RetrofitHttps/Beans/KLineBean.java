package com.sengmei.RetrofitHttps.Beans;


import java.util.List;

public class KLineBean {

    private List<ObjectBean> data;

    public List<ObjectBean> getData() {
        return data;
    }

    public void setData(List<ObjectBean> data) {
        this.data = data;
    }

    public static class ObjectBean {
        private String volume;
        private String high;
        private String low;
        private String time;
        private String close;
        private String open;

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }
    }
}
/**
 *  "volume":40.9997,
 *             "high":213.3,
 *             "low":213.12,
 *             "time":1541732100000,
 *             "close":213.12,
 *             "open":213.3
 * */