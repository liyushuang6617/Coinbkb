package com.sengmei.kline.bean;

public class KLineHeaderBean {


    /**
     * change : 0.4578155600//
     * currency_id : 24//
     * currency_name : LTC//
     * high_price : 30.8600000
     * legal_id : 2//
     * legal_name : USDT//
     * low_price : 30.2900000
     * now_cny_price : 208.8960000
     * now_price : 30.7200000//
     * type : daymarket
     * volume : 100484.87577//
     * content :
     */
    /*
    * {
    "type":"daymarket",
    "period":"1day",
    "currency_id":"6",//
    "currency_name":"XRP",//
    "legal_id":"3",//
    "legal_name":"USDT",//
    "open":"0.3563",
    "close":"0.3615",
    "high":"0.368",
    "low":"0.3563",
    "symbol":"XRP/USDT",
    "volume":"37539819.953405",//
    "time":"1554307200000",
    "change":"+1.46",//
    "now_price":"0.3615",//
    "api_form":"huobi_websocket",
    "content":""
}
    * */

    private String change;
    private int currency_id;
    private String currency_name;
    private String high_price;
    private int legal_id;
    private String legal_name;
    private String low_price;
    private String now_cny_price;
    private String now_price;
    private String type;
    private double volume;
    private String content;


    private String high;
    private String low;
    private String open;

    @Override
    public String toString() {
        return "KLineHeaderBean{" +
                "change='" + change + '\'' +
                ", currency_id=" + currency_id +
                ", currency_name='" + currency_name + '\'' +
                ", high_price='" + high_price + '\'' +
                ", legal_id=" + legal_id +
                ", legal_name='" + legal_name + '\'' +
                ", low_price='" + low_price + '\'' +
                ", now_cny_price='" + now_cny_price + '\'' +
                ", now_price='" + now_price + '\'' +
                ", type='" + type + '\'' +
                ", volume=" + volume +
                ", content='" + content + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", open='" + open + '\'' +
                '}';
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

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getHigh_price() {
        return high_price;
    }

    public void setHigh_price(String high_price) {
        this.high_price = high_price;
    }

    public int getLegal_id() {
        return legal_id;
    }

    public void setLegal_id(int legal_id) {
        this.legal_id = legal_id;
    }

    public String getLegal_name() {
        return legal_name;
    }

    public void setLegal_name(String legal_name) {
        this.legal_name = legal_name;
    }

    public String getLow_price() {
        return low_price;
    }

    public void setLow_price(String low_price) {
        this.low_price = low_price;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
