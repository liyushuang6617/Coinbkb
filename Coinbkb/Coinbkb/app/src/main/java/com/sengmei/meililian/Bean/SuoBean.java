package com.sengmei.meililian.Bean;

import java.util.List;

public class SuoBean {

    /**
     * message : [{"configId":1,"currencyName":"ROD","id":22,"last_time":"2019-02-21 01:00:00","logoUrl":"http://rodx.wanzhantong.cn/upload/8567dac3-2edd-4496-999d-ab26f7b64c521559567608.png","number":"14000.00000","time":"2019-01-21 18:09:32","total":"20000.00000"},{"configId":1,"currencyName":"ROD","id":32,"logoUrl":"http://rodx.wanzhantong.cn/upload/8567dac3-2edd-4496-999d-ab26f7b64c521559567608.png","number":"30.00000","time":"2019-06-02 23:22:54","total":"30.00000"},{"configId":1,"currencyName":"ROD","id":33,"logoUrl":"http://rodx.wanzhantong.cn/upload/8567dac3-2edd-4496-999d-ab26f7b64c521559567608.png","number":"29.00000","time":"2019-06-02 23:24:04","total":"29.00000"},{"configId":1,"currencyName":"ROD","id":34,"logoUrl":"http://rodx.wanzhantong.cn/upload/8567dac3-2edd-4496-999d-ab26f7b64c521559567608.png","number":"23.00000","time":"2019-06-02 23:55:44","total":"23.00000"},{"configId":1,"currencyName":"ROD","id":35,"logoUrl":"http://rodx.wanzhantong.cn/upload/8567dac3-2edd-4496-999d-ab26f7b64c521559567608.png","number":"12.00000","time":"2019-06-02 23:56:43","total":"12.00000"},{"configId":1,"currencyName":"ROD","id":36,"last_time":"2019-07-03 21:56:15","logoUrl":"http://rodx.wanzhantong.cn/upload/8567dac3-2edd-4496-999d-ab26f7b64c521559567608.png","number":"1.00000","time":"2019-06-03 21:56:15","total":"1.00000"},{"configId":2,"currencyName":"ROD","id":37,"last_time":"2019-07-03 23:22:34","logoUrl":"http://rodx.wanzhantong.cn/upload/8567dac3-2edd-4496-999d-ab26f7b64c521559567608.png","number":"1.00000","time":"2019-06-03 23:22:34","total":"1.00000"}]
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
         * configId : 1
         * currencyName : ROD
         * id : 22
         * last_time : 2019-02-21 01:00:00
         * logoUrl : http://rodx.wanzhantong.cn/upload/8567dac3-2edd-4496-999d-ab26f7b64c521559567608.png
         * number : 14000.00000
         * time : 2019-01-21 18:09:32
         * total : 20000.00000
         */

        private int configId;
        private String currencyName;
        private int id;
        private String last_time;
        private String logoUrl;
        private String number;
        private String time;
        private String total;

        public int getConfigId() {
            return configId;
        }

        public void setConfigId(int configId) {
            this.configId = configId;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }
}
